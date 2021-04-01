package com.upitnik.playwithlessons.services

import android.util.Log
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import com.upitnik.playwithlessons.models.user.User
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class Firebase {
    private var auth: FirebaseAuth = FirebaseAuth.getInstance()

    fun login(user: User) {
        CoroutineScope(Dispatchers.IO).launch {
            auth.signInWithEmailAndPassword(user.email, user.password)
                .addOnCompleteListener {
                    if (it.isSuccessful) {
                        Log.d("Test", "Logged")

                    } else {
                        Log.d("Test", "No logged")
                    }
                }
        }
    }


    fun register(user: User): Boolean {
        var registered: Boolean = false
        if (user.email.isNullOrEmpty() || user.password.isNullOrEmpty()) {
            print("sos tonto")
        } else {
            CoroutineScope(Dispatchers.IO).launch {
                auth.createUserWithEmailAndPassword(user.email, user.password)
                    .addOnCompleteListener {
                        registered = it.isSuccessful
                    }
            }
        }
        return registered
    }

    fun initializeCurrentUser(): FirebaseUser? {
        val firebaseUser: FirebaseUser? = auth.currentUser
        if (firebaseUser != null) {
            return firebaseUser
        }
        return null
    }

    fun signOut() {
        auth.signOut()
    }

}