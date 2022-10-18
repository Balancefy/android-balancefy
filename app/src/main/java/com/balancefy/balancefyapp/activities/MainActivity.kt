package com.balancefy.balancefyapp.activities

import android.content.Intent
import android.content.SharedPreferences
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import com.balancefy.balancefyapp.R
import com.balancefy.balancefyapp.databinding.ActivityMainBinding
import com.balancefy.balancefyapp.frames.Forum
import com.balancefy.balancefyapp.frames.Goal
import com.balancefy.balancefyapp.frames.Home
import com.balancefy.balancefyapp.frames.Rank

class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding
    lateinit var preferences : SharedPreferences

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        preferences = getSharedPreferences("Auth", MODE_PRIVATE)
        setContentView(binding.root)
        replaceFragment(Home())

        binding.topAppBar.setOnMenuItemClickListener(){
            when(it.itemId){
                R.id.bt_profile_setting -> startActivity(Intent(this, ProfileActivity::class.java))
            }
            true
        }

        binding.bottomNavigation.setOnItemSelectedListener {
            when (it.itemId) {
                R.id.home -> replaceFragment(Home())
                R.id.forum -> replaceFragment(Forum())
                R.id.goal -> replaceFragment(Goal())
                R.id.rank -> replaceFragment(Rank())
            }
            true
        }
    }

    private fun replaceFragment(fragment: Fragment) {
        val fragmentManager = supportFragmentManager
        val fragmentTransaction = fragmentManager.beginTransaction()
        fragmentTransaction.replace(R.id.frame_layout, fragment)
        fragmentTransaction.commit()
    }

    fun logOut() {
        val editor = preferences.edit()
        editor.putString("nameUser", null)
        editor.apply()
        startActivity(Intent(this, IntroActivity::class.java))
    }
}