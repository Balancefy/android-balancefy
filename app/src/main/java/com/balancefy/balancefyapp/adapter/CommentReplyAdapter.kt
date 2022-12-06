package com.balancefy.balancefyapp.adapter

import android.annotation.SuppressLint
import android.content.Context
import android.graphics.BitmapFactory
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import androidx.recyclerview.widget.RecyclerView
import com.balancefy.balancefyapp.R
import com.balancefy.balancefyapp.databinding.CommentReplyCardBinding
import com.balancefy.balancefyapp.models.response.FeedCommentReplyResponseDto
import com.squareup.picasso.Picasso
import java.net.URL

class CommentReplyAdapter(
    private val listTopics: List<FeedCommentReplyResponseDto>,
) : RecyclerView.Adapter<RecyclerView.ViewHolder>() {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        val inflater =
            CommentReplyCardBinding.inflate(LayoutInflater.from(parent.context), parent, false)

        return CommentReplyHolder(inflater, parent.context)
    }

    inner class CommentReplyHolder(
        private val binding: CommentReplyCardBinding,
        val context: Context
    ) : RecyclerView.ViewHolder(binding.root) {
        @SuppressLint("SetTextI18n")
        fun attach(topicPosts: FeedCommentReplyResponseDto?) {
            if(topicPosts?.autor?.usuario?.avatar!!.isNotEmpty()) {
                Picasso.get().load(topicPosts.autor.usuario.avatar).into(binding.postsProfileImage)
            }

            binding.tvName.text = topicPosts.autor.usuario.name
            binding.tvTextPost.text = topicPosts.description
        }
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        (holder as CommentReplyHolder).attach(listTopics[position])
    }

    override fun getItemCount(): Int {
        return listTopics.size
    }
}