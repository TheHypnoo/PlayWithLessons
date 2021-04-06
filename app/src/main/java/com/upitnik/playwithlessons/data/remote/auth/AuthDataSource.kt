package com.upitnik.playwithlessons.data.remote.auth

import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import kotlinx.coroutines.tasks.await

class AuthDataSource {

    suspend fun signIn(email: String, password: String): FirebaseUser? {
        val authResult =
            FirebaseAuth.getInstance().signInWithEmailAndPassword(email, password).await()
        return authResult.user
    }

    suspend fun signUp(email: String, password: String, username: String): FirebaseUser? {
        val authResult =
            FirebaseAuth.getInstance().createUserWithEmailAndPassword(email, password).await()
        //API COMMUNICATION CREATE USER

        return authResult.user
    }
}