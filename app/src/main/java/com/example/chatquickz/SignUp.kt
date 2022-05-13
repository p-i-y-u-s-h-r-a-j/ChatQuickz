package com.example.chatquickz

import android.app.Activity
import android.app.Activity.RESULT_OK
import android.content.Intent
import android.content.pm.PackageManager
import android.net.Uri
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import androidx.activity.result.contract.ActivityResultContracts
import com.google.android.gms.tasks.Continuation
import com.google.android.gms.tasks.Task
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.storage.FirebaseStorage
import com.google.firebase.storage.UploadTask
import com.squareup.picasso.Picasso
import kotlinx.android.synthetic.main.activity_login.*
import kotlinx.android.synthetic.main.activity_sign_up.*

private lateinit var mAuth: FirebaseAuth
private lateinit var mDbRef: DatabaseReference

class SignUp : AppCompatActivity() {

    val storage by lazy {
        FirebaseStorage.getInstance()
    }
    val Auth by lazy {
        FirebaseAuth.getInstance()
    }
    lateinit var downloadUrl: String

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_sign_up)
        supportActionBar?.hide()


        mAuth = FirebaseAuth.getInstance()
        registerBtn.setOnClickListener {
            val name = edtNameR.text.toString()
            val email = edtUsernameR.text.toString()
            val password = edtpasswordR.text.toString()

            signUp(name,email, password)
        }

    }





    private fun uploadImage(it: Uri) {
        registerBtn.isEnabled = false
        val ref =storage.reference.child("uploads/" + Auth.uid.toString())
        val uploadTask =ref.putFile(it)

        uploadTask.continueWithTask(com.google.android.gms.tasks.Continuation<UploadTask.TaskSnapshot,Task<Uri>> { task ->
            if(!task.isSuccessful){
                task.exception?.let {
                    throw it
                }
            }
            return@Continuation ref.downloadUrl
        }).addOnCompleteListener {task ->
            registerBtn.isEnabled = true
            if(task.isSuccessful){
                downloadUrl = task.result.toString()
            }else{
                Toast.makeText(this, "Something Went Wrong...", Toast.LENGTH_SHORT).show()
            }
        }.addOnFailureListener{

        }
    }

    private fun signUp(name:String,email: String, password: String) {

        if(name.isEmpty() or email.isEmpty() or password.isEmpty()){
            Toast.makeText(this, "Please Enter all details", Toast.LENGTH_SHORT).show()
        }else {
            mAuth.createUserWithEmailAndPassword(email, password)
                .addOnCompleteListener(this) { task ->
                    if (task.isSuccessful) {
                        // code for signup
                        addUserToDatabase(name, email, mAuth.currentUser?.uid!!)
                        val intent = Intent(this@SignUp, MainActivity::class.java)
                        finish()
                        startActivity(intent)
                    } else {
                        Toast.makeText(this@SignUp, "Some Error Occurred", Toast.LENGTH_SHORT)
                            .show()
                    }

                }
        }
    }

    private fun addUserToDatabase(name: String, email: String, uid: String) {//--->This function add user detail to database. Till now only User is Created not added to database
        mDbRef = FirebaseDatabase.getInstance().getReference()
        mDbRef.child("User").child(uid).setValue(User(name,email,uid))
    }
}