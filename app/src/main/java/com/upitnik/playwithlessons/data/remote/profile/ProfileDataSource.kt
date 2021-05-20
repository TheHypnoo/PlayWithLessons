package com.upitnik.playwithlessons.data.remote.profile

import com.google.firebase.auth.FirebaseAuth
import com.upitnik.playwithlessons.data.model.achievements.Achievements
import com.upitnik.playwithlessons.data.model.authentication.User
import com.upitnik.playwithlessons.repository.WebService
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import retrofit2.await

class ProfileDataSource {

    suspend fun getUser(): User {
        val auth = FirebaseAuth.getInstance().uid
        var user = User(0, "", "", 0, "", "", 0)
        val call = WebService.RetrofitClient.webService.getUsers().await()
        withContext(Dispatchers.IO) {
            call.forEach { userAPI ->
                if (userAPI.uid == auth) {
                    user = User(
                        userAPI.id,
                        userAPI.uid,
                        userAPI.email,
                        userAPI.experience,
                        userAPI.image,
                        userAPI.nickname,
                        userAPI.score
                    )
                }
            }
        }
        return user
    }

    suspend fun getAchievements(): List<Achievements> {
        val listAchievements: ArrayList<Achievements> = arrayListOf()
        var achievements: List<Achievements> = listOf()
        val call = WebService.RetrofitClient.webService.getAchievements().await()
        withContext(Dispatchers.IO) {
            listAchievements.clear()
            call.forEach { achievement ->
                listAchievements.add(
                    Achievements(
                        achievement.id,
                        achievement.image,
                        achievement.name,
                        achievement.score
                    )
                )
                achievements = listAchievements.toList()
            }
        }
        return achievements
    }
}