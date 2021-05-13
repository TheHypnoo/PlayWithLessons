package com.upitnik.playwithlessons.domain.levels

import com.upitnik.playwithlessons.data.model.levels.Levels
import com.upitnik.playwithlessons.data.model.subject.SubjectsItem

interface LevelsRepo {
    suspend fun getLevels(): List<Levels>
    suspend fun getSubjects(): List<SubjectsItem>
}