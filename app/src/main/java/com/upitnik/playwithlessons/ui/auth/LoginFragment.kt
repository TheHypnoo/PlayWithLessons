package com.upitnik.playwithlessons.ui.auth

import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.TextView
import androidx.appcompat.app.AlertDialog
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import androidx.navigation.fragment.findNavController
import com.google.firebase.auth.FirebaseAuth
import com.upitnik.playwithlessons.R
import com.upitnik.playwithlessons.core.Result
import com.upitnik.playwithlessons.core.extensions.hideKeyboard
import com.upitnik.playwithlessons.data.remote.authentication.AuthDataSource
import com.upitnik.playwithlessons.databinding.FragmentLoginBinding
import com.upitnik.playwithlessons.domain.auth.AuthRepoImpl
import com.upitnik.playwithlessons.presentation.auth.AuthViewModel
import com.upitnik.playwithlessons.presentation.auth.AuthViewModelFactory


class LoginFragment : Fragment(R.layout.fragment_login) {

    private lateinit var binding: FragmentLoginBinding
    private val firebaseAuth by lazy { FirebaseAuth.getInstance() }
    private val viewModel by viewModels<AuthViewModel> {
        AuthViewModelFactory(
            AuthRepoImpl(
                AuthDataSource()
            )
        )
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding = FragmentLoginBinding.bind(view)
        isUserLoggedIn()
        doLogin()
        gotoSignUpPage()
    }

    private fun isUserLoggedIn() {
        firebaseAuth.currentUser?.let {
            findNavController().navigate(R.id.action_loginFragment_to_mainMenu)
        }
    }


    private fun doLogin() {
        binding.btnSignIn.setOnClickListener {
            val email = binding.inputEmail.text.toString().trim()
            val password = binding.inputPasword.text.toString().trim()
            if (validateCredentials(email, password)) {
                signIn(email, password)
            }
        }
    }

    private fun gotoSignUpPage() {
        binding.txtSignup.setOnClickListener {
            findNavController().navigate(R.id.action_loginFragment_to_registerFragment)
        }
    }

    private fun validateCredentials(email: String, password: String): Boolean {
        binding.tilPassword.isPasswordVisibilityToggleEnabled = true
        if (email.isEmpty()) {
            binding.inputEmail.setError(
                "E-mail esta vacio",
                ContextCompat.getDrawable(binding.root.context, R.drawable.mtrl_ic_error)
            )
            return false
        }
        if (password.isEmpty()) {
            binding.tilPassword.isPasswordVisibilityToggleEnabled = false
            binding.inputPasword.error = "Contraseña esta vacia"
            return false
        }
        return true
    }

    private fun signIn(email: String, password: String) {
        viewModel.signIn(email, password).observe(viewLifecycleOwner, Observer { result ->
            when (result) {
                is Result.Loading -> {
                    binding.progressBar.visibility = View.VISIBLE
                    binding.btnSignIn.visibility = View.INVISIBLE
                    binding.btnSignIn.isEnabled = false
                }
                is Result.Sucess -> {
                    binding.progressBar.visibility = View.GONE
                    hideKeyboard()
                    findNavController().navigate(R.id.action_loginFragment_to_mainMenu)
                }
                is Result.Failure -> {
                    binding.btnSignIn.isEnabled = true
                    binding.btnSignIn.visibility = View.VISIBLE
                    binding.progressBar.visibility = View.GONE
                    dialogError(result.exception.toString())
                    /*Toast.makeText(
                        requireContext(),
                        "Error: ${result.exception}",
                        Toast.LENGTH_SHORT
                    ).show()*/
                }
            }
        })
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