package com.balancefy.balancefyapp.adapter

import android.annotation.SuppressLint
import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.ImageView
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.RecyclerView
import com.balancefy.balancefyapp.R
import com.balancefy.balancefyapp.databinding.ProfilePostCardBinding
import com.balancefy.balancefyapp.frames.CommentReplyFragment
import com.balancefy.balancefyapp.frames.ProfileAlternativeFragment
import com.balancefy.balancefyapp.models.response.FeedTopicoResponseDto
import com.balancefy.balancefyapp.models.response.TopicoResponseDto
import com.balancefy.balancefyapp.rest.Rest
import com.squareup.picasso.Picasso
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class TopicPostsProfileAdapter(
    private val listTopics: List<FeedTopicoResponseDto>,
    private val token: String,
    private val onClick: (mensagem: String) -> Unit

) : RecyclerView.Adapter<RecyclerView.ViewHolder>() {


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        val inflater =
            ProfilePostCardBinding.inflate(LayoutInflater.from(parent.context), parent, false)

        return TopicPostsProfileHolder(inflater, parent.context)
    }

    inner class TopicPostsProfileHolder(
        private val binding: ProfilePostCardBinding,
        val context: Context
    ) : RecyclerView.ViewHolder(binding.root) {
        private var preferences =
            context.getSharedPreferences("Auth", AppCompatActivity.MODE_PRIVATE)
        private var postLiked = false

        @SuppressLint("SetTextI18n")
        fun attach(topicPosts: FeedTopicoResponseDto?) {

            val avatarImg = topicPosts!!.autor.fkUsuario.avatar

            if (avatarImg != "") {
                Picasso.get().load(avatarImg).into(binding.icPostsProfileImage)
            }else{
                binding.icPostsProfileImage.setBackgroundResource(R.drawable.ic_account)
            }

            binding.tvPostsCreateProfileName.text = topicPosts.autor.fkUsuario.name
            binding.tvTitlePost.text = topicPosts.topicoResponseDto.titulo
            binding.tvTextPost.text = topicPosts.topicoResponseDto.descricao
            binding.tvPostComments.text = topicPosts.commentSize.toString()
            binding.tvPostLikes.text = topicPosts.topicoResponseDto.likes.toString()
            binding.tvPostTimer.text = topicPosts.topicoResponseDto.createdAt.take(10)

            isLiked(topicPosts.liked, binding.icPostLikes)

            postLiked = topicPosts.liked

            binding.tvPostsCreateProfileName.setOnClickListener {
                val editor = preferences.edit()
                editor.putString("altAccountName", topicPosts.autor.fkUsuario.name)
                editor.putInt("altAccountId", topicPosts.autor.fkUsuario.id)
                editor.putString("altAccountAvatar", topicPosts.autor.fkUsuario.avatar)
                editor.putString("altAccountBanner", topicPosts.autor.fkUsuario.banner)
                editor.apply()

                ProfileAlternativeFragment()

                //Mudar de fragment :)
                val activity = it.context as AppCompatActivity

                activity.supportFragmentManager.beginTransaction()
                    .replace(
                        R.id.fragmentContainerView,
                        ProfileAlternativeFragment()
                    ).commitNow()
            }

            binding.tvTitlePost.setOnClickListener {
                onClick("Redirecionar para Post da Pessoa (in development)")
            }

            binding.icPostLikes.setOnClickListener {
                postLiked = !postLiked

                if (postLiked) {
                    isLiked(true, binding.icPostLikes)
                    likeAPost(topicPosts.topicoResponseDto.id)
                    binding.tvPostLikes.text = (topicPosts.topicoResponseDto.likes + 1).toString()
                } else {
                    isLiked(false, binding.icPostLikes)
                    unlikePost(topicPosts.topicoResponseDto.id)
                    binding.tvPostLikes.text = (topicPosts.topicoResponseDto.likes).toString()
                }
            }

            binding.icPostComments.setOnClickListener {
                val editor = preferences.edit()
                editor.putInt("postId", topicPosts.topicoResponseDto.id)
                editor.putString("postAccountName", topicPosts.autor.fkUsuario.name)
                editor.putInt("postAccountId", topicPosts.autor.fkUsuario.id)
                editor.putString("postAccountTitle", topicPosts.topicoResponseDto.titulo)
                editor.putString("postAccountContent", topicPosts.topicoResponseDto.descricao)
                editor.putInt("postAccountLikes", topicPosts.topicoResponseDto.likes)
                editor.putString("postAccountCreatedAt", topicPosts.topicoResponseDto.createdAt)
                editor.putInt("postAccountComments", topicPosts.commentSize)
                editor.putString("postAccountAvatar", topicPosts.autor.fkUsuario.avatar)
                editor.putBoolean("postLiked", topicPosts.liked)
                editor.apply()

                val activity = it.context as AppCompatActivity

                activity.supportFragmentManager.beginTransaction()
                    .replace(
                        R.id.fragmentContainerView,
                        CommentReplyFragment()
                    ).commitNow()
            }
        }
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        (holder as TopicPostsProfileHolder).attach(listTopics[position])
    }

    override fun getItemCount(): Int {
        return listTopics.size
    }

    private fun isLiked(liked: Boolean, card: ImageView) {
        if (liked) {
            card.setImageResource(R.drawable.ic_post_likes_enable)
        } else {
            card.setImageResource(R.drawable.ic_new_joia)
        }
    }

    private fun likeAPost(idTopic: Int) {
        Rest.getForumInstance().addLike("Bearer $token", idTopic)
            .enqueue(object : Callback<TopicoResponseDto> {
                override fun onResponse(
                    call: Call<TopicoResponseDto>,
                    response: Response<TopicoResponseDto>
                ) {
                    println(response.code())
                    return
                }

                override fun onFailure(call: Call<TopicoResponseDto>, t: Throwable) {
                    return
                }
            })
    }

    private fun unlikePost(idTopic: Int) {
        Rest.getForumInstance().unlike("Bearer $token", idTopic)
            .enqueue(object : Callback<TopicoResponseDto> {
                override fun onResponse(
                    call: Call<TopicoResponseDto>,
                    response: Response<TopicoResponseDto>
                ) {
                    println(response.code())
                    return
                }
                override fun onFailure(call: Call<TopicoResponseDto>, t: Throwable) {
                    return
                }
            })
    }
}