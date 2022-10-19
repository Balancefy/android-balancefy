package com.balancefy.balancefyapp.activities

import android.content.Intent
import android.content.SharedPreferences
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.core.os.bundleOf
import androidx.navigation.NavController
import com.balancefy.balancefyapp.R
import com.balancefy.balancefyapp.databinding.ActivityMainBinding
import com.balancefy.balancefyapp.frames.*

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
            swapFragment(it.itemId)
            true
        }

        binding.bottomMenu.setOnMenuItemClickListener {
            swapFragment(it.itemId)
            true
        }
        initHome()
    }

    private fun initHome() {
        supportFragmentManager.beginTransaction().add(
            binding.fragmentContainerView.id,
            HomeFragment()
        ).commit()

        binding.topAppBar.title =  getString(R.string.description_home)
    }

    fun swapFragment(fragmentId : Int){
        val transaction = supportFragmentManager.beginTransaction()
        val container = binding.fragmentContainerView.id
        val bundle = bundleOf(
            "nameUser" to preferences.getString("nameUser", null)
        )

        val fragment = when(fragmentId){
            R.id.home_fragment -> {
                binding.topAppBar.title =  getString(R.string.description_home)
                HomeFragment()
            }
            R.id.goal_fragment -> {
                binding.topAppBar.title =  getString(R.string.description_goal)
                GoalFragment()
            }
            R.id.forum_fragment -> {
                binding.topAppBar.title =  getString(R.string.description_forum)
                ForumFragment()
            }
            R.id.rank_fragment -> {
                binding.topAppBar.title =  getString(R.string.description_rank)
                RankFragment()
            }
            R.id.profile_fragment -> {
                binding.topAppBar.title =  getString(R.string.description_profile)
                ProfileFragment()
            }
            else -> return
        }

        fragment.arguments = bundle

        transaction.replace(container, fragment).commit()
    }

    fun logOut() {
        val editor = preferences.edit()
        editor.putString("nameUser", null)
        editor.apply()
        startActivity(Intent(this, IntroActivity::class.java))
    }
}