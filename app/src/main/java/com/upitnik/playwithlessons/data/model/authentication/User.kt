package com.upitnik.playwithlessons.data.model.authentication

import java.io.Serializable


data class User(
    val id: Int,
    val uid: String,
    val email: String,
    var experience: Int,
    val image: String?,
    val nickname: String,
    var score: Int
): Serializable