package com.upitnik.playwithlessons.domain.levels

import com.upitnik.playwithlessons.data.model.levels.Levels

interface LevelsRepo {
    suspend fun getLevels(subject: Int): List<Levels>
}