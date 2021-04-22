package com.upitnik.playwithlessons.ui.mainMenu

import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import com.upitnik.playwithlessons.R
import com.upitnik.playwithlessons.data.model.Courses
import com.upitnik.playwithlessons.databinding.FragmentMainMenuBinding
import com.upitnik.playwithlessons.repository.WebService
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory


class MainMenu : Fragment(R.layout.fragment_main_menu) {
    val BASEAPI = "http://10.0.2.2:8000/"
    private lateinit var binding:FragmentMainMenuBinding
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding = FragmentMainMenuBinding.bind(view)
        val retrofit = Retrofit.Builder()
            .baseUrl(BASEAPI)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
        val httpService = retrofit.create(WebService::class.java)

        val call: Call<Courses> =
            httpService.getJSON("api/pwlassignatura")

        call.enqueue(object : Callback<Courses> {

            override fun onFailure(call: Call<Courses>, t: Throwable) {
                t.message?.let {
                    println(it)

                }


            }


            override fun onResponse(call: Call<Courses>?, response: Response<Courses>?) {

                if (!response!!.isSuccessful) {
                    println(response.code())

                    return

                }

                val cityList = response.body()

                if (cityList != null) {
                    println("ID: ${cityList.id} Name: ${cityList.name}")


                }
            }
        })
        binding.btnSignInEmail.setOnClickListener {
            findNavController().navigate(R.id.action_mainMenu_to_activityPrincipal)
        }
    }


}