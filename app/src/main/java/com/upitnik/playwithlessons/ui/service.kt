package com.upitnik.playwithlessons.ui

import com.upitnik.playwithlessons.data.model.Courses
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Url

interface service {

    @GET
    fun getJSON(@Url url: String): Call<Courses>
}