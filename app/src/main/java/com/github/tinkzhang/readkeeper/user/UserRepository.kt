package com.github.tinkzhang.readkeeper.user

import android.app.Activity
import android.content.Context
import com.github.tinkzhang.readkeeper.R
import com.github.tinkzhang.readkeeper.common.data.ReadingBook
import com.github.tinkzhang.readkeeper.reading.PAGE_SIZE
import com.github.tinkzhang.readkeeper.settings.SettingsActivity
import com.google.android.gms.auth.api.signin.GoogleSignIn
import com.google.android.gms.auth.api.signin.GoogleSignInOptions
import com.google.firebase.auth.ktx.auth
import com.google.firebase.firestore.FieldPath
import com.google.firebase.firestore.Query
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase
import kotlinx.coroutines.tasks.await
import timber.log.Timber
import java.util.*

class UserRepository {
    val user = Firebase.auth.currentUser
    lateinit var lastBook: ReadingBook
    lateinit var firstPage: List<ReadingBook>

    private val userDocumentRef = if (user == null) {
        Timber.d("offline users")
        Firebase.firestore.document("user/${UUID.randomUUID()}}")
    } else {
        Timber.d("Signed in user with id: ${user.uid}")
        Firebase.firestore.document("user/${user.uid}")
    }

    private val readingCollectionRef = userDocumentRef.collection("readings")

    fun addReadingBook(book: ReadingBook) {
        readingCollectionRef
            .add(book)
            .addOnSuccessListener {
                Timber.d("Book ${book.bookInfo.title} has been added to reading lists")
            }
            .addOnFailureListener {
                Timber.e("The book ${book.bookInfo.title} failed to be added into the reading list")
            }
    }

    suspend fun getReadingList(page: Int): List<ReadingBook> {
        Timber.d("Load Reading List for Page: $page")
        return when (page) {
            0 -> {
                firstPage = readingCollectionRef
                    .orderBy(FieldPath.of("timeInfo", "editedTime"), Query.Direction.DESCENDING)
                    .limit(PAGE_SIZE.toLong())
                    .get()
                    .await()
                    .toObjects(ReadingBook::class.java)
                if (firstPage.isNotEmpty()) {
                    lastBook = firstPage.last()
                    Timber.d("ReadingBook: ${firstPage.first()}")
                }
                firstPage
            }
            else -> {
                val books = readingCollectionRef
                    .orderBy(FieldPath.of("timeInfo", "editedTime"), Query.Direction.DESCENDING)
                    .startAfter(lastBook)
                    .limit(PAGE_SIZE.toLong())
                    .get()
                    .await()
                    .toObjects(ReadingBook::class.java)
                if (books.isNotEmpty()) {
                    lastBook = books.last()
                }
                books
            }
        }
    }

    fun signOutWithGoogle() {
        Firebase.auth.signOut()
    }

    fun signInWithGoogle(context: Context) {
        val gso = GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
            .requestIdToken(context.getString(R.string.google_sign_in_token_id))
            .requestEmail()
            .requestProfile()
            .build()
        val googleSignInClient = GoogleSignIn.getClient(context, gso)

        val signInIntent = googleSignInClient.signInIntent
        (context as Activity).startActivityForResult(signInIntent, SettingsActivity.RC_SIGN_IN)
    }
}
