package com.upitnik.playwithlessons.domain.mainMenu

import com.upitnik.playwithlessons.data.model.subject.SubjectsItem

interface MainMenuRepo {
    suspend fun getSubjects(): ArrayList<SubjectsItem>
    suspend fun getUsers()
}