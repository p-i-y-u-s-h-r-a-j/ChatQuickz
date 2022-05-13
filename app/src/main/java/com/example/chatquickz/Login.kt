package com.example.chatquickz

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.EditText
import android.widget.Toast
import com.google.firebase.auth.FirebaseAuth
import kotlinx.android.synthetic.main.activity_login.*


class Login : AppCompatActivity() {

    private lateinit var mAuth:FirebaseAuth
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)
        supportActionBar?.hide()

        mAuth = FirebaseAuth.getInstance()

        registerTxt.setOnClickListener {
            val intent = Intent(this, SignUp::class.java)
            startActivity(intent)
        }
        loginBtn.setOnClickListener {
            val email = edtUsernameL.text.toString()
            val password = edtPasswordL.text.toString()
            login(email, password)
        }
    }
    private fun login(email: String, password: String) {
        if ((email.isEmpty()) or (password.isEmpty())) {
            Toast.makeText(this, "Please Enter Your Email or PassWord", Toast.LENGTH_SHORT).show()
        }else{
        mAuth.signInWithEmailAndPassword(email, password)
            .addOnCompleteListener(this) { task ->
                if (task.isSuccessful) {
                    //code for login
                    val intent = Intent(this@Login, MainActivity::class.java)
                    finish()
                    startActivity(intent)
                } else {
                    Toast.makeText(this, "User Not Found!!", Toast.LENGTH_SHORT).show()
                }
            }
        }
    }
}