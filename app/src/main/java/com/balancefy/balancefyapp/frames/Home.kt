package com.balancefy.balancefyapp.frames

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import com.balancefy.balancefyapp.R
import com.balancefy.balancefyapp.databinding.FragmentHomeBinding

class Home : Fragment(R.layout.fragment_home) {
    private lateinit var binding: FragmentHomeBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentHomeBinding.inflate(layoutInflater, container, false)

        binding.bottomMenu.setOnMenuItemClickListener {
            when (it.itemId) {
                R.id.home_fragment -> true
                R.id.forum_fragment -> findNavController().navigate(R.id.action_home_to_forum)
                R.id.goal_fragment -> findNavController().navigate(R.id.action_home_to_goal)
                R.id.rank_fragment -> findNavController().navigate(R.id.action_home_to_rank)
            }
            true
        }

        return binding.root
    }
}