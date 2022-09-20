package com.balancefy.balancefyapp.auth

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import com.balancefy.balancefyapp.R

class IntroActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_intro)
    }

    fun entrar(view: View) {
        startActivity(Intent(this, LoginActivity::class.java))
    }

    fun cadastrar(view: View) {
        startActivity(Intent(this, LoginActivity::class.java))
    }
}