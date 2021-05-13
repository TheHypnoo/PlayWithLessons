package com.upitnik.playwithlessons.data.remote.mainMenu

import com.google.firebase.auth.FirebaseAuth
import com.upitnik.playwithlessons.data.model.auth.UserItem
import com.upitnik.playwithlessons.data.model.difficulty.Difficulty
import com.upitnik.playwithlessons.data.model.levels.Levels
import com.upitnik.playwithlessons.data.model.progress.Progress
import com.upitnik.playwithlessons.data.model.subject.SubjectsItem
import com.upitnik.playwithlessons.repository.WebService
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import retrofit2.await

class MainMenuDataSource {
    suspend fun getSubjects(): List<SubjectsItem> {
        val listSubjects: ArrayList<SubjectsItem> = arrayListOf()
        var subjects: List<SubjectsItem> = listOf()
        val call = WebService.RetrofitClient.webService.getSubjects().await()
        withContext(Dispatchers.IO) {
            listSubjects.clear()
            call.forEach { subject ->
                listSubjects.add(
                    SubjectsItem(
                        subject.gamemode,
                        subject.id,
                        subject.image,
                        subject.name,
                        0,
                        ""
                    )
                )
                subjects = listSubjects.toList()
            }
        }
        return subjects
    }

    suspend fun getUser(): UserItem {
        val auth = FirebaseAuth.getInstance().uid
        var User = UserItem(0, "", "", 0, "", "", 0, 0)
        val call = WebService.RetrofitClient.webService.getUsers().await()
        withContext(Dispatchers.IO) {
            call.forEach { user ->
                if (user.uid == auth) {
                    User = UserItem(
                        user.id,
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

    suspend fun getProgress(): List<Progress> {
        val progressArrayList: ArrayList<Progress> = arrayListOf()
        var progressList: List<Progress> = listOf()
        val call = WebService.RetrofitClient.webService.getProgress().await()
        withContext(Dispatchers.IO) {
            progressArrayList.clear()
            call.forEach { progress ->
                progressArrayList.add(
                    Progress(progress.id, progress.progress, progress.subject, progress.user)
                )
                progressList = progressArrayList.toList()
            }
        }
        return progressList
    }

    suspend fun getLevels(): List<Levels> {
        val listLevels: ArrayList<Levels> = arrayListOf()
        var levels: List<Levels> = listOf()
        val call = WebService.RetrofitClient.webService.getLevels().await()
        withContext(Dispatchers.IO) {
            listLevels.clear()
            call.forEach { level ->
                listLevels.add(
                    Levels(level.difficulty, level.id, level.number, level.subject)
                )
                levels = listLevels.toList()
            }
        }
        return levels
    }

    suspend fun getDifficulty(): List<Difficulty> {
        val difficultyArrayList: ArrayList<Difficulty> = arrayListOf()
        var listDifficulty: List<Difficulty> = listOf()
        val call = WebService.RetrofitClient.webService.getDifficulty().await()
        withContext(Dispatchers.IO) {
            difficultyArrayList.clear()
            call.forEach { difficulty ->
                difficultyArrayList.add(
                    Difficulty(difficulty.id, difficulty.name)
                )
                listDifficulty = difficultyArrayList.toList()
            }
        }
        return listDifficulty
    }
}