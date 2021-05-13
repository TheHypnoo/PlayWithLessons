package com.upitnik.playwithlessons.data.remote.levels

import com.upitnik.playwithlessons.data.model.levels.Levels
import com.upitnik.playwithlessons.data.model.subject.SubjectsItem
import com.upitnik.playwithlessons.repository.WebService
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import retrofit2.await

class LevelsDataSource {
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
}