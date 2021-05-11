package com.upitnik.playwithlessons.domain.mainMenu

import com.upitnik.playwithlessons.data.model.subject.SubjectsItem
import com.upitnik.playwithlessons.data.remote.MainMenu.MainMenuDataSource

class MainMenuRepoImpl(private val dataSource: MainMenuDataSource) : MainMenuRepo {
    override suspend fun getSubjects(): ArrayList<SubjectsItem> {
        return dataSource.getSubjects()
    }

    override suspend fun getUsers() {
        TODO("Not yet implemented")
    }

}