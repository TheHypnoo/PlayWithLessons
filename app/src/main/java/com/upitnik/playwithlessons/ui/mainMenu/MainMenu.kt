package com.upitnik.playwithlessons.ui.mainMenu

import android.content.ActivityNotFoundException
import android.content.Context
import android.content.Intent
import android.graphics.Color
import android.net.Uri
import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.findNavController
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.GridLayoutManager
import com.bumptech.glide.Glide
import com.google.android.material.snackbar.Snackbar
import com.google.firebase.auth.FirebaseAuth
import com.upitnik.playwithlessons.R
import com.upitnik.playwithlessons.data.model.auth.UserItem
import com.upitnik.playwithlessons.data.model.subject.Subjects
import com.upitnik.playwithlessons.data.model.subject.SubjectsItem
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
    private val listSubjects: ArrayList<SubjectsItem> = arrayListOf()
    private var user: UserItem? = null

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding = FragmentMainMenuBinding.bind(view)
        if (user != null) {
            Glide.with(binding.root.context).load(user!!.image).into(binding.civUser)
            binding.tvUser.text = user!!.nickname
            binding.lpiProgress.progress = user!!.experience

            binding.tvLevelUser.text = checkExperience(user!!.experience)
            println("User encontrado")
        }
        getSubjects()
        getUser()
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
        /*//Debe cargar las asignaturas desde la API y desde ahí coger las que solo tienen el progreso mayor a 0
        val Matematicas = Subject("Matematicas", 0)
        val Catalan = Subject("Catalan", 50)
        val Ingles = Subject("Ingles", 75)
        val Naturales = Subject("Naturales", 25)
        val listSubject: List<Subject> =
            listOf(Matematicas, Catalan, Ingles, Naturales, Matematicas, Catalan)
        binding.rvSubjectMainMenu.layoutManager = GridLayoutManager(this@MainMenu.context, 2)
        binding.rvSubjectMainMenu.adapter = SubjectAdapter(listSubject, this@MainMenu)*/

    }

    private fun getSubjects() {
        listSubjects.clear()
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

                val cityList = response.body()

                if (cityList != null) {
                    for (d in cityList.indices) {
                        listSubjects.add(
                            SubjectsItem(
                                cityList[d].gamemode,
                                cityList[d].id,
                                cityList[d].image,
                                cityList[d].name
                            )
                        )
                    }
                    binding.rvSubjectMainMenu.layoutManager =
                        GridLayoutManager(this@MainMenu.context, 2)
                    binding.rvSubjectMainMenu.adapter =
                        SubjectAdapter(listSubjects, this@MainMenu)
                }
            }
        })
    }

    private fun getUser() {
        val call: Call<List<UserItem>> =
            WebService.RetrofitClient.webService.getUsers()

        call.enqueue(object : Callback<List<UserItem>> {

            override fun onFailure(call: Call<List<UserItem>>, t: Throwable) {
                t.message?.let {
                    println(it)

                }


            }


            override fun onResponse(
                call: Call<List<UserItem>>?,
                response: Response<List<UserItem>>?
            ) {

                if (!response!!.isSuccessful) {
                    println(response.code())

                    return

                }

                val cityList = response.body()

                if (cityList != null) {
                    val auth = FirebaseAuth.getInstance().currentUser!!.uid
                    for (d in cityList.indices) {
                        if (cityList[d].uid == auth) {
                            println(cityList[d])
                            user = cityList[d]
                            Glide.with(binding.root.context).load(user!!.image)
                                .into(binding.civUser)
                            binding.tvUser.text = user!!.nickname
                            binding.lpiProgress.progress = user!!.experience

                            binding.tvLevelUser.text = checkExperience(user!!.experience)
                            println("User encontrado")
                        }
                    }
                }
            }
        })
    }

    override fun onSubjectClicked(Subject: SubjectsItem) {
        binding.root.findNavController()
            .navigate(R.id.action_mainMenu_to_menulevels)
    }

    private fun checkExperience(experiencie: Int): String {
        if (experiencie == 0) {
            return "Nivel 0"
        }
        return "Nivel 0"
    }

    override fun onAttach(context: Context) {
        super.onAttach(context)
        user = arguments?.getSerializable("User") as UserItem?
    }

}