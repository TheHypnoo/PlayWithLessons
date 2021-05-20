package com.upitnik.playwithlessons.domain.ranking

import com.upitnik.playwithlessons.data.model.authentication.User
import com.upitnik.playwithlessons.data.remote.ranking.RankingDataSource

class RankingRepoImpl(private val dataSource: RankingDataSource) : RankingRepo {

    override suspend fun getUsers(): List<User> {
        return dataSource.getUsers()
    }

}