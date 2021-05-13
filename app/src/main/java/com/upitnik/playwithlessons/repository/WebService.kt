package com.upitnik.playwithlessons.repository

import com.google.gson.GsonBuilder
import com.upitnik.playwithlessons.application.AppConstants
import com.upitnik.playwithlessons.data.model.achievements.Achievements
import com.upitnik.playwithlessons.data.model.auth.ImagesRegisterItem
import com.upitnik.playwithlessons.data.model.auth.UserItem
import com.upitnik.playwithlessons.data.model.difficulty.Difficulty
import com.upitnik.playwithlessons.data.model.levels.Levels
import com.upitnik.playwithlessons.data.model.progress.Progress
import com.upitnik.playwithlessons.data.model.subject.SubjectsItem
import retrofit2.Call
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.create
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.Headers
import retrofit2.http.POST

interface WebService {
    //Aqui todas las llamadas a las API
    @GET("api/pwlimage")
    fun getImages(): Call<List<ImagesRegisterItem>>

    @GET("api/pwlSubject")
    fun getSubjects(): Call<List<SubjectsItem>>

    @GET("api/pwlProgress")
    fun getProgress(): Call<List<Progress>>

    @GET("api/pwlUserspwl")
    fun getUsers(): Call<List<UserItem>>

    @GET("api/pwlAchievements")
    fun getAchievements(): Call<List<Achievements>>

    @GET("api/pwlLevels")
    fun getLevels(): Call<List<Levels>>

    @GET("api/pwlDifficulty")
    fun getDifficulty(): Call<List<Difficulty>>

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
                .build().create()
        }
    }
}
