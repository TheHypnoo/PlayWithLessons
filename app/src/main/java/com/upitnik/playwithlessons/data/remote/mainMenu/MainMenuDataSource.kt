package com.upitnik.playwithlessons.data.remote.mainMenu

import com.google.firebase.auth.FirebaseAuth
import com.upitnik.playwithlessons.data.model.authentication.User
import com.upitnik.playwithlessons.data.model.subject.Subject
import com.upitnik.playwithlessons.repository.WebService
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import retrofit2.await

class MainMenuDataSource {

    suspend fun getSubjects(uid: String): List<Subject> {
        val listSubjects: ArrayList<Subject> = arrayListOf()
        var subjects: List<Subject> = listOf()
        val call = WebService.RetrofitClient.webService.getSubjects(uid).await()
        withContext(CoroutineScope(Dispatchers.IO).coroutineContext) {
            listSubjects.clear()
            call.forEach { subject ->
                listSubjects.add(
                    Subject(
                        subject.gamemode_id,
                        subject.id,
                        subject.image,
                        subject.name,
                        subject.number,
                        subject.pwluser_id,
                        subject.subject_id
                    )
                )
                subjects = listSubjects.toList()
            }
        }
        return subjects
    }

    suspend fun getUser(): User {
        val auth = FirebaseAuth.getInstance().uid
        var user = User(0, "", "", 0, "", "", 0)
        val call = WebService.RetrofitClient.webService.getUsers().await()
        withContext(CoroutineScope(Dispatchers.IO).coroutineContext) {
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

}