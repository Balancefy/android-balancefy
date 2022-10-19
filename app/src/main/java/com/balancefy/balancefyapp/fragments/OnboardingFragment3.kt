package com.balancefy.balancefyapp.fragments

import android.content.Intent
import android.content.SharedPreferences
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import com.balancefy.balancefyapp.R
import com.balancefy.balancefyapp.activities.IntroActivity
import com.balancefy.balancefyapp.databinding.FragmentOnboardingStep3Binding


class OnboardingFragment3: Fragment(R.layout.fragment_onboarding_step3) {
    private lateinit var binding: FragmentOnboardingStep3Binding
    lateinit var preferences: SharedPreferences


    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        binding = FragmentOnboardingStep3Binding.inflate(inflater)
        preferences = this.requireActivity().getSharedPreferences("Auth", AppCompatActivity.MODE_PRIVATE)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        binding.btnStart.setOnClickListener {
            val editor = preferences.edit()
            editor.putBoolean("firstLogin", false)
            editor.apply()
            startActivity(Intent(activity, IntroActivity::class.java))
        }

    }
}