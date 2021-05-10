package com.upitnik.playwithlessons.repository

import com.google.gson.GsonBuilder
import com.upitnik.playwithlessons.application.AppConstants
import com.upitnik.playwithlessons.data.model.auth.ImagesRegister
import com.upitnik.playwithlessons.data.model.auth.UserItem
import com.upitnik.playwithlessons.data.model.subject.Subjects
import retrofit2.Call
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.*

interface WebService {
    //Aqui todas las llamadas a las API
    @GET("api/pwlimage")
    fun getImages(): Call<ImagesRegister>

    @GET("api/pwlSubject")
    fun getSubjects(): Call<Subjects>

    @GET("api/pwlUserspwl")
    fun getUsers(): Call<List<UserItem>>

    @Headers("Content-Type: application/json")
    @POST("api/pwlUserspwl")
    fun createUser(
        @Body userspwls: UserItem
    ): Call<UserItem>


    object RetrofitClient {
        val webService: WebService by lazy {
            Retrofit.Builder()
                .baseUrl(AppConstants.BASE_URL)
                .addConverterFactory(
                    GsonConverterFactory.create(
                        GsonBuilder().setLenient().create()
                    )
                )
                .build().create(WebService::class.java)
        }
    }
}
