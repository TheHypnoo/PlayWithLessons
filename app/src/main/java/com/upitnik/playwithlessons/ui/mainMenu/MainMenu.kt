package com.upitnik.playwithlessons.ui.mainMenu

import android.content.ActivityNotFoundException
import android.content.Intent
import android.graphics.Color
import android.net.Uri
import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.findNavController
import androidx.navigation.fragment.findNavController
import com.google.android.material.snackbar.Snackbar
import com.upitnik.playwithlessons.R
import com.upitnik.playwithlessons.data.model.Courses
import com.upitnik.playwithlessons.data.model.subject.Subject
import com.upitnik.playwithlessons.data.remote.auth.AuthDataSource
import com.upitnik.playwithlessons.databinding.FragmentMainMenuBinding
import com.upitnik.playwithlessons.domain.auth.AuthRepoImpl
import com.upitnik.playwithlessons.presentation.auth.AuthViewModel
import com.upitnik.playwithlessons.presentation.auth.AuthViewModelFactory
import com.upitnik.playwithlessons.repository.WebService
import com.upitnik.playwithlessons.ui.Subjects.OnSubjectActionListener
import com.upitnik.playwithlessons.ui.Subjects.SubjectAdapter
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response


class MainMenu : Fragment(R.layout.fragment_main_menu), OnSubjectActionListener {
    private lateinit var binding: FragmentMainMenuBinding
    private val viewModel by viewModels<AuthViewModel> {
        AuthViewModelFactory(
            AuthRepoImpl(
                AuthDataSource()
            )
        )
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding = FragmentMainMenuBinding.bind(view)
        binding.btnPlayGame.setOnClickListener {
            findNavController().navigate(R.id.action_mainMenu_to_selectSubject)
        }
        binding.btnRanking.setOnClickListener {
            findNavController().navigate(R.id.action_mainMenu_to_Ranking)
        }
        binding.mcvUser.setOnClickListener {
            findNavController().navigate(R.id.action_mainMenu_to_Profile)
        }
        binding.civInstagram.setOnClickListener {
            val uri: Uri = Uri.parse("http://instagram.com/_u/upitnikofficial")
            val intent = Intent(Intent.ACTION_VIEW, uri)
            intent.setPackage("com.instagram.android")

            try {
                startActivity(intent)
            } catch (e: ActivityNotFoundException) {

                //No encontró la aplicación, abre la versión web.
                startActivity(
                    Intent(
                        Intent.ACTION_VIEW,
                        Uri.parse("http://instagram.com/upitnikofficial")
                    )
                )
            }
        }
        binding.civTwitter.setOnClickListener {
            val uri: Uri = Uri.parse("http://twitter.com/UpitnikOfficial")
            val intent = Intent(Intent.ACTION_VIEW, uri)
            intent.setPackage("com.twitter.android")

            try {
                startActivity(intent)
            } catch (e: ActivityNotFoundException) {

                //No encontró la aplicación, abre la versión web.
                startActivity(
                    Intent(
                        Intent.ACTION_VIEW,
                        Uri.parse("http://twitter.com/UpitnikOfficial")
                    )
                )
            }
        }
        binding.civWeb.setOnClickListener {
            Snackbar.make(binding.root, "Proximamente...", Snackbar.LENGTH_LONG)
                .setTextColor(Color.GREEN).show()
        }
        //Debe cargar las asignaturas desde la API y desde ahí coger las que solo tienen el progreso mayor a 0
        val Matematicas = Subject("Matematicas", 0)
        val Catalan = Subject("Catalan", 50)
        val Ingles = Subject("Ingles", 75)
        val Naturales = Subject("Naturales", 25)
        val listSubject: List<Subject> = listOf(Matematicas, Catalan, Ingles, Naturales)
        binding.rvSubjectMainMenu.adapter = SubjectAdapter(listSubject, this@MainMenu)
    }

    fun TestAPI() {
        val retrofit = WebService.RetrofitClient.webService
        /*val retrofit = Retrofit.Builder()
            .baseUrl(AppConstants.BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .build()*/
        //val httpService = retrofit.create(WebService::class.java)

        val call: Call<Courses> =
            WebService.RetrofitClient.webService.getJSON("api/pwlassignatura")

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
    }

    override fun onSubjectClicked(Subject: Subject) {
        binding.root.findNavController()
            .navigate(R.id.action_mainMenu_to_menulevels)
    }


}