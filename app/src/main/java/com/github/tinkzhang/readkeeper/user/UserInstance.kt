package com.github.tinkzhang.readkeeper.user

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

class UserViewModel: ViewModel() {
    val isLoggedIn: MutableLiveData<Boolean> by lazy {
        MutableLiveData<Boolean>(false)
    }

    val profileImageUrl: String = "https://lh3.googleusercontent.com/ogw/ADea4I7Sai6ixeWECnEqktIJ3iH_Vx9YwZyM26e2Whdn_A=s192-c-mo"
    val userName: String = "Yunfeng Zhang"
    val userEmail: String = "ZhangYunfengZJU@gmail.com"
    fun logout() {
        isLoggedIn.value = false
    }

    fun login() {
        isLoggedIn.value = true
    }
}
