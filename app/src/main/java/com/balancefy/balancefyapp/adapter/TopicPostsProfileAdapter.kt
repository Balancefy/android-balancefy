package com.balancefy.balancefyapp.adapter

import android.os.Build
import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.annotation.RequiresApi
import androidx.core.net.toUri
import androidx.recyclerview.widget.RecyclerView
import com.balancefy.balancefyapp.R
import com.balancefy.balancefyapp.databinding.ProfilePostCardBinding
import com.balancefy.balancefyapp.models.response.FeedTopicoResponseDto

class TopicPostsProfileAdapter(
    private val listTopics: List<FeedTopicoResponseDto>?,
    private val onClick: (mensagem: String) -> Unit,
) : RecyclerView.Adapter<RecyclerView.ViewHolder>() {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        val inflater =
            ProfilePostCardBinding.inflate(LayoutInflater.from(parent.context), parent, false)

        return TopicPostsProfileHolder(inflater)
    }

    inner class TopicPostsProfileHolder(
        private val binding: ProfilePostCardBinding
    ) : RecyclerView.ViewHolder(binding.root) {

        fun attach(topicPosts: FeedTopicoResponseDto?) {
            if (topicPosts != null) {
                if (topicPosts.autor.fkUsuario.avatar.isEmpty()){
                    binding.postsProfileImage.setImageResource(R.drawable.ic_account)
                }else {
                    binding.postsProfileImage.setImageURI(topicPosts.autor.fkUsuario.avatar.toUri())
                }
                binding.tvPostsCreateProfileName.text = topicPosts.autor.fkUsuario.name
                binding.tvTitlePost.text = topicPosts.topicoResponseDto.titulo
                binding.tvTextPost.text = topicPosts.topicoResponseDto.descricao
                binding.tvPostComments.text = topicPosts.commentSize.toString()
                binding.tvPostLikes.text = topicPosts.topicoResponseDto.likes.toString()
                binding.tvPostTimer.text = topicPosts.topicoResponseDto.createdAt.take(10)
            }

            binding.tvTitlePost.setOnClickListener {
                onClick("Redirecionar para Post da Pessoa (in development)")
            }

            binding.icPostLikes.setOnClickListener {
                likeAPost(binding.tvPostLikes, binding.icPostLikes)
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

    private fun likeAPost(tvLikes: TextView, icLikes: ImageView) {
//        icLikes.setImageResource()
    }
}