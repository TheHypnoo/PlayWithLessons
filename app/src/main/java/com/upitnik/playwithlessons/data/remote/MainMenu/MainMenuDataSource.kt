package com.upitnik.playwithlessons.data.remote.MainMenu

import com.upitnik.playwithlessons.data.model.subject.Subjects
import com.upitnik.playwithlessons.data.model.subject.SubjectsItem
import com.upitnik.playwithlessons.repository.WebService
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class MainMenuDataSource {
    val listSubjects: ArrayList<SubjectsItem> = arrayListOf()
    fun getSubjects(): ArrayList<SubjectsItem> {
        CoroutineScope(Dispatchers.IO).launch {
            withContext(Dispatchers.IO){
            val call: Call<Subjects> =
                WebService.RetrofitClient.webService.getSubjects()

            call.enqueue(object : Callback<Subjects> {

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

                    response.body()?.forEach {
                        listSubjects.add(
                            SubjectsItem(
                                it.gamemode,
                                it.id,
                                it.image,
                                it.name
                            )
                        )
                        println("Metido 1 ${listSubjects[0]}")
                    }
                }
            }
            )
            }
        }
        println(listSubjects)
        return listSubjects
    }
}