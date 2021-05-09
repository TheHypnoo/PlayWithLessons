package com.upitnik.playwithlessons.ui.auth

import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import androidx.navigation.fragment.findNavController
import com.bumptech.glide.Glide
import com.upitnik.playwithlessons.R
import com.upitnik.playwithlessons.core.Result
import com.upitnik.playwithlessons.data.model.auth.imageUser
import com.upitnik.playwithlessons.data.remote.auth.AuthDataSource
import com.upitnik.playwithlessons.databinding.FragmentRegisterBinding
import com.upitnik.playwithlessons.domain.auth.AuthRepoImpl
import com.upitnik.playwithlessons.presentation.auth.AuthViewModel
import com.upitnik.playwithlessons.presentation.auth.AuthViewModelFactory
import com.upitnik.playwithlessons.repository.WebService
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response


class RegisterFragment : Fragment(R.layout.fragment_register), OnImageActionListener {
    private lateinit var binding: FragmentRegisterBinding
    private var listImage: ArrayList<imageUser> = ArrayList()
    private val viewModel by viewModels<AuthViewModel> {
        AuthViewModelFactory(
            AuthRepoImpl(
                AuthDataSource()
            )
        )
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding = FragmentRegisterBinding.bind(view)
        TestAPI()
        SelectorImage()
        visibilityImage()
        SignUp()
    }

    private fun SelectorImage() {
        /*val Matematicas =
            imageUser("https://avatars.githubusercontent.com/u/31130069?s=400&u=803eaf9c41f3b173610d6f1ee42ab55665f5c9d0&v=4")
        val Catalan = imageUser("https://avatars.githubusercontent.com/u/19620536?v=4")
        val Ingles = imageUser("https://avatars.githubusercontent.com/u/10779469?v=4")
        val Culo = imageUser("https://m.media-amazon.com/images/I/71jod9LB42L._SS500_.jpg")
        val Tetas = imageUser("https://www.ecestaticos.com/imagestatic/clipping/0cb/784/0cb784d3e9676df636b5ddd1b937c45a.jpg")*/
/*        val listImages: List<imageUser> = listOf(Matematicas, Catalan, Ingles, Culo, Tetas)*/
    }

    private fun TestAPI() {
        val retrofit = WebService.RetrofitClient.webService
        /*val retrofit = Retrofit.Builder()
            .baseUrl(AppConstants.BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .build()*/
        //val httpService = retrofit.create(WebService::class.java)

        val call: Call<List<imageUser>> =
            WebService.RetrofitClient.webService.getJSON("api/pwlimage/")

        call.enqueue(object : Callback<List<imageUser>> {

            override fun onFailure(call: Call<List<imageUser>>, t: Throwable) {
                t.message?.let {
                    println(it)

                }


            }


            override fun onResponse(
                call: Call<List<imageUser>>?,
                response: Response<List<imageUser>>?
            ) {

                if (!response!!.isSuccessful) {
                    println(response.code())

                    return

                }

                val cityList = response.body()

                if (cityList != null) {
                    for (d in cityList.indices) {
                        listImage.add(imageUser(cityList[d].photo_url))
                    }
                    binding.rvSelectorImage.adapter =
                        SelectorImageAdapter(listImage, this@RegisterFragment)
                    println(cityList)
                }
            }
        })
    }

    private fun visibilityImage() {
        binding.civSelectImage.setOnClickListener {
            binding.civSelectImage.visibility = View.GONE
            binding.rvSelectorImage.visibility = View.VISIBLE
        }
    }

    private fun SignUp() {

        binding.btnSignUp.setOnClickListener {
            val username = binding.editTextUsername.text.toString().trim()
            val password = binding.editTextPassword.text.toString().trim()
            val confirmPassword = binding.editTextConfirmPassword.text.toString().trim()
            val email = binding.editTextEmail.text.toString().trim()

            if (validateUserData(
                    password,
                    confirmPassword,
                    username,
                    email
                )
            ) return@setOnClickListener

            createUser(email, password, username)
        }
    }

    private fun createUser(email: String, password: String, username: String) {
        viewModel.signUp(email, password, username).observe(viewLifecycleOwner, Observer { result ->
            when (result) {
                is Result.Loading -> {
                    binding.progressBar.visibility = View.VISIBLE
                    binding.btnSignUp.isEnabled = false
                }
                is Result.Sucess -> {
                    binding.progressBar.visibility = View.GONE
                    findNavController().navigate(R.id.action_registerFragment_to_mainMenu)

                }
                is Result.Failure -> {
                    binding.progressBar.visibility = View.GONE
                    binding.btnSignUp.isEnabled = true
                    Toast.makeText(
                        requireContext(),
                        "Error: ${result.exception}",
                        Toast.LENGTH_SHORT
                    ).show()
                }
            }
        })
    }

    private fun validateUserData(
        password: String,
        confirmPassword: String,
        username: String,
        email: String
    ): Boolean {
        if (password != confirmPassword) {
            binding.editTextConfirmPassword.error = "Las contraseñas no coinciden"
            binding.editTextPassword.error = "Las contraseñas no coinciden"
            return true
        }
        if (username.isEmpty()) {
            binding.editTextUsername.error = "El usuario esta vacio"
            return true
        }
        if (email.isEmpty()) {
            binding.editTextEmail.error = "El correo esta vacio"
            return true
        }
        if (password.isEmpty()) {
            binding.editTextPassword.error = "La contraseña esta vacia"
            return true
        }
        if (confirmPassword.isEmpty()) {
            binding.editTextConfirmPassword.error = "Confirmar contraseña esta vacio"
            return true
        }
        return false
    }

    override fun onImageClick(imageUser: imageUser) {
        binding.rvSelectorImage.visibility = View.GONE
        Glide.with(binding.root.context).load(imageUser.photo_url).into(binding.civSelectImage)
        binding.civSelectImage.visibility = View.VISIBLE
    }


}