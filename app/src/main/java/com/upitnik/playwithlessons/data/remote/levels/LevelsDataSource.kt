package com.upitnik.playwithlessons.data.remote.levels

import com.upitnik.playwithlessons.data.model.levels.Levels
import com.upitnik.playwithlessons.repository.WebService
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import retrofit2.await

class LevelsDataSource {

    suspend fun getLevels(subject: Int): List<Levels> {
        val listLevels: ArrayList<Levels> = arrayListOf()
        var levels: List<Levels> = listOf()
        val call = WebService.RetrofitClient.webService.getLevels(subject).await()
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
}