package com.upitnik.playwithlessons.domain.mainMenu

import com.upitnik.playwithlessons.data.model.authentication.User
import com.upitnik.playwithlessons.data.model.subject.Subject

interface MainMenuRepo {
    suspend fun getSubjects(uid: String): List<Subject>
    suspend fun getUsers(): User
}