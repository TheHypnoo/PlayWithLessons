package com.upitnik.playwithlessons.ui

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import com.upitnik.playwithlessons.R
import com.upitnik.playwithlessons.services.Firebase
import com.upitnik.playwithlessons.ui.login.LoginFragment
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class LoadingActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_loading)
        Onloaded()
    }

    private fun Onloaded() {
        val firebase = Firebase()
        CoroutineScope(Dispatchers.IO).launch {
            val connected = firebase.initializeCurrentUser()
            if (connected != null) {
                //Tienes algo!
                //Te meto en el Home directamente
                startActivity(Intent(this@LoadingActivity, MainActivity::class.java))
                finish()
            } else {
                val LoginFragment = LoginFragment()
                replaceFragment(LoginFragment)
                //Te llevo Login/Registro
            }
        }
    }

    private fun replaceFragment(fragment: Fragment) {
        val transaction = supportFragmentManager.beginTransaction()
        transaction.replace(R.id.fragment_container, fragment)
        transaction.commit()
    }

}