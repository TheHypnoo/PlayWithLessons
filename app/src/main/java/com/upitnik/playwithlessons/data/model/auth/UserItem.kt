package com.upitnik.playwithlessons.data.model.auth

import java.io.Serializable


data class UserItem(
    val id: Int,
    val uid: String,
    val email: String,
    val experience: Int,
    val image: String?,
    val nickname: String,
    val score: Int
): Serializable