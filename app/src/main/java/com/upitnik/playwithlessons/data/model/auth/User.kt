package com.upitnik.playwithlessons.data.model.auth

data class User(val email: String = "", val username: String = "", val photo_url: String = "", val score: Int = 0)
data class imageUser(val photo_url: String)
data class Achievement(val name: String = "", val image: String = "", val score: Int)
