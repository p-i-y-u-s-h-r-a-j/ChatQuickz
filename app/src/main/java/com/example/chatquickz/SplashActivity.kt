package com.example.chatquickz

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.google.firebase.auth.FirebaseAuth

class SplashActivity : AppCompatActivity() {

    private val auth by lazy {
        FirebaseAuth.getInstance()
    }
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        if(auth.currentUser == null){
            finish()
            startActivity(Intent(this, Login::class.java))
        }else{
            finish()
            startActivity(Intent(this, MainActivity::class.java))
        }

    }
}