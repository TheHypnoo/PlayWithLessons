package com.upitnik.playwithlessons.models.user

data class User(
    val alias: String? = null,
    val emailUser: String?,
    val passwordUser: String?,
    val uidUser: String?
) {
    val displayName = alias
    var email = emailUser
    var password = passwordUser
    val uid = uidUser
}