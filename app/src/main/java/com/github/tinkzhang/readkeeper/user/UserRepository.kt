package com.github.tinkzhang.readkeeper.user

import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase

class UserRepository{
    val user = Firebase.auth.currentUser
}