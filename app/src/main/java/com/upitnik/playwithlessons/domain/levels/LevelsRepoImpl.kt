package com.upitnik.playwithlessons.domain.levels

import com.upitnik.playwithlessons.data.model.levels.Levels
import com.upitnik.playwithlessons.data.remote.levels.LevelsDataSource

class LevelsRepoImpl(private val dataSource: LevelsDataSource) : LevelsRepo {
    override suspend fun getLevels(subject: Int): List<Levels> {
        return dataSource.getLevels(subject)
    }

}