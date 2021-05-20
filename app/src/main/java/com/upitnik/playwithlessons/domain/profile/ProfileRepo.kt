package com.upitnik.playwithlessons.domain.profile

import com.upitnik.playwithlessons.data.model.achievements.Achievements
import com.upitnik.playwithlessons.data.model.authentication.User

interface ProfileRepo {
    suspend fun getUsers(): User
    suspend fun getAchievements(): List<Achievements>
}