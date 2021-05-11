package com.upitnik.playwithlessons.presentation.mainMenu

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.liveData
import com.upitnik.playwithlessons.core.Result
import com.upitnik.playwithlessons.domain.mainMenu.MainMenuRepo
import kotlinx.coroutines.Dispatchers

class MainMenuViewModel(private val repo: MainMenuRepo) : ViewModel() {

    fun getSubjects() = liveData(Dispatchers.IO) {
        emit(Result.Loading())
        try {
            emit(Result.Sucess(repo.getSubjects()))
        } catch (e: Exception) {
            emit(Result.Failure(e))
        }
    }
}

class MainMenuViewModelFactory(private val repo: MainMenuRepo) : ViewModelProvider.Factory {
    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        return MainMenuViewModel(repo) as T
    }
}