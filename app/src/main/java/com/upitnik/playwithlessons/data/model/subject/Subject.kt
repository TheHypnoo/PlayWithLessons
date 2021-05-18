package com.upitnik.playwithlessons.data.model.subject

import java.io.Serializable

data class Subject(
    val gamemode_id: Int,
    val id: Int,
    val image: String,
    val name: String,
    val number: Int,
    val pwluser_id: Int,
    val subject_id: Int
) : Serializable