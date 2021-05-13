package com.upitnik.playwithlessons.domain.ranking

import com.upitnik.playwithlessons.data.model.auth.UserItem
import com.upitnik.playwithlessons.data.remote.ranking.RankingDataSource

class RankingRepoImpl(private val dataSource: RankingDataSource) : RankingRepo {

    override suspend fun getUsers(): List<UserItem> {
        return dataSource.getUsers()
    }

}