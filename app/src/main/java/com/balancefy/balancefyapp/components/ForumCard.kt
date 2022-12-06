package com.balancefy.balancefyapp.components

import android.content.Context
import android.graphics.BitmapFactory
import android.net.Uri
import android.util.AttributeSet
import android.view.LayoutInflater
import android.widget.LinearLayout
import com.balancefy.balancefyapp.R
import com.balancefy.balancefyapp.databinding.ProfilePostCardBinding
import java.net.URL
import java.time.LocalDate
import java.time.format.DateTimeFormatter

class ForumCard(context: Context, attrs: AttributeSet) : LinearLayout(context, attrs) {

    private val binding: ProfilePostCardBinding = ProfilePostCardBinding.inflate(
        LayoutInflater.from(context), this, true
    )

    fun setName(name: String) {
        binding.tvPostsCreateProfileName.text = name
    }

    fun setTitle(title: String) {
        binding.tvTitlePost.text = title
    }

    fun setDescription(description: String) {
        binding.tvTextPost.text = description
    }

    fun setLikes(likes: String) {
        binding.tvPostLikes.text = likes
    }

    fun setCreated(date: String) {
        binding.tvPostTimer.text = date
    }

    fun setComments(comment: String) {
        binding.tvPostComments.text = comment
    }
}