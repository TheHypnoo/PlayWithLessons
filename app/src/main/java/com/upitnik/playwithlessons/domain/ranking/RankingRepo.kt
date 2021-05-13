package com.upitnik.playwithlessons.domain.ranking

import com.upitnik.playwithlessons.data.model.auth.UserItem

interface RankingRepo {
    suspend fun getUsers(): List<UserItem>
}