package com.balancefy.balancefyapp.frames

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import com.balancefy.balancefyapp.R
import com.balancefy.balancefyapp.databinding.FragmentRankBinding

class Rank : Fragment(R.layout.fragment_rank) {
    private lateinit var binding: FragmentRankBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentRankBinding.inflate(layoutInflater, container, false)

        binding.bottomMenu.setOnMenuItemClickListener {
            when (it.itemId) {
                R.id.home_fragment -> findNavController().navigate(R.id.action_rank_to_home)
                R.id.forum_fragment -> findNavController().navigate(R.id.action_rank_to_forum)
                R.id.goal_fragment -> findNavController().navigate(R.id.action_rank_to_goal)
                R.id.rank_fragment -> true
            }
            true
        }

        return binding.root
    }
}