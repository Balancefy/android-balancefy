package com.balancefy.balancefyapp.activities

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.balancefy.balancefyapp.adapter.OnboardingAdapter
import com.balancefy.balancefyapp.databinding.ActivityOnboardingBinding
import com.balancefy.balancefyapp.databinding.OnboardingContainerBinding
import com.balancefy.balancefyapp.fragments.OnboardingFragment1
import com.balancefy.balancefyapp.fragments.OnboardingFragment2
import com.balancefy.balancefyapp.fragments.OnboardingFragment3

class OnboardingActivity : AppCompatActivity() {

    private lateinit var binding: OnboardingContainerBinding


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = OnboardingContainerBinding.inflate(layoutInflater)

        setContentView(binding.root)

        val viewPager = binding.viewPager

        viewPager.adapter = OnboardingAdapter(
            supportFragmentManager,
            lifecycle,
            listOf(
                OnboardingFragment1(),
                OnboardingFragment2(),
                OnboardingFragment3(),
            )
        )

//        binding.btn_start.setOnClickListener {
//            startActivity(Intent(baseContext, IntroActivity::class.java))
//        }


    }


}