package com.example.foodrunner.Activity

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.*
import com.example.foodrunner.R

class LoginActivity : AppCompatActivity() {

    lateinit var etUsername: com.google.android.material.textfield.TextInputLayout
    lateinit var etPassword: com.google.android.material.textfield.TextInputLayout
    lateinit var btnLogin: Button
    lateinit var txtForgot: TextView
    lateinit var txtRegister: TextView


    lateinit var relative_layout: RelativeLayout
    lateinit var linear_layout: LinearLayout



    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.login_activity)
        etUsername = findViewById(R.id.etUsername)
        etPassword = findViewById(R.id.etPassword)
        txtForgot = findViewById(R.id.txtForgot)
        txtRegister = findViewById(R.id.txtRegister)
        btnLogin = findViewById(R.id.btnLogin)




        btnLogin.setOnClickListener {
            val intent = Intent(this@LoginActivity, HomeActivity::class.java)
            startActivity(intent)
        }
        txtForgot.setOnClickListener {
            val intent = Intent(this@LoginActivity, ForgotPassword::class.java)
            startActivity(intent)
        }
        txtRegister.setOnClickListener {
            val intent = Intent(this@LoginActivity, Register::class.java)
            startActivity(intent)
        }

    }
}
