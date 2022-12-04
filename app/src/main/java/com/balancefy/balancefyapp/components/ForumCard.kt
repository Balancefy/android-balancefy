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

class ForumCard(context: Context, attrs: AttributeSet) : LinearLayout(context, attrs) {

    private val binding: ProfilePostCardBinding = ProfilePostCardBinding.inflate(
        LayoutInflater.from(context), this, true
    )

    init {
        val attributes = context.obtainStyledAttributes(attrs, R.styleable.ForumCard)
        /*val imageUrl = URL(attributes.getString(R.styleable.ForumCard_forum_card_avatar))
        val photo = BitmapFactory.decodeStream(imageUrl.openConnection().getInputStream());
        binding.postsProfileImage.setImageBitmap(photo)*/
        setName(attributes.getString(R.styleable.ForumCard_forum_card_name) ?: "sem nome")
        setTitle(attributes.getString(R.styleable.ForumCard_forum_card_title) ?: "sem titulo")
        setDescription(attributes.getString(R.styleable.ForumCard_forum_card_description) ?: "sem descricao")
        setLikes(attributes.getString(R.styleable.ForumCard_forum_card_likes) ?: "0")
    }

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