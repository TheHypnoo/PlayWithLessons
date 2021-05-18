package com.upitnik.playwithlessons.data.model.levels

import java.io.Serializable


data class Levels(
    val difficulty: Int,
    val id: Int,
    val number: Int,
    val subject: Int
) : Serializable