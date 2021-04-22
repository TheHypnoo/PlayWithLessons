package com.upitnik.playwithlessons.repository

import com.google.gson.GsonBuilder
import com.upitnik.playwithlessons.application.AppConstants
import com.upitnik.playwithlessons.data.model.Courses
import retrofit2.Call
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.GET
import retrofit2.http.Url

interface WebService {
    //Aqui todas las llamadas a las API
    @GET
    fun getJSON(@Url url: String): Call<Courses>

    object RetrofitClient {
        val webService by lazy {
            Retrofit.Builder()
                .baseUrl(AppConstants.BASE_URL)
                .addConverterFactory(GsonConverterFactory.create(GsonBuilder().create()))
                .build().create(WebService::class.java)
        }
    }
}
