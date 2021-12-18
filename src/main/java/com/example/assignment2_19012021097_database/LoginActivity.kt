package com.example.assignment2_19012021097_database

import android.app.ProgressDialog
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.PatternMatcher
import android.text.TextUtils
import android.util.Patterns
import android.widget.Toast
import androidx.appcompat.app.ActionBar
import com.example.assignment2_19012021097_database.databinding.ActivityLoginBinding
import com.google.firebase.analytics.FirebaseAnalytics
import com.google.firebase.auth.FirebaseAuth

class LoginActivity : AppCompatActivity() {

    //viewBinding
    private lateinit var binding:ActivityLoginBinding

    //ActionBar
    private lateinit var actionBar: ActionBar

    //ProgressDialog
    private lateinit var progressDialog: ProgressDialog

    //FirebaseAuth
    private lateinit var firebaseAuth: FirebaseAuth
    private var email=""
    private var password=""

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding= ActivityLoginBinding.inflate(layoutInflater)
        setContentView(binding.root)

        //configure bar
        actionBar=supportActionBar!!
        actionBar.title="Login"

        //configure progress dialog
        progressDialog=ProgressDialog(this)
        progressDialog.setTitle("Please Wait!")
        progressDialog.setMessage("Logging In...")
        progressDialog.setCanceledOnTouchOutside(false)

        //init firebaseAuth
        firebaseAuth= FirebaseAuth.getInstance()
        checkUser()

        //handle click open register activity
        binding.noAccountTv.setOnClickListener{
            startActivity(Intent(this, SignUpActivity::class.java))
        }

        //handle click
        binding.loginBtn.setOnClickListener{
            //before logging in
            validateData()

        }
    }

    private fun validateData() {
        //get data
        email=binding.emailEt.text.toString().trim()
        password=binding.passwordEt.text.toString().trim()

        //validate data
        if(!Patterns.EMAIL_ADDRESS.matcher(email).matches()){
            //invalid email format
            binding.emailEt.error="Invalid Email Format"
        }
        else if (TextUtils.isEmpty(password)){
            //no password entered
            binding.passwordEt.error="Please Enter Password"
        }
        else{
            //data validated
            firebaseLogin()
        }

    }

    private fun firebaseLogin() {
        //show progress
        progressDialog.show()
        firebaseAuth.signInWithEmailAndPassword(email, password)
            .addOnSuccessListener {
                //login success
                progressDialog.dismiss()
                //get user info
                val firebaseUser=firebaseAuth.currentUser
                val email = firebaseUser!!.email
                Toast.makeText(this,"Logged in as $email",Toast.LENGTH_SHORT).show()

                //profile open
                startActivity(Intent(this, ProfileActivity::class.java))
                finish()

            }
            .addOnFailureListener {e->
                //login failure
                progressDialog.dismiss()
                Toast.makeText(this,"Login Failed due to ${e.message}",Toast.LENGTH_SHORT).show()
            }
    }

    private fun checkUser() {
        //user logged in
        //get current user
        val firebaseUser=firebaseAuth.currentUser
        if(firebaseUser != null){
            startActivity(Intent(this, ProfileActivity::class.java))
            finish()
        }
    }
}