package com.upitnik.playwithlessons.ui.auth

import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import androidx.navigation.fragment.findNavController
import com.bumptech.glide.Glide
import com.google.firebase.auth.FirebaseAuth
import com.upitnik.playwithlessons.R
import com.upitnik.playwithlessons.core.Result
import com.upitnik.playwithlessons.data.model.auth.ImagesRegister
import com.upitnik.playwithlessons.data.model.auth.UserItem
import com.upitnik.playwithlessons.data.model.auth.imageUser
import com.upitnik.playwithlessons.data.remote.auth.AuthDataSource
import com.upitnik.playwithlessons.databinding.FragmentRegisterBinding
import com.upitnik.playwithlessons.domain.auth.AuthRepoImpl
import com.upitnik.playwithlessons.presentation.auth.AuthViewModel
import com.upitnik.playwithlessons.presentation.auth.AuthViewModelFactory
import com.upitnik.playwithlessons.repository.WebService
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response


class RegisterFragment : Fragment(R.layout.fragment_register), OnImageActionListener {
    private lateinit var binding: FragmentRegisterBinding
    private var listImage: ArrayList<imageUser> = ArrayList()
    private var image: String? = null
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
        CoroutineScope(Dispatchers.IO).launch { withContext(Dispatchers.IO) { getImages() } }
        //SelectorImage()
        visibilityImage()
        SignUp()
    }

    private fun SelectorImage() {
        val Matematicas =
            imageUser("https://avatars.githubusercontent.com/u/31130069?s=400&u=803eaf9c41f3b173610d6f1ee42ab55665f5c9d0&v=4")
        val Catalan = imageUser("https://avatars.githubusercontent.com/u/19620536?v=4")
        val Ingles = imageUser("https://avatars.githubusercontent.com/u/10779469?v=4")
        val Culo = imageUser("https://m.media-amazon.com/images/I/71jod9LB42L._SS500_.jpg")
        val Tetas =
            imageUser("https://www.ecestaticos.com/imagestatic/clipping/0cb/784/0cb784d3e9676df636b5ddd1b937c45a.jpg")
        val listImages: List<imageUser> = listOf(Matematicas, Catalan, Ingles, Culo, Tetas)
        binding.rvSelectorImage.adapter =
            SelectorImageAdapter(listImages, this@RegisterFragment)
    }

    private fun getImages() {
        val call: Call<ImagesRegister> =
            WebService.RetrofitClient.webService.getImages()

        call.enqueue(object : Callback<ImagesRegister> {

            override fun onFailure(call: Call<ImagesRegister>, t: Throwable) {
                t.message?.let {
                    println(it)

                }


            }


            override fun onResponse(
                call: Call<ImagesRegister>?,
                response: Response<ImagesRegister>?
            ) {

                if (!response!!.isSuccessful) {
                    println(response.code())

                    return

                }

                val cityList = response.body()

                if (cityList != null) {
                    println(cityList)
                    for (d in cityList.indices) {
                        listImage.add(imageUser(cityList[d].url))
                    }
                    binding.rvSelectorImage.adapter =
                        SelectorImageAdapter(listImage, this@RegisterFragment)
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

            createUser(email, password, username, image!!)
        }
    }

    private fun createUser(email: String, password: String, username: String, image: String) {
        viewModel.signUp(email, password, username, image)
            .observe(viewLifecycleOwner, Observer { result ->
                when (result) {
                    is Result.Loading -> {
                        binding.progressBar.visibility = View.VISIBLE
                        binding.btnSignUp.isEnabled = false
                    }
                    is Result.Sucess -> {
                        binding.progressBar.visibility = View.GONE
                        val bundle = Bundle()
                        bundle.putSerializable(
                            "User",
                            UserItem(
                                FirebaseAuth.getInstance().currentUser!!.uid,
                                email,
                                0,
                                image,
                                username,
                                1,
                                0
                            )
                        )
                        findNavController().navigate(
                            R.id.action_registerFragment_to_mainMenu,
                            bundle
                        )

                    }
                    is Result.Failure -> {
                        binding.progressBar.visibility = View.GONE
                        binding.btnSignUp.isEnabled = true
                        println("ERROR: " + result.exception)
                        Toast.makeText(
                            requireContext(),
                            "Error: ${result.exception}",
                            Toast.LENGTH_LONG
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
        image = imageUser.photo_url
        binding.civSelectImage.visibility = View.VISIBLE
    }


}