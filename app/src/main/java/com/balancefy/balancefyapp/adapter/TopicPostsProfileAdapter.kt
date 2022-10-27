package com.balancefy.balancefyapp.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.balancefy.balancefyapp.R
import com.balancefy.balancefyapp.models.request.TopicoRequestDto

class TopicPostsProfileAdapter(
    private val listTopics: List<TopicoRequestDto>,
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

        fun attach(topicPosts : TopicoRequestDto) {
            val tvTitle = topicPostCardLayout.findViewById<TextView>(R.id.tv_titlePost)
            val tvText = topicPostCardLayout.findViewById<TextView>(R.id.tv_textPost)

            tvTitle.text = topicPosts.titulo
            tvText.text = topicPosts.conteudo

            tvTitle.setOnClickListener{
                onClick("Redirecionar para Post da Pessoa (in development)")
            }
        }
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        (holder as TopicPostsProfileHolder).attach(listTopics[position])
    }

    override fun getItemCount(): Int {
        return listTopics.size
    }
}