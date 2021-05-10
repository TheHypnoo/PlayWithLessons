package com.upitnik.playwithlessons.data.model.auth

import java.io.Serializable


data class UserItem(
    val uid: String,
    val email: String,
    val experience: Int,
    val image: String,
    val nickname: String,
    val question: Int,
    val score: Int
): Serializable