package com.upitnik.playwithlessons.domain.mainMenu

import com.upitnik.playwithlessons.data.model.auth.UserItem
import com.upitnik.playwithlessons.data.model.difficulty.Difficulty
import com.upitnik.playwithlessons.data.model.levels.Levels
import com.upitnik.playwithlessons.data.model.progress.Progress
import com.upitnik.playwithlessons.data.model.subject.SubjectsItem

interface MainMenuRepo {
    suspend fun getSubjects(): List<SubjectsItem>
    suspend fun getUsers(): UserItem
    suspend fun getLevels(): List<Levels>
    suspend fun getProgress(): List<Progress>
    suspend fun getDifficulty(): List<Difficulty>
}