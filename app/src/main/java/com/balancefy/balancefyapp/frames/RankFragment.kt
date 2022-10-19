package com.balancefy.balancefyapp.frames

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.balancefy.balancefyapp.databinding.FragmentRankBinding

class RankFragment : Fragment() {
    private lateinit var binding: FragmentRankBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentRankBinding.inflate(layoutInflater)
        return binding.root
    }
}