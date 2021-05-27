package com.upitnik.playwithlessons.presentation.auth

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.liveData
import com.upitnik.playwithlessons.core.Result
import com.upitnik.playwithlessons.domain.auth.AuthRepo
import kotlinx.coroutines.Dispatchers

class AuthViewModel(private val repo: AuthRepo): ViewModel(){

    fun signIn(email: String, password: String) = liveData(Dispatchers.IO) {
        emit(Result.Loading())
        try {
            emit(Result.Sucess(repo.signIn(email, password)))
        } catch (e: Exception) {
            emit(Result.Failure(e))
        }
    }

    fun signUp(email: String, password: String, username: String, image: String?) =
        liveData(Dispatchers.IO) {
            emit(Result.Loading())
            try {
                emit(Result.Sucess(repo.signUp(email, password, username, image!!)))
            } catch (e: Exception) {
                emit(Result.Failure(e))
            }
        }

    fun signOut() = liveData(Dispatchers.IO) {
        emit(Result.Loading())
        try {
            emit(Result.Sucess(repo.signOut()))
        } catch (e: Exception) {
            emit(Result.Failure(e))
        }
    }

    fun getImages() = liveData(Dispatchers.IO) {
        emit(Result.Loading())
        try {
            emit(Result.Sucess(repo.getImages()))
        } catch (e: Exception) {
            emit(Result.Failure(e))
        }
    }

}

class AuthViewModelFactory(private val repo:AuthRepo): ViewModelProvider.Factory{
    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        return AuthViewModel(repo) as T
    }
}