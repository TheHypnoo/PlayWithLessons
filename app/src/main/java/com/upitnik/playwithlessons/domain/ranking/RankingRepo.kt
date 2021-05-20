package com.upitnik.playwithlessons.domain.ranking

import com.upitnik.playwithlessons.data.model.authentication.User

interface RankingRepo {
    suspend fun getUsers(): List<User>
}