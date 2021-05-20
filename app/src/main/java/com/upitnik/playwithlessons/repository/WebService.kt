package com.upitnik.playwithlessons.repository

import com.google.gson.GsonBuilder
import com.upitnik.playwithlessons.application.AppConstants
import com.upitnik.playwithlessons.data.model.achievements.Achievements
import com.upitnik.playwithlessons.data.model.authentication.Images
import com.upitnik.playwithlessons.data.model.authentication.User
import com.upitnik.playwithlessons.data.model.difficults.Difficults
import com.upitnik.playwithlessons.data.model.levels.Levels
import com.upitnik.playwithlessons.data.model.questions.Question
import com.upitnik.playwithlessons.data.model.subject.Subject
import retrofit2.Call
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.create
import retrofit2.http.*

interface WebService {
    //Aqui todas las llamadas a las API
    @GET("api/pwlimage")
    fun getImages(): Call<List<Images>>

    @GET("api/pwlProgressandSubject/{uid}")
    fun getSubjects(@Path("uid") uid: String): Call<List<Subject>>

    @GET("api/pwlUserspwl")
    fun getUsers(): Call<List<User>>

    @GET("api/pwlAchievements")
    fun getAchievements(): Call<List<Achievements>>

    @GET("api/pwlLevels/{id}")
    fun getLevels(@Path("id") id: Int?): Call<List<Levels>>

    @GET("api/pwlDifficulty")
    fun getDifficulty(): Call<List<Difficults>>
    //NO SE CUANDO HARE ESTO VALE?!

    @PUT("api/pwlQuestions/{id}")
    fun putQuestion(@Path("id") id: Int?, @Body question: Question): Call<Question>

    @Headers("Content-Type: application/json")
    @GET("api/pwlQuestions/{idLevel}/{idSubject}")
    fun getQuestions(
        @Path("idLevel") idLevel: Int,
        @Path("idSubject") idSubject: Int
    ): Call<List<Question>>

    @Headers("Content-Type: application/json")
    @POST("api/pwlUserAndProgress")
    fun createUser(
        @Body userspwls: User
    ): Call<User>

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
