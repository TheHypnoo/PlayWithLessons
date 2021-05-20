package com.upitnik.playwithlessons.domain.mainMenu

import com.upitnik.playwithlessons.data.model.authentication.User
import com.upitnik.playwithlessons.data.model.subject.Subject
import com.upitnik.playwithlessons.data.remote.mainMenu.MainMenuDataSource

class MainMenuRepoImpl(private val dataSource: MainMenuDataSource) : MainMenuRepo {
    override suspend fun getSubjects(uid: String): List<Subject> {
        return dataSource.getSubjects(uid)
    }

    override suspend fun getUsers(): User {
        return dataSource.getUser()
    }


}