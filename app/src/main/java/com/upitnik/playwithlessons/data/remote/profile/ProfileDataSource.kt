package com.upitnik.playwithlessons.data.remote.profile

import com.google.firebase.auth.FirebaseAuth
import com.upitnik.playwithlessons.data.model.achievements.Achievements
import com.upitnik.playwithlessons.data.model.auth.UserItem
import com.upitnik.playwithlessons.repository.WebService
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import retrofit2.await

class ProfileDataSource {

    suspend fun getUser(): UserItem {
        val auth = FirebaseAuth.getInstance().uid
        var User = UserItem("", "", 0, "", "", 0, 0)
        val call = WebService.RetrofitClient.webService.getUsers().await()
        withContext(Dispatchers.IO) {
            call.forEach { user ->
                if (user.uid == auth) {
                    User = UserItem(
                        user.uid,
                        user.email,
                        user.experience,
                        user.image,
                        user.nickname,
                        user.question,
                        user.score
                    )
                }
            }
        }
        return User
    }

    suspend fun getAchievements(): List<Achievements> {
        val listAchievements: ArrayList<Achievements> = arrayListOf()
        var Achievements: List<Achievements> = listOf()
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
                Achievements = listAchievements.toList()
            }
        }
        println(listAchievements)
        return Achievements
    }
}