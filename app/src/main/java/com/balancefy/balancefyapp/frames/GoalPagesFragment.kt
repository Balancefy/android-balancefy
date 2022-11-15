package com.balancefy.balancefyapp.frames

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.balancefy.balancefyapp.databinding.FragmentGoalPagesBinding

class GoalPagesFragment : Fragment() {
    private lateinit var binding: FragmentGoalPagesBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentGoalPagesBinding.inflate(layoutInflater)
        return binding.root
    }
}