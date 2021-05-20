package com.upitnik.playwithlessons.data.model.authentication

import java.io.Serializable


data class User(
    val id: Int,
    val uid: String,
    val email: String,
    val experience: Int,
    val image: String?,
    val nickname: String,
    val score: Int
): Serializable