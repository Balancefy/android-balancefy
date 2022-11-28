package com.balancefy.balancefyapp.frames

import android.content.SharedPreferences
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import com.balancefy.balancefyapp.databinding.FragmentCommentReplyBinding

class CommentReplyFragment : Fragment() {
    private lateinit var binding: FragmentCommentReplyBinding
    private lateinit var preferences: SharedPreferences
    private lateinit var token: String

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentCommentReplyBinding.inflate(layoutInflater)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        preferences = requireContext().getSharedPreferences("Auth", AppCompatActivity.MODE_PRIVATE)
        token = preferences.getString("token", null)!!
        super.onViewCreated(view, savedInstanceState)
    }
}