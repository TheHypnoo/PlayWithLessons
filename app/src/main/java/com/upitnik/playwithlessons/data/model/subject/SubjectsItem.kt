package com.upitnik.playwithlessons.data.model.subject

import java.io.Serializable


data class SubjectsItem(
    val gamemode: Int,
    val id: Int,
    val image: String,
    val name: String,
    var progress: Int,
    val difficulty: String
) : Serializable