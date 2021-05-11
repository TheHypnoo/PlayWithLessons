package com.upitnik.playwithlessons.presentation.profile

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.liveData
import com.upitnik.playwithlessons.core.Result
import com.upitnik.playwithlessons.domain.profile.ProfileRepo
import kotlinx.coroutines.Dispatchers

class ProfileViewModel(private val repo: ProfileRepo) : ViewModel() {

    fun getUser() = liveData(Dispatchers.IO) {
        emit(Result.Loading())
        try {
            emit(Result.Sucess(repo.getUsers()))
        } catch (e: Exception) {
            emit(Result.Failure(e))
        }
    }

    fun getAchievements() = liveData(Dispatchers.IO) {
        emit(Result.Loading())
        try {
            emit(Result.Sucess(repo.getAchievements()))
        } catch (e: Exception) {
            emit(Result.Failure(e))
        }
    }

}

class ProfileViewModelFactory(private val repo: ProfileRepo) : ViewModelProvider.Factory {
    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        return ProfileViewModel(repo) as T
    }
}