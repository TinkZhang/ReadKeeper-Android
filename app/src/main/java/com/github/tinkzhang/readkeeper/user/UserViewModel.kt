package com.github.tinkzhang.readkeeper.user

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.github.tinkzhang.basic.UserRepository
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class UserViewModel @Inject constructor(
    private val userRepository: UserRepository
) : ViewModel() {

    val isSignedIn: MutableLiveData<Boolean> by lazy {
        MutableLiveData<Boolean>()
    }

    val userEmail: MutableLiveData<String> by lazy {
        MutableLiveData<String>()
    }

    val userName: MutableLiveData<String> by lazy {
        MutableLiveData<String>()
    }

    val userProfileImageUrl: MutableLiveData<String> by lazy {
        MutableLiveData<String>()
    }

    val signInError: MutableLiveData<Boolean> by lazy {
        MutableLiveData<Boolean>(false)
    }

    val user = Firebase.auth.currentUser

    init {
        isSignedIn.value = Firebase.auth.currentUser != null
        when (isSignedIn.value) {
            true -> {
                syncUserInfo()
            }
            else -> {}
        }
    }

//    fun signInWithGoogle(context: Context){
//        userRepository.signInWithGoogle(context)
//    }
//
//    fun signOutWithGoogle() {
//        userRepository.signOutWithGoogle()
//        isSignedIn.value = false
//    }

    private fun syncUserInfo(){
        user?.let{
            signInError.value = false
            userName.value = it.displayName
            userEmail.value = it.email
            userProfileImageUrl.value = it.photoUrl.toString()
        }
    }
}
