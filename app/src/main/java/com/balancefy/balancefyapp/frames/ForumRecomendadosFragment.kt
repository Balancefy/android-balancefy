package com.balancefy.balancefyapp.frames

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.balancefy.balancefyapp.R
import com.balancefy.balancefyapp.databinding.FragmentForumGeralBinding
import com.balancefy.balancefyapp.databinding.FragmentForumRecomendadosBinding

class ForumRecomendadosFragment : Fragment() {
    private lateinit var binding: FragmentForumRecomendadosBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentForumRecomendadosBinding.inflate(layoutInflater)
        return binding.root
    }
}