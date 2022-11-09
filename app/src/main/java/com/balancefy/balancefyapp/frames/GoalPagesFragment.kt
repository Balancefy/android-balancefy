package com.balancefy.balancefyapp.frames

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.os.bundleOf
import androidx.fragment.app.Fragment
import androidx.fragment.app.setFragmentResult
import androidx.navigation.findNavController
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.balancefy.balancefyapp.R
import com.balancefy.balancefyapp.adapter.GoalCardsAdapter
import com.balancefy.balancefyapp.databinding.FragmentGoalBinding
import com.balancefy.balancefyapp.databinding.FragmentGoalPagesBinding
import com.balancefy.balancefyapp.models.response.GoalsResponse
import com.balancefy.balancefyapp.rest.Rest
import com.google.android.material.snackbar.Snackbar
import com.google.android.material.tabs.TabLayout
import com.google.gson.annotations.SerializedName
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

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