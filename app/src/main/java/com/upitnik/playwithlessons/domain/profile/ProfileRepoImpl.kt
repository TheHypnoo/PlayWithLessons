package com.upitnik.playwithlessons.domain.profile

import com.upitnik.playwithlessons.data.model.achievements.Achievements
import com.upitnik.playwithlessons.data.model.auth.UserItem
import com.upitnik.playwithlessons.data.remote.profile.ProfileDataSource

class ProfileRepoImpl(private val dataSource: ProfileDataSource) : ProfileRepo {

    override suspend fun getUsers(): UserItem {
        return dataSource.getUser()
    }

    override suspend fun getAchievements(): List<Achievements> {
        return dataSource.getAchievements()
    }

}