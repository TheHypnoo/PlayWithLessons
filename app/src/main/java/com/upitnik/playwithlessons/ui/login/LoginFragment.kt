package com.upitnik.playwithlessons.ui.login

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.upitnik.playwithlessons.databinding.FragmentLoginBinding
import com.upitnik.playwithlessons.models.user.User
import com.upitnik.playwithlessons.services.Firebase
import com.upitnik.playwithlessons.ui.MainActivity

class LoginFragment : Fragment() {

    private var _binding: FragmentLoginBinding? = null
    private val binding get() = _binding!!
    private var firebase: Firebase = Firebase()
    private var user: User = User("", "", "", "")

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentLoginBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.btLogin.setOnClickListener {
            user.email = binding.editText.text.toString()
            user.password = binding.etPassword.text.toString()
            val registered = firebase.login(user)
            println("Logged?$registered")
            activity?.finish()
            activity?.startActivity(
                Intent(
                    this@LoginFragment.requireContext(),
                    MainActivity::class.java
                )
            )
            //activity?.findNavController(R.id.nav_host_fragment)?.navigate(R.id.action_nav_register_to_nav_home)

        }
    }


}