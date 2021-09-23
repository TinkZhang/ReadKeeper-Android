package com.github.tinkzhang.readkeeper.user

import android.app.Activity
import android.content.Context
import com.github.tinkzhang.readkeeper.R
import com.github.tinkzhang.readkeeper.settings.SettingsActivity
import com.google.android.gms.auth.api.signin.GoogleSignIn
import com.google.android.gms.auth.api.signin.GoogleSignInOptions
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase

class UserRepository{
    val user = Firebase.auth.currentUser

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
