package com.upitnik.playwithlessons.ui.register

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.navigation.findNavController
import com.upitnik.playwithlessons.R
import com.upitnik.playwithlessons.databinding.FragmentRegisterBinding
import com.upitnik.playwithlessons.models.user.User
import com.upitnik.playwithlessons.services.Firebase

class RegisterFragment : Fragment() {
    private var _binding: FragmentRegisterBinding? = null
    private val binding get() = _binding!!
    private var firebase: Firebase = Firebase()
    private var user: User = User("", "", "", "")

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentRegisterBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.btLogin.setOnClickListener {
            user.email = binding.editText.text.toString()
            user.password = binding.etPassword.text.toString()
            val registered = firebase.register(user)
            println("Registro?$registered")
            activity?.findNavController(R.id.nav_host_fragment)
                ?.navigate(R.id.action_nav_register_to_nav_home)
        }

    }

}