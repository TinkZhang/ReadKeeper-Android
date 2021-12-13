package com.github.tinkzhang.readkeeper.user

import android.app.Activity
import android.content.Context
import com.github.tinkzhang.readkeeper.R
import com.github.tinkzhang.readkeeper.reading.PAGE_SIZE
import com.github.tinkzhang.readkeeper.reading.ReadingBook
import com.github.tinkzhang.readkeeper.settings.SettingsActivity
import com.google.android.gms.auth.api.signin.GoogleSignIn
import com.google.android.gms.auth.api.signin.GoogleSignInOptions
import com.google.firebase.auth.ktx.auth
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase
import kotlinx.coroutines.tasks.await
import timber.log.Timber
import java.util.*

class UserRepository {
    val user = Firebase.auth.currentUser

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
                Timber.d("Book ${book.title} has been added to reading lists")
            }
            .addOnFailureListener {
                Timber.e("The book ${book.title} failed to be added into the reading list")
            }
    }

    suspend fun getReadingList(page: Int): List<ReadingBook>? {
        return readingCollectionRef
            .orderBy("addedTime")
            .startAt(page * PAGE_SIZE)
            .limit(PAGE_SIZE.toLong())
            .get()
            .await()
            .toObjects(ReadingBook::class.java)
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
