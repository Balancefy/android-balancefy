package com.balancefy.balancefyapp.activities

import android.content.Intent
import android.content.SharedPreferences
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.navigation.NavController
import androidx.navigation.NavDirections
import androidx.navigation.findNavController
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.ui.navigateUp
import androidx.navigation.ui.setupActionBarWithNavController
import androidx.navigation.ui.setupWithNavController
import com.balancefy.balancefyapp.R
import com.balancefy.balancefyapp.databinding.ActivityMainBinding
import com.balancefy.balancefyapp.frames.Home

class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding
    private lateinit var navController: NavController
    lateinit var preferences : SharedPreferences

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        preferences = getSharedPreferences("Auth", MODE_PRIVATE)
        setContentView(binding.root)

        binding.topAppBar.setOnMenuItemClickListener(){
            when(it.itemId){
                R.id.bt_profile_setting -> startActivity(Intent(this, ProfileActivity::class.java))
            }
            true
        }
    }

    fun logOut() {
        val editor = preferences.edit()
        editor.putString("nameUser", null)
        editor.apply()
        startActivity(Intent(this, IntroActivity::class.java))
    }
}