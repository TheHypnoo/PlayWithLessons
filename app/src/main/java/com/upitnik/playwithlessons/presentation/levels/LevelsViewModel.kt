package com.upitnik.playwithlessons.presentation.levels

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.liveData
import com.upitnik.playwithlessons.core.Result
import com.upitnik.playwithlessons.domain.levels.LevelsRepo
import kotlinx.coroutines.Dispatchers

class LevelsViewModel(private val repo: LevelsRepo) : ViewModel() {

    fun getLevels() = liveData(Dispatchers.IO) {
        emit(Result.Loading())
        try {
            emit(Result.Sucess(repo.getLevels()))
        } catch (e: Exception) {
            emit(Result.Failure(e))
        }
    }

    fun getSubjects() = liveData(Dispatchers.IO) {
        emit(Result.Loading())
        try {
            emit(Result.Sucess(repo.getLevels()))
        } catch (e: Exception) {
            emit(Result.Failure(e))
        }
    }
}

class LevelsViewModelFactory(private val repo: LevelsRepo) : ViewModelProvider.Factory {
    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        return LevelsViewModel(repo) as T
    }
}