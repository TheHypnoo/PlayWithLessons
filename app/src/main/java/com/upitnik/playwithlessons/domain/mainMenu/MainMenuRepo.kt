package com.upitnik.playwithlessons.domain.mainMenu

import com.upitnik.playwithlessons.data.model.auth.UserItem
import com.upitnik.playwithlessons.data.model.subject.SubjectsItem

interface MainMenuRepo {
    suspend fun getSubjects(): List<SubjectsItem>
    suspend fun getUsers(): UserItem
}