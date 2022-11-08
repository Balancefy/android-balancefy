package com.balancefy.balancefyapp.adapter

import android.os.Build
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.annotation.RequiresApi
import androidx.recyclerview.widget.RecyclerView
import com.balancefy.balancefyapp.R
import com.balancefy.balancefyapp.models.response.FeedTopicoResponseDto

class TopicPostsProfileAdapter(
    private val listTopics: List<FeedTopicoResponseDto>?,
    private val onClick: (mensagem: String) -> Unit
) : RecyclerView.Adapter<RecyclerView.ViewHolder>(){
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        val topicPostCardLayout = LayoutInflater.from(
            parent.context
        ).inflate(R.layout.profile_post_card, parent, false)

        return TopicPostsProfileHolder(topicPostCardLayout)
    }

    inner class TopicPostsProfileHolder(
        private val topicPostCardLayout: View
    ) : RecyclerView.ViewHolder(topicPostCardLayout) {

        fun attach(topicPosts: FeedTopicoResponseDto?) {
            val tvTitle = topicPostCardLayout.findViewById<TextView>(R.id.tv_titlePost)
            val tvText = topicPostCardLayout.findViewById<TextView>(R.id.tv_textPost)
            val tvComments = topicPostCardLayout.findViewById<TextView>(R.id.tv_post_comments)
            val tvLikes = topicPostCardLayout.findViewById<TextView>(R.id.tv_post_likes)
            val tvTimer = topicPostCardLayout.findViewById<TextView>(R.id.tv_post_timer)
            val icLikes = topicPostCardLayout.findViewById<ImageView>(R.id.ic_post_likes)

            if (topicPosts != null) {
                tvTitle.text = topicPosts.topicoResponseDto.titulo
                tvText.text = topicPosts.topicoResponseDto.descricao
                tvComments.text = topicPosts.commentSize.toString()
                tvLikes.text = topicPosts.topicoResponseDto.likes.toString()
                //TODO pensar em outro jeito de passar a data
                tvTimer.text = topicPosts.topicoResponseDto.createdAt.take(10)
            }

            tvTitle.setOnClickListener{
                onClick("Redirecionar para Post da Pessoa (in development)")
            }

            icLikes.setOnClickListener{
                likeAPost(tvLikes, icLikes)
            }
        }
    }

    @RequiresApi(Build.VERSION_CODES.O)
    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        (holder as TopicPostsProfileHolder).attach(listTopics?.get(position))
    }

    override fun getItemCount(): Int {
        return listTopics?.size ?: 0
    }

    private fun likeAPost(tvLikes: TextView, icLikes : ImageView) {
//        icLikes.setImageResource()


    }
}