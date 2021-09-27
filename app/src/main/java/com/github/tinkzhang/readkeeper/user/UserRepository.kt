package com.github.tinkzhang.readkeeper.user

import android.app.Activity
import android.content.Context
import com.github.tinkzhang.readkeeper.R
import com.github.tinkzhang.readkeeper.reading.ReadingBook
import com.github.tinkzhang.readkeeper.settings.SettingsActivity
import com.google.android.gms.auth.api.signin.GoogleSignIn
import com.google.android.gms.auth.api.signin.GoogleSignInOptions
import com.google.firebase.auth.ktx.auth
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.firestore.ktx.toObjects
import com.google.firebase.ktx.Firebase
import timber.log.Timber
import java.util.*

class UserRepository {
    val user = Firebase.auth.currentUser

    private val userDocumentRef = if (user == null) {
        Firebase.firestore.document("user/${UUID.randomUUID()}}")
    } else {
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

    fun getReadingBook() {
        readingCollectionRef
            .get()
            .addOnSuccessListener { document ->
                Timber.d("Reading List: ${document.toObjects<ReadingBook>().forEach { it.title }}")
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
