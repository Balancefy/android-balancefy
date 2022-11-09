package com.balancefy.balancefyapp.frames

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.balancefy.balancefyapp.R
import com.balancefy.balancefyapp.databinding.FragmentGoalPagesBinding
import com.balancefy.balancefyapp.databinding.FragmentGoalRoadmapBinding

class GoalRoadmapFragment : Fragment() {
    private lateinit var binding: FragmentGoalRoadmapBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentGoalRoadmapBinding.inflate(layoutInflater)
        return binding.root
    }
}