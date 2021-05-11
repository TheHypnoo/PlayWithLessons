package com.upitnik.playwithlessons.data.remote.MainMenu

import com.google.firebase.auth.FirebaseAuth
import com.upitnik.playwithlessons.data.model.auth.UserItem
import com.upitnik.playwithlessons.data.model.subject.SubjectsItem
import com.upitnik.playwithlessons.repository.WebService
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import retrofit2.await

class MainMenuDataSource {
    suspend fun getSubjects(): List<SubjectsItem> {
        val listSubjects: ArrayList<SubjectsItem> = arrayListOf()
        var Subjects: List<SubjectsItem> = listOf()
        val call = WebService.RetrofitClient.webService.getSubjects().await()
        withContext(Dispatchers.IO) {
            listSubjects.clear()
            call.forEach { subject ->
                listSubjects.add(
                    SubjectsItem(
                        subject.gamemode,
                        subject.id,
                        subject.image,
                        subject.name
                    )
                )
                Subjects = listSubjects.toList()
            }
        }

        /*  call.enqueue(object : Callback<Subjects> {

              override fun onFailure(call: Call<Subjects>, t: Throwable) {
                  t.message?.let {
                      println(it)

                  }


              }


              override fun onResponse(
                  call: Call<Subjects>?,
                  response: Response<Subjects>?
              ) {

                  if (!response!!.isSuccessful) {
                      println(response.code())

                      return

                  }
                  CoroutineScope(Dispatchers.IO).launch {
                      withContext(Dispatchers.IO) {
                          response.body()?.forEach {
                              listSubjects.add(
                                  SubjectsItem(
                                      it.gamemode,
                                      it.id,
                                      it.image,
                                      it.name
                                  )
                              )
                              Subjects = listSubjects.toList()
                          }

                          println("Metido 1 ${listSubjects[0]}")
                      }
                  }
              }
          })*/
        println(listSubjects)
        return Subjects
    }

    suspend fun getUser(): UserItem {
        val auth = FirebaseAuth.getInstance().uid
        var User = UserItem("", "", 0, "", "", 0, 0)
        val call = WebService.RetrofitClient.webService.getUsers().await()
        withContext(Dispatchers.IO) {
            call.forEach { user ->
                if (user.uid == auth) {
                    User = UserItem(
                        user.uid,
                        user.email,
                        user.experience,
                        user.image,
                        user.nickname,
                        user.question,
                        user.score
                    )
                }
            }
        }
        return User
    }
}