package com.upitnik.playwithlessons.data.remote.auth

import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import com.upitnik.playwithlessons.data.model.auth.ImagesRegisterItem
import com.upitnik.playwithlessons.data.model.auth.UserItem
import com.upitnik.playwithlessons.repository.WebService
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.tasks.await
import kotlinx.coroutines.withContext
import retrofit2.await

class AuthDataSource {

    suspend fun signIn(email: String, password: String): FirebaseUser? {
        val authResult =
            FirebaseAuth.getInstance().signInWithEmailAndPassword(email, password).await()
        return authResult.user
    }

    suspend fun signUp(
        email: String,
        password: String,
        nickname: String,
        image: String
    ): FirebaseUser? {
        val authResult =
            FirebaseAuth.getInstance().createUserWithEmailAndPassword(email, password).await()
        singUpApi(email, nickname, image)
        return authResult.user
    }

    private suspend fun singUpApi(email: String, nickname: String, image: String) {
        val User =
            UserItem(0,FirebaseAuth.getInstance().currentUser!!.uid, email, 0, image, nickname, 1, 0)
        WebService.RetrofitClient.webService.createUser(User).await()
    }

    fun signOut() {
        return FirebaseAuth.getInstance().signOut()
    }

    suspend fun getImages(): List<ImagesRegisterItem> {
        val listImages: ArrayList<ImagesRegisterItem> = arrayListOf()
        var images: List<ImagesRegisterItem> = listOf()
        val call = WebService.RetrofitClient.webService.getImages().await()
        withContext(Dispatchers.IO) {
            listImages.clear()
            call.forEach { image ->
                listImages.add(
                    ImagesRegisterItem(image.id, image.url)
                )
                images = listImages.toList()
            }
        }
        return images
    }
}