package com.upitnik.playwithlessons.domain.auth

import com.google.firebase.auth.FirebaseUser
import com.upitnik.playwithlessons.data.model.auth.ImagesRegisterItem

interface AuthRepo {
    suspend fun signIn(email: String, password: String): FirebaseUser?
    suspend fun signUp(
        email: String,
        password: String,
        username: String,
        image: String
    ): FirebaseUser?

    suspend fun signOut()
    suspend fun getImages(): List<ImagesRegisterItem>
}