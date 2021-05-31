package com.upitnik.playwithlessons.repository

import com.google.gson.GsonBuilder
import com.upitnik.playwithlessons.application.AppConstants
import com.upitnik.playwithlessons.data.model.achievements.Achievements
import com.upitnik.playwithlessons.data.model.authentication.Images
import com.upitnik.playwithlessons.data.model.authentication.User
import com.upitnik.playwithlessons.data.model.levels.Levels
import com.upitnik.playwithlessons.data.model.questions.Question
import com.upitnik.playwithlessons.data.model.subject.Subject
import com.upitnik.playwithlessons.data.model.userQuestions
import retrofit2.Call
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.create
import retrofit2.http.*

interface WebService {
    //Aqui todas las llamadas a las API
    @GET("api/pwlimage")
    @Headers("Authorization: Bearer ${AppConstants.API_KEY}")
    fun getImages(): Call<List<Images>>

    @GET("api/pwlProgressandSubject/{uid}")
    @Headers("Authorization: Bearer ${AppConstants.API_KEY}")
    fun getSubjects(@Path("uid") uid: String): Call<List<Subject>>

    @GET("api/pwlUserspwl")
    @Headers("Authorization: Bearer ${AppConstants.API_KEY}")
    fun getUsers(): Call<List<User>>

    @GET("api/pwlUserspwl/{uid}")
    @Headers("Authorization: Bearer ${AppConstants.API_KEY}")
    fun getUser(@Path("uid") uid: String): Call<List<User>>

    @PUT("api/pwlUserspwl/{uid}")
    @Headers("Authorization: Bearer ${AppConstants.API_KEY}")
    fun putUser(@Path("uid") uid: String, @Body user: User): Call<Int>

    @GET("api/pwlAchievements")
    @Headers("Authorization: Bearer ${AppConstants.API_KEY}")
    fun getAchievements(): Call<List<Achievements>>

    @GET("api/pwlLevels/{uidUser}/{idSubject}")
    @Headers("Authorization: Bearer ${AppConstants.API_KEY}")
    fun getLevels(
        @Path("uidUser") uidUser: String,
        @Path("idSubject") idSubject: Int
    ): Call<List<Levels>>

    @PATCH("api/pwlUserQuestion/{idQuestion}/{uidUser}")
    @Headers("Authorization: Bearer ${AppConstants.API_KEY}")
    fun putQuestion(
        @Path("idQuestion") idQuestion: Int,
        @Path("uidUser") uidUser: String,
        @Body stagecorrect: userQuestions
    ): Call<Int>

    @PATCH("api/pwlUserStagecorrect/{uidUser}/{NumberLevel}/{idSubject}")
    @Headers("Authorization: Bearer ${AppConstants.API_KEY}")
    fun stageCorrectTo0(
        @Path("uidUser") uidUser: String,
        @Path("NumberLevel") NumberLevel: Int,
        @Path("idSubject") idSubject: Int
    ): Call<List<Question>>

    @PATCH("api/pwlsavelevels/{uidUser}/{numberLevel}/{idSubject}")
    @Headers("Authorization: Bearer ${AppConstants.API_KEY}")
    fun finishLevel(
        @Path("uidUser") uidUser: String,
        @Path("numberLevel") numberLevel: Int,
        @Path("idSubject") idSubject: Int
    ): Call<Int>

    @GET("api/pwlQuestions/{idLevel}/{idSubject}/{uidUser}")
    @Headers("Authorization: Bearer ${AppConstants.API_KEY}")
    fun getQuestions(
        @Path("idLevel") idLevel: Int,
        @Path("idSubject") idSubject: Int,
        @Path("uidUser") uidUser: String
    ): Call<List<Question>>

    //@Headers("Content-Type: application/json")
    @Headers("Authorization: Bearer ${AppConstants.API_KEY}")
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
