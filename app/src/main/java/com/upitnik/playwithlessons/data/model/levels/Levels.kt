package com.upitnik.playwithlessons.data.model.levels


import com.google.gson.annotations.SerializedName
import java.io.Serializable

data class Levels(
    @SerializedName("difficult_id")
    val difficultId: Int,
    @SerializedName("finished")
    val finished: Int,
    @SerializedName("id")
    val id: Int,
    @SerializedName("number")
    val number: Int,
    @SerializedName("subject_id")
    val subjectId: Int
) : Serializable