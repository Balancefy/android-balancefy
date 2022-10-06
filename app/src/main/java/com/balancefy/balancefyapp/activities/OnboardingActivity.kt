package com.balancefy.balancefyapp.activities

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.balancefy.balancefyapp.databinding.ActivityOnboardingBinding

class OnboardingActivity : AppCompatActivity() {

    private lateinit var binding: ActivityOnboardingBinding


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityOnboardingBinding.inflate(layoutInflater)

        setContentView(binding.root)

        binding.btn.setOnClickListener {
            startActivity(Intent(baseContext, RegisterActivity::class.java))
        }
    }


}