package com.balancefy.balancefyapp.frames

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.setFragmentResultListener
import com.balancefy.balancefyapp.R
import com.balancefy.balancefyapp.databinding.FragmentGoalDetailsBinding
import com.balancefy.balancefyapp.databinding.FragmentGoalPagesBinding
import com.balancefy.balancefyapp.databinding.FragmentGoalRoadmapBinding

class GoalDetailsFragment : Fragment() {
    private lateinit var binding: FragmentGoalDetailsBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentGoalDetailsBinding.inflate(layoutInflater)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        setFragmentResultListener("requestKey") { key, bundle ->
            val result = bundle.getInt("goalId")

            println("funfou $result")
        }
    }
}