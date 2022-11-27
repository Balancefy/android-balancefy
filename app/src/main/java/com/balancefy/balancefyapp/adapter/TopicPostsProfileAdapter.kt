package com.balancefy.balancefyapp.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import androidx.recyclerview.widget.RecyclerView
import com.balancefy.balancefyapp.R
import com.balancefy.balancefyapp.databinding.ProfilePostCardBinding
import com.balancefy.balancefyapp.models.response.FeedTopicoResponseDto
import com.balancefy.balancefyapp.models.response.TopicoResponseDto
import com.balancefy.balancefyapp.rest.Rest
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class TopicPostsProfileAdapter(
    private val listTopics: List<FeedTopicoResponseDto>,
    private val token: String,
    private val onClick: (mensagem: String) -> Unit


) : RecyclerView.Adapter<RecyclerView.ViewHolder>() {
    private var postLiked = false
    private var amountLike = 0


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        val inflater =
            ProfilePostCardBinding.inflate(LayoutInflater.from(parent.context), parent, false)

        return TopicPostsProfileHolder(inflater)
    }

    inner class TopicPostsProfileHolder(
        private val binding: ProfilePostCardBinding
    ) : RecyclerView.ViewHolder(binding.root) {

        fun attach(topicPosts: FeedTopicoResponseDto?) {
            setDefaultImage(topicPosts?.autor?.fkUsuario!!.avatar, binding.postsProfileImage)

            binding.tvPostsCreateProfileName.text = topicPosts.autor.fkUsuario.name
            binding.tvTitlePost.text = topicPosts.topicoResponseDto.titulo
            binding.tvTextPost.text = topicPosts.topicoResponseDto.descricao
            binding.tvPostComments.text = topicPosts.commentSize.toString()
            binding.tvPostLikes.text = topicPosts.topicoResponseDto.likes.toString()
            binding.tvPostTimer.text = topicPosts.topicoResponseDto.createdAt.take(10)

            isLiked(topicPosts.liked, binding.icPostLikes)

            postLiked = topicPosts.liked

            amountLike = topicPosts.topicoResponseDto.likes

            binding.tvTitlePost.setOnClickListener {
                onClick("Redirecionar para Post da Pessoa (in development)")
            }

            binding.icPostLikes.setOnClickListener {
                postLiked = !postLiked

                if (postLiked){
                    isLiked(true, binding.icPostLikes)
                    binding.tvPostLikes.text = likeAPost(topicPosts.topicoResponseDto.id, amountLike).toString()
                }else {
                    isLiked(false, binding.icPostLikes)
                    binding.tvPostLikes.text = unlikePost(topicPosts.topicoResponseDto.id, amountLike).toString()
                }
            }
        }
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        (holder as TopicPostsProfileHolder).attach(listTopics[position])
    }

    override fun getItemCount(): Int {
        return listTopics.size
    }

    private fun isLiked(liked : Boolean, card: ImageView){
        if (liked){
            card.setImageResource(R.drawable.ic_post_likes_enable)
        }else {
            card.setImageResource(R.drawable.ic_post_likes)
        }
    }

    private fun likeAPost(idTopic: Int, amountLike: Int): Int{
        var resultLike = amountLike
        Rest.getForumInstance().addLike("Bearer $token", idTopic)
            .enqueue(object : Callback<TopicoResponseDto> {
                override fun onResponse(
                    call: Call<TopicoResponseDto>,
                    response: Response<TopicoResponseDto>
                ) {
                    val data = response.body()
                    println(response.code())
                    when (response.code()) {
                        200 -> {
                            if (data != null) {
                                resultLike = data.likes
                            }
                        }
                    }
                }

                override fun onFailure(call: Call<TopicoResponseDto>, t: Throwable) {
                    return
                }
            })
        return resultLike
    }

    private fun unlikePost(idTopic: Int, amountLike: Int): Int{
        var resultLike = amountLike
        Rest.getForumInstance().unlike("Bearer $token", idTopic)
            .enqueue(object : Callback<TopicoResponseDto> {
                override fun onResponse(
                    call: Call<TopicoResponseDto>,
                    response: Response<TopicoResponseDto>
                ) {
                    val data = response.body()
                    println(response.code())
                    when (response.code()) {
                        200 -> {
                            if (data != null) {
                                resultLike = data.likes
                            }
                        }
                    }
                }

                override fun onFailure(call: Call<TopicoResponseDto>, t: Throwable) {
                    return
                }
            })
        return resultLike
    }

    private fun setDefaultImage(avatarImg : String, card: View) {
        if (avatarImg.isEmpty()) {
            card.setBackgroundResource(R.drawable.ic_account)
        }
        //else {
//            card.background = avatarImg.toUri()
//        }
    }
}