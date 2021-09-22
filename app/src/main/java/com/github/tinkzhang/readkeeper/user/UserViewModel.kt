package com.github.tinkzhang.readkeeper.user

import android.app.Activity
import android.content.Context
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.github.tinkzhang.readkeeper.R
import com.github.tinkzhang.readkeeper.settings.SettingsActivity
import com.google.android.gms.auth.api.signin.GoogleSignIn
import com.google.android.gms.auth.api.signin.GoogleSignInClient
import com.google.android.gms.auth.api.signin.GoogleSignInOptions
import com.google.firebase.auth.GoogleAuthProvider
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class UserViewModel @Inject constructor(
    private val userRepository: UserRepository
) : ViewModel() {
    private lateinit var googleSignInClient: GoogleSignInClient


    val isSignedIn: MutableLiveData<Boolean> by lazy {
        MutableLiveData<Boolean>()
    }

    val userEmail: MutableLiveData<String> by lazy {
        MutableLiveData<String>()
    }

    val userName: MutableLiveData<String> by lazy {
        MutableLiveData<String>()
    }

    init {
        isSignedIn.value = Firebase.auth.currentUser != null
        when (isSignedIn.value) {
            true -> {
                userEmail.value = Firebase.auth.currentUser?.email ?: ""
                userName.value = Firebase.auth.currentUser?.displayName ?: ""
            }
        }
    }

    val profileImageUrl: String =
        "https://lh3.googleusercontent.com/ogw/ADea4I7Sai6ixeWECnEqktIJ3iH_Vx9YwZyM26e2Whdn_A=s192-c-mo"
    var signInError: Boolean = false

    fun loginWithGoogle(context: Context) {
        val gso = GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
            .requestIdToken(context.getString(R.string.google_sign_in_token_id))
            .requestEmail()
            .requestProfile()
            .build()
        googleSignInClient = GoogleSignIn.getClient(context, gso)

        val signInIntent = googleSignInClient.signInIntent
        (context as Activity).startActivityForResult(signInIntent, SettingsActivity.RC_SIGN_IN)
    }

    fun signOutWithGoogle() {
        Firebase.auth.signOut()
        isSignedIn.value = false
    }

    fun firebaseAuthWithGoogle(idToken: String, activity: SettingsActivity) {
        val credential = GoogleAuthProvider.getCredential(idToken, null)
        Firebase.auth.signInWithCredential(credential)
            .addOnCompleteListener(activity) { task ->
                if (task.isSuccessful) {
                    userName.value = Firebase.auth.currentUser?.displayName ?: ""
                    userEmail.value = Firebase.auth.currentUser?.email ?: ""
                    isSignedIn.value = true
                } else {
                }
            }
    }
}
