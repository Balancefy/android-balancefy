package com.balancefy.balancefyapp.frames

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import com.balancefy.balancefyapp.R
import com.balancefy.balancefyapp.databinding.FragmentForumBinding

class Forum : Fragment(R.layout.fragment_forum) {
    private lateinit var binding: FragmentForumBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentForumBinding.inflate(layoutInflater, container, false)

        binding.bottomMenu.setOnMenuItemClickListener {
            when (it.itemId) {
                R.id.home_fragment -> findNavController().navigate(R.id.action_forum_to_home)
                R.id.forum_fragment -> true
                R.id.goal_fragment -> findNavController().navigate(R.id.action_forum_to_goal)
                R.id.rank_fragment -> findNavController().navigate(R.id.action_forum_to_rank)
            }
            true
        }

        return binding.root
    }
}