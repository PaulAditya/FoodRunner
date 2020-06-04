package com.example.foodrunner.Activity

import android.animation.ObjectAnimator
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler
import android.view.animation.Animation
import android.view.animation.AnimationUtils
import android.widget.TextView
import com.example.foodrunner.R

class MainActivity : AppCompatActivity() {

    lateinit var txtLogo : TextView
    lateinit var bottom_up : Animation

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        txtLogo = findViewById(R.id.txtLogo)
        bottom_up = AnimationUtils.loadAnimation(this, R.anim.bottom_up)
        txtLogo.startAnimation(bottom_up)
        Handler().postDelayed({
            val startAct = Intent(this@MainActivity, LoginActivity::class.java)
            startActivity(startAct)
        },2000)
    }
}
