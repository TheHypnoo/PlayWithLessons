package com.upitnik.playwithlessons.ui.auth

import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
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
import com.upitnik.playwithlessons.core.extensions.invisible
import com.upitnik.playwithlessons.core.extensions.isNull
import com.upitnik.playwithlessons.core.extensions.visible
import com.upitnik.playwithlessons.data.model.authentication.Images
import com.upitnik.playwithlessons.data.model.authentication.User
import com.upitnik.playwithlessons.data.remote.authentication.AuthDataSource
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
                    dialogError(result.exception.toString())
                    /*Toast.makeText(
                        requireContext(),
                        "Error: ${result.exception}",
                        Toast.LENGTH_LONG
                    ).show()*/
                }
            }
        })
    }

    private fun visibilityImage() {
        binding.civSelectImage.setOnClickListener {
            binding.civSelectImage.invisible()
            binding.rvSelectorImage.visible()
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
            if (image.isNull()) image = "http://cdn.onlinewebfonts.com/svg/img_258083.png"
            createUser(email, password, username, image)
        }
    }

    private fun createUser(
        emailUser: String,
        passwordUser: String,
        usernameUser: String,
        imageUser: String?
    ) {
        viewModel.signUp(emailUser, passwordUser, usernameUser, imageUser!!)
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
                            User(
                                0,
                                FirebaseAuth.getInstance().currentUser!!.uid,
                                emailUser,
                                0,
                                imageUser,
                                usernameUser,
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
                        dialogError(result.exception.toString())
                        /*Toast.makeText(
                            requireContext(),
                            "Error: ${result.exception}",
                            Toast.LENGTH_LONG
                        ).show()*/
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
        if (username.length == 12) {
            binding.editTextUsername.error = "El nombre del usuario es muy largo"
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
            binding.editTextConfirmPassword.error = "El confirmar contraseña esta vacio"
            return true
        }
        return false
    }

    override fun onImageClick(imageUser: Images) {
        binding.rvSelectorImage.invisible()
        Glide.with(binding.root.context).load(imageUser.url).into(binding.civSelectImage)
        binding.civSelectImage.borderColor =
            ContextCompat.getColor(requireContext(), R.color.darkGreen)
        image = imageUser.url
        binding.civSelectImage.visible()
    }

    private fun dialogError(message: String) {
        val view = View.inflate(binding.root.context, R.layout.dialog_error, null)

        val builder = AlertDialog.Builder(binding.root.context)
        builder.setView(view)

        val dialog = builder.create()
        dialog.show()
        dialog.window?.setBackgroundDrawableResource(android.R.color.transparent)

        val btnConfirm = view.findViewById<Button>(R.id.btn_leave)
        btnConfirm.setOnClickListener {
            dialog.dismiss()
        }
        val textMessage = view.findViewById<TextView>(R.id.textMessage)
        textMessage.text = message
    }


}