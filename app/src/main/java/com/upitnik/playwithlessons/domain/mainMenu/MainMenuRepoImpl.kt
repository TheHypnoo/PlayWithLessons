package com.upitnik.playwithlessons.domain.mainMenu

import com.upitnik.playwithlessons.data.model.auth.UserItem
import com.upitnik.playwithlessons.data.model.subject.SubjectsItem
import com.upitnik.playwithlessons.data.remote.mainMenu.MainMenuDataSource

class MainMenuRepoImpl(private val dataSource: MainMenuDataSource) : MainMenuRepo {
    override suspend fun getSubjects(): List<SubjectsItem> {
        return dataSource.getSubjects()
    }

    override suspend fun getUsers(): UserItem {
        return dataSource.getUser()
    }

}