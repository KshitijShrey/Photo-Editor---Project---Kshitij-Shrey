package com.example.assignment2_19012021097_database

import android.app.ProgressDialog
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.text.TextUtils
import android.util.Patterns
import android.widget.Toast
import androidx.appcompat.app.ActionBar
import com.example.assignment2_19012021097_database.databinding.ActivityLoginBinding
import com.example.assignment2_19012021097_database.databinding.ActivitySignUpBinding
import com.google.firebase.auth.FirebaseAuth

class SignUpActivity : AppCompatActivity() {

    private lateinit var binding: ActivitySignUpBinding

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
        binding = ActivitySignUpBinding.inflate(layoutInflater)
        setContentView(binding.root)

        //Configure Action Bar
        actionBar=supportActionBar!!
        actionBar.title="Sign Up"
        actionBar.setDisplayHomeAsUpEnabled(true)
        actionBar.setDisplayShowHomeEnabled(true)

        //configure progress dialog
        progressDialog=ProgressDialog(this)
        progressDialog.setTitle("Please Wait!")
        progressDialog.setMessage("Creating Account...")
        progressDialog.setCanceledOnTouchOutside(false)

        //init firebase auth
        firebaseAuth= FirebaseAuth.getInstance()

        //click handle start signup
        binding.signupbtn.setOnClickListener{
            validateData()
        }
    }

    private fun validateData() {
        //get data
        email=binding.emailEt.text.toString().trim()
        password=binding.passwordEt.text.toString().trim()

        //validate data
        if(!Patterns.EMAIL_ADDRESS.matcher(email).matches()){
            binding.emailEt.error="Invalid email format"
        }
        else if(TextUtils.isEmpty(password)){
            binding.passwordEt.error="Enter Password"
        }
        else if (password.length<4){
            //password minimum
            binding.passwordEt.error="Password must be more than 4 letters"
        }
        else{
            //data valid confirm signup
            firebaseSignUp()
        }
    }

    private fun firebaseSignUp() {
        //show progress
        progressDialog.show()

        //create account
        firebaseAuth.createUserWithEmailAndPassword(email, password)
            .addOnSuccessListener {
                //signup success
                progressDialog.dismiss()
                //get current user
                val firebaseUser=firebaseAuth.currentUser
                val email=firebaseUser!!.email
                Toast.makeText(this,"Account Created with email : $email",Toast.LENGTH_SHORT).show()
                //open profile
                startActivity(Intent(this, ProfileActivity::class.java))
                finish()
            }
            .addOnFailureListener {e->
                //signup failure
                progressDialog.dismiss()
                Toast.makeText(this,"Signup failed due to ${e.message}",Toast.LENGTH_SHORT).show()

            }
    }

    override fun onSupportNavigateUp(): Boolean {
        onBackPressed()
        return super.onSupportNavigateUp()
    }
}