package com.github.tinkzhang.basic

import android.app.Activity
import android.content.Context
import android.nfc.tech.MifareUltralight.PAGE_SIZE
import com.github.tinkzhang.basic.model.EditableBook
import com.github.tinkzhang.basic.model.ReadingBook
import com.github.tinkzhang.basic.model.WishBook
import com.github.tinkzhang.readkeeper.model.R
import com.google.android.gms.auth.api.signin.GoogleSignIn
import com.google.android.gms.auth.api.signin.GoogleSignInOptions
import com.google.firebase.auth.ktx.auth
import com.google.firebase.firestore.CollectionReference
import com.google.firebase.firestore.FieldPath
import com.google.firebase.firestore.Query
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase
import kotlinx.coroutines.tasks.await
import timber.log.Timber
import java.util.*

object UserRepository {
    val user = Firebase.auth.currentUser
    lateinit var lastBook: EditableBook


    private val userDocumentRef = if (user == null) {
        Timber.d("offline users")
        Firebase.firestore.document("user/${UUID.randomUUID()}}")
    } else {
        Timber.d("Signed in user with id: ${user.uid}")
        Firebase.firestore.document("user/${user.uid}")
    }

    val readingCollectionRef = userDocumentRef.collection("readings")
    val wishCollectionRef = userDocumentRef.collection("wishes")

    fun addReadingBook(book: ReadingBook) {
        readingCollectionRef
            .document(book.bookInfo.uuid)
            .set(book)
            .addOnSuccessListener {
                Timber.d("Book ${book.bookInfo.title} has been added to reading lists")
            }
            .addOnFailureListener {
                Timber.e("The book ${book.bookInfo.title} failed to be added into the reading list")
            }
    }

    inline fun <reified T: EditableBook> addBook(book: T) {
        when (T::class) {
            ReadingBook::class -> readingCollectionRef.document(book.bookInfo.uuid).set(book)
            WishBook::class -> wishCollectionRef.document(book.bookInfo.uuid).set(book)
            else -> Unit
        }
    }

    fun removeReadingBook(uuid: String) {
        readingCollectionRef.document(uuid).delete()
    }

    fun addWishBook(book: WishBook) {
        wishCollectionRef
            .document(book.bookInfo.uuid)
            .set(book)
            .addOnSuccessListener {
                Timber.d("Book ${book.bookInfo.title} has been added to wish lists")
            }
            .addOnFailureListener {
                Timber.e("The book ${book.bookInfo.title} failed to be added into the wish list")
            }
    }

    fun removeWishBook(uuid: String) {
        wishCollectionRef.document(uuid).delete()
    }

    suspend inline fun <reified T : EditableBook> getList(
        reference: CollectionReference,
        page: Int
    ): List<T> {
        Timber.d("Load Reading List for Page: $page")
        return when (page) {
            0 -> {
                val firstPage = reference
                    .orderBy(FieldPath.of("timeInfo", "editedTime"), Query.Direction.DESCENDING)
                    .limit(PAGE_SIZE.toLong())
                    .get()
                    .await()
                    .toObjects(T::class.java)
                if (firstPage.isNotEmpty()) {
                    lastBook = firstPage.last()
                    Timber.d("ReadingBook: ${firstPage.first()}")
                }
                firstPage
            }
            else -> {
                val books = reference
                    .orderBy(FieldPath.of("timeInfo", "editedTime"), Query.Direction.DESCENDING)
                    .startAfter(lastBook)
                    .limit(PAGE_SIZE.toLong())
                    .get()
                    .await()
                    .toObjects(T::class.java)
                if (books.isNotEmpty()) {
                    lastBook = books.last() as T
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
        (context as Activity).startActivityForResult(signInIntent, 9001)
    }

    suspend inline fun <reified T : EditableBook> getList(nextPage: Int): List<T> =
        when (T::class) {
            ReadingBook::class -> getList(readingCollectionRef, nextPage)
            WishBook::class -> getList(wishCollectionRef, nextPage)
            else -> listOf()
        }
}
