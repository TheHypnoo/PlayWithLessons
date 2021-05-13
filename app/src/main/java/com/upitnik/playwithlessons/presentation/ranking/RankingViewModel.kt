package com.upitnik.playwithlessons.presentation.ranking

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.liveData
import com.upitnik.playwithlessons.core.Result
import com.upitnik.playwithlessons.domain.ranking.RankingRepo
import kotlinx.coroutines.Dispatchers

class RankingViewModel(private val repo: RankingRepo) : ViewModel() {

    fun getUsers() = liveData(Dispatchers.IO) {
        emit(Result.Loading())
        try {
            emit(Result.Sucess(repo.getUsers()))
        } catch (e: Exception) {
            emit(Result.Failure(e))
        }
    }

}

class RankingViewModelFactory(private val repo: RankingRepo) : ViewModelProvider.Factory {
    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        return RankingViewModel(repo) as T
    }
}