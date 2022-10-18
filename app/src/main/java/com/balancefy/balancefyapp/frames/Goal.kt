package com.balancefy.balancefyapp.frames

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import com.balancefy.balancefyapp.R
import com.balancefy.balancefyapp.databinding.FragmentGoalBinding

class Goal : Fragment(R.layout.fragment_goal) {

    private lateinit var binding: FragmentGoalBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentGoalBinding.inflate(layoutInflater, container, false)

        binding.bottomMenu.setOnMenuItemClickListener {
            when (it.itemId) {
                R.id.home_fragment -> findNavController().navigate(R.id.action_goal_to_home)
                R.id.forum_fragment -> findNavController().navigate(R.id.action_goal_to_forum)
                R.id.goal_fragment -> true
                R.id.rank_fragment -> findNavController().navigate(R.id.action_goal_to_rank)
            }
            true
        }

        return binding.root
    }

}