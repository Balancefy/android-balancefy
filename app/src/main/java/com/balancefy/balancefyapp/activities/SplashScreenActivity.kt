package com.balancefy.balancefyapp.activities

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.balancefy.balancefyapp.databinding.ActivitySplashScreenBinding
import layout.fadeIn
import layout.fadeOut

class SplashScreenActivity : AppCompatActivity() {

    private lateinit var binding: ActivitySplashScreenBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivitySplashScreenBinding.inflate(layoutInflater)

        setContentView(binding.root)

        binding.gifLoad.fadeOut(1500)

        binding.logoBalancefy.fadeIn(
            3200
        ) { action() }

    }

    fun action() {
        startActivity(Intent(baseContext, IntroActivity::class.java))
    }
}