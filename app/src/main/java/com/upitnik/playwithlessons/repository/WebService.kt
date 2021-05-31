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
import com.upitnik.playwithlessons.data.model.userQuestions
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

    @GET("api/pwlUserspwl/{uid}")
    fun getUser(@Path("uid") uid: String): Call<List<User>>

    @PUT("api/pwlUserspwl/{uid}")
    fun putUser(@Path("uid") uid: String, @Body user: User): Call<Int>

    @GET("api/pwlAchievements")
    fun getAchievements(): Call<List<Achievements>>

    @GET("api/pwlLevels/{uidUser}/{idSubject}")
    fun getLevels(
        @Path("uidUser") uidUser: String,
        @Path("idSubject") idSubject: Int
    ): Call<List<Levels>>

    @GET("api/pwlDifficulty")
    fun getDifficulty(): Call<List<Difficults>>
    //NO SE CUANDO HARE ESTO VALE?!

    @PATCH("api/pwlUserQuestion/{idQuestion}/{uidUser}")
    fun putQuestion(
        @Path("idQuestion") idQuestion: Int,
        @Path("uidUser") uidUser: String,
        @Body stagecorrect: userQuestions
    ): Call<Int>

    @PATCH("api/pwlUserStagecorrect/{uidUser}/{NumberLevel}/{idSubject}")
    fun stageCorrectTo0(
        @Path("uidUser") uidUser: String,
        @Path("NumberLevel") NumberLevel: Int,
        @Path("idSubject") idSubject: Int
    ): Call<List<Question>>

    @PATCH("api/pwlsavelevels/{uidUser}/{numberLevel}/{idSubject}")
    fun finishLevel(
        @Path("uidUser") uidUser: String,
        @Path("numberLevel") numberLevel: Int,
        @Path("idSubject") idSubject: Int
    ): Call<Int>

    @Headers("Content-Type: application/json")
    @GET("api/pwlQuestions/{idLevel}/{idSubject}/{uidUser}")
    fun getQuestions(
        @Path("idLevel") idLevel: Int,
        @Path("idSubject") idSubject: Int,
        @Path("uidUser") uidUser: String
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
