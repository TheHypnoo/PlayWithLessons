package com.upitnik.playwithlessons.domain.profile

import com.upitnik.playwithlessons.data.model.achievements.Achievements
import com.upitnik.playwithlessons.data.model.auth.UserItem

interface ProfileRepo {
    suspend fun getUsers(): UserItem
    suspend fun getAchievements(): List<Achievements>
}