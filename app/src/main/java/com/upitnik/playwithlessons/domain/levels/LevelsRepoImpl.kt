package com.upitnik.playwithlessons.domain.levels

import com.upitnik.playwithlessons.data.model.levels.Levels
import com.upitnik.playwithlessons.data.model.subject.SubjectsItem
import com.upitnik.playwithlessons.data.remote.levels.LevelsDataSource

class LevelsRepoImpl(private val dataSource: LevelsDataSource) : LevelsRepo {
    override suspend fun getLevels(): List<Levels> {
        return dataSource.getLevels()
    }

    override suspend fun getSubjects(): List<SubjectsItem> {
        return dataSource.getSubjects()
    }
}