package com.upitnik.playwithlessons.domain.auth

import com.google.firebase.auth.FirebaseUser
import com.upitnik.playwithlessons.data.model.authentication.Images
import com.upitnik.playwithlessons.data.remote.authentication.AuthDataSource

class AuthRepoImpl(private val dataSource: AuthDataSource) : AuthRepo {
    override suspend fun signIn(email: String, password: String): FirebaseUser? =
        dataSource.signIn(email, password)

    override suspend fun signUp(
        email: String,
        password: String,
        username: String,
        image: String?
    ): FirebaseUser? =
        dataSource.signUp(email, password, username, image!!)

    override suspend fun signOut() {
        dataSource.signOut()
    }

    override suspend fun getImages(): List<Images> {
        return dataSource.getImages()
    }
}