package com.upitnik.playwithlessons.data.remote.auth

import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import com.upitnik.playwithlessons.data.model.auth.UserItem
import com.upitnik.playwithlessons.repository.WebService
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.tasks.await
import kotlinx.coroutines.withContext
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class AuthDataSource {

    suspend fun signIn(email: String, password: String): FirebaseUser? {
        val authResult =
            FirebaseAuth.getInstance().signInWithEmailAndPassword(email, password).await()
        return authResult.user
    }

    suspend fun signUp(
        email: String,
        password: String,
        username: String,
        image: String
    ): FirebaseUser? {
        val authResult =
            FirebaseAuth.getInstance().createUserWithEmailAndPassword(email, password).await()
        registerApi(email, username, image)
        return authResult.user
    }

    private suspend fun registerApi(email: String, username: String, image: String) {
        CoroutineScope(Dispatchers.IO).launch {
            withContext(Dispatchers.IO) {
                val call: Call<UserItem> =
                    WebService.RetrofitClient.webService.createUser(
                        UserItem(
                            FirebaseAuth.getInstance().currentUser!!.uid,
                            email,
                            0,
                            image,
                            username,
                            1,
                            0
                        )
                    )

                call.enqueue(object : Callback<UserItem> {

                    override fun onFailure(call: Call<UserItem>, t: Throwable) {
                        t.message?.let {
                            println("ERROR USER: $it")

                        }


                    }


                    override fun onResponse(
                        call: Call<UserItem>?,
                        response: Response<UserItem>?
                    ) {

                        if (!response!!.isSuccessful) {
                            println("ERROR USER NO SUCCES: " + response.code())

                            return

                        }

                        val cityList = response.body()

                        if (cityList != null) {
                            println(cityList)
                            println("user created!")
                        }
                    }
                })
            }
        }
    }

    fun signOut() {
        return FirebaseAuth.getInstance().signOut()
    }
}