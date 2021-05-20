package com.upitnik.playwithlessons.data.remote.ranking

import com.upitnik.playwithlessons.data.model.authentication.User
import com.upitnik.playwithlessons.repository.WebService
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import retrofit2.await

class RankingDataSource {

    suspend fun getUsers(): List<User> {
        val listUsers: ArrayList<User> = arrayListOf()
        var users: List<User> = listOf()
        val call = WebService.RetrofitClient.webService.getUsers().await()
        withContext(Dispatchers.IO) {
            listUsers.clear()
            call.forEach { user ->
                listUsers.add(
                    User(
                        user.id,
                        user.uid,
                        user.email,
                        user.experience,
                        user.image,
                        user.nickname,
                        user.score
                    )
                )
                users = listUsers.toList()
            }
        }
        return users
    }
}