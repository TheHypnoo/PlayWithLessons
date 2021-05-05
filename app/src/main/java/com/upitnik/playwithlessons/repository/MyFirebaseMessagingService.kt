package com.upitnik.playwithlessons.repository

import android.os.Handler
import android.os.Looper
import android.widget.Toast
import com.google.firebase.messaging.FirebaseMessagingService
import com.google.firebase.messaging.RemoteMessage

class MyFirebaseMessagingService : FirebaseMessagingService() {
    override fun onMessageReceived(remoteMessage: RemoteMessage) {
        Looper.prepare()

        Handler(Looper.myLooper()!!).post {
            Toast.makeText(
                baseContext,
                "${remoteMessage.notification?.title}\n${remoteMessage.notification?.body}",
                Toast.LENGTH_SHORT
            ).show()
        }
        Looper.loop()
    }
}