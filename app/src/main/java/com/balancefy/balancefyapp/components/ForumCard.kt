package com.balancefy.balancefyapp.components

import android.content.Context
import android.net.Uri
import android.util.AttributeSet
import android.view.LayoutInflater
import android.widget.LinearLayout
import com.balancefy.balancefyapp.R
import com.balancefy.balancefyapp.databinding.ProfilePostCardBinding

class ForumCard(context: Context, attrs: AttributeSet) : LinearLayout(context, attrs) {

    private val binding: ProfilePostCardBinding = ProfilePostCardBinding.inflate(
        LayoutInflater.from(context), this, true
    )

    init {
        val attributes = context.obtainStyledAttributes(attrs, R.styleable.CurrentTask)

        //setAvatar(attributes.getDrawable(R.styleable.ForumCard_forum_card_avatar) ?: R.drawable.ic_facebook)
        setName(attributes.getString(R.styleable.ForumCard_forum_card_name) ?: "")
        setTitle(attributes.getString(R.styleable.ForumCard_forum_card_title) ?: "")
        setDescription(attributes.getString(R.styleable.ForumCard_forum_card_description) ?: "")
        setLikes(attributes.getString(R.styleable.ForumCard_forum_card_likes) ?: "")
    }

    /*fun setAvatar(avatar: Any) {
        binding.postsProfileImage.setImageURI(avatar)
    }*/

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
}