package com.example.assignment2_19012021097_database

import android.app.ProgressDialog
import android.content.Intent
import android.os.Binder
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.appcompat.app.ActionBar
import com.example.assignment2_19012021097_database.databinding.ActivityLoginBinding
import com.example.assignment2_19012021097_database.databinding.ActivityProfileBinding
import com.google.firebase.auth.FirebaseAuth
import kotlinx.android.synthetic.main.activity_profile.*

class ProfileActivity : AppCompatActivity() {

    //viewBinding
    private lateinit var binding:ActivityProfileBinding

    //ActionBar
    private lateinit var actionBar: ActionBar

    //FirebaseAuth
    private lateinit var firebaseAuth: FirebaseAuth

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityProfileBinding.inflate(layoutInflater)
            setContentView(binding.root)

        //Configure Action Bar
        actionBar=supportActionBar!!
        actionBar.title="Profile"

        //init firebase auth
        firebaseAuth= FirebaseAuth.getInstance()
        checkUser()

        //logout click
        binding.logoutBtn.setOnClickListener{
            firebaseAuth.signOut()
            checkUser()
        }

        toEditBtn.setOnClickListener{
            val intent=Intent(this, MainActivity::class.java)
            startActivity(intent)

        }


    }
    private fun checkUser(){
        //check user logged in
        val firebaseUser = firebaseAuth.currentUser
        if(firebaseUser != null){
            //user not null i.e. logged in
            val email=firebaseUser.email

            //set to textview
            binding.emailTv.text=email


        }
        else{
            //user null i.e. not logged in so move to login activity
            startActivity(Intent(this, LoginActivity::class.java))
            finish()

        }
    }
}