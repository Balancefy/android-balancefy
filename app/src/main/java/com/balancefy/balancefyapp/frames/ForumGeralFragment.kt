package com.balancefy.balancefyapp.frames

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.balancefy.balancefyapp.R
import com.balancefy.balancefyapp.databinding.FragmentForumBinding
import com.balancefy.balancefyapp.databinding.FragmentForumGeralBinding


class ForumGeralFragment : Fragment() {
    private lateinit var binding: FragmentForumGeralBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentForumGeralBinding.inflate(layoutInflater)
        return binding.root
    }

}