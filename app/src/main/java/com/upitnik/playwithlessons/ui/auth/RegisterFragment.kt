package com.upitnik.playwithlessons.ui.auth

import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import androidx.navigation.fragment.findNavController
import com.bumptech.glide.Glide
import com.google.firebase.auth.FirebaseAuth
import com.upitnik.playwithlessons.R
import com.upitnik.playwithlessons.core.Result
import com.upitnik.playwithlessons.core.extensions.hideKeyboard
import com.upitnik.playwithlessons.data.model.auth.ImagesRegisterItem
import com.upitnik.playwithlessons.data.model.auth.UserItem
import com.upitnik.playwithlessons.data.remote.auth.AuthDataSource
import com.upitnik.playwithlessons.databinding.FragmentRegisterBinding
import com.upitnik.playwithlessons.domain.auth.AuthRepoImpl
import com.upitnik.playwithlessons.presentation.auth.AuthViewModel
import com.upitnik.playwithlessons.presentation.auth.AuthViewModelFactory


class RegisterFragment : Fragment(R.layout.fragment_register), OnImageActionListener {

    private lateinit var binding: FragmentRegisterBinding
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
        getImages()
        visibilityImage()
        signUp()
    }

    private fun getImages() {
        viewModel.getImages().observe(viewLifecycleOwner, Observer { result ->
            when (result) {
                is Result.Loading -> {
                    binding.pbImages.visibility = View.VISIBLE
                    binding.rvSelectorImage.visibility = View.INVISIBLE
                }
                is Result.Sucess -> {
                    binding.pbImages.visibility = View.GONE
                    binding.rvSelectorImage.visibility = View.VISIBLE
                    if (result.data.isNotEmpty()) {
                        binding.rvSelectorImage.adapter =
                            SelectorImageAdapter(result.data, this@RegisterFragment)
                    }
                }
                is Result.Failure -> {
                    binding.pbImages.visibility = View.GONE
                    binding.rvSelectorImage.visibility = View.INVISIBLE
                    Toast.makeText(
                        requireContext(),
                        "Error: ${result.exception}",
                        Toast.LENGTH_LONG
                    ).show()
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

    private fun signUp() {
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
                                0,
                                FirebaseAuth.getInstance().currentUser!!.uid,
                                email,
                                0,
                                image,
                                username,
                                0
                            )
                        )
                        hideKeyboard()
                        findNavController().navigate(
                            R.id.action_registerFragment_to_mainMenu,
                            bundle
                        )

                    }
                    is Result.Failure -> {
                        binding.progressBar.visibility = View.GONE
                        binding.btnSignUp.isEnabled = true
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

    override fun onImageClick(imageUser: ImagesRegisterItem) {
        binding.rvSelectorImage.visibility = View.GONE
        Glide.with(binding.root.context).load(imageUser.url).into(binding.civSelectImage)
        binding.civSelectImage.borderColor =
            ContextCompat.getColor(requireContext(), R.color.yellow)
        image = imageUser.url
        binding.civSelectImage.visibility = View.VISIBLE
    }


}