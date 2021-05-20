package com.upitnik.playwithlessons.domain.profile

import com.upitnik.playwithlessons.data.model.achievements.Achievements
import com.upitnik.playwithlessons.data.model.authentication.User
import com.upitnik.playwithlessons.data.remote.profile.ProfileDataSource

class ProfileRepoImpl(private val dataSource: ProfileDataSource) : ProfileRepo {

    override suspend fun getUsers(): User {
        return dataSource.getUser()
    }

    override suspend fun getAchievements(): List<Achievements> {
        return dataSource.getAchievements()
    }

}