package com.upitnik.playwithlessons.domain.mainMenu

import com.upitnik.playwithlessons.data.model.auth.UserItem
import com.upitnik.playwithlessons.data.model.difficulty.Difficulty
import com.upitnik.playwithlessons.data.model.levels.Levels
import com.upitnik.playwithlessons.data.model.progress.Progress
import com.upitnik.playwithlessons.data.model.subject.SubjectsItem
import com.upitnik.playwithlessons.data.remote.mainMenu.MainMenuDataSource

class MainMenuRepoImpl(private val dataSource: MainMenuDataSource) : MainMenuRepo {
    override suspend fun getSubjects(): List<SubjectsItem> {
        return dataSource.getSubjects()
    }

    override suspend fun getUsers(): UserItem {
        return dataSource.getUser()
    }

    override suspend fun getProgress(): List<Progress> {
        return dataSource.getProgress()
    }

    override suspend fun getLevels(): List<Levels> {
        return dataSource.getLevels()
    }

    override suspend fun getDifficulty(): List<Difficulty> {
        return dataSource.getDifficulty()
    }

}