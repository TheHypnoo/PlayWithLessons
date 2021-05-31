package com.upitnik.playwithlessons.data.remote.levels

import android.annotation.SuppressLint
import com.google.firebase.auth.FirebaseAuth
import com.upitnik.playwithlessons.data.model.levels.Levels
import com.upitnik.playwithlessons.repository.WebService
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import retrofit2.await

class LevelsDataSource {

    @SuppressLint("NewApi")
    suspend fun getLevels(subject: Int): List<Levels> {
        val listLevels: ArrayList<Levels> = arrayListOf()
        var levels: List<Levels> = listOf()
        val call = WebService.RetrofitClient.webService.getLevels(
            FirebaseAuth.getInstance().currentUser!!.uid,
            subject
        ).await()
        withContext(Dispatchers.IO) {
            listLevels.clear()
            call.forEach { level ->
                listLevels.add(
                    Levels(
                        level.difficultId,
                        level.finished,
                        level.id,
                        level.number,
                        level.subjectId
                    )
                )
                levels = listLevels.toList()
            }
        }
        return levels
    }
}