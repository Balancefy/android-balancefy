package com.balancefy.balancefyapp.adapter

import android.annotation.SuppressLint
import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.RecyclerView
import com.balancefy.balancefyapp.R
import com.balancefy.balancefyapp.databinding.CommentReplyCardBinding
import com.balancefy.balancefyapp.models.response.FeedCommentReplyResponseDto

class CommentReplyAdapter(
    private val listTopics: List<FeedCommentReplyResponseDto>,
    token: String,
    param: (Any) -> Unit,
) : RecyclerView.Adapter<RecyclerView.ViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        val inflater =
            CommentReplyCardBinding.inflate(LayoutInflater.from(parent.context), parent, false)

        return TopicPostsProfileHolder(inflater, parent.context)
    }

    inner class TopicPostsProfileHolder(
        private val binding: CommentReplyCardBinding,
        val context: Context
    ) : RecyclerView.ViewHolder(binding.root) {
        private var preferences =
            context.getSharedPreferences("Auth", AppCompatActivity.MODE_PRIVATE)
        private var postLiked = false

        @SuppressLint("SetTextI18n")
        fun attach(topicPosts: FeedCommentReplyResponseDto?) {

            setDefaultImage(topicPosts?.autor?.fkUsuario!!.avatar, binding.postsProfileImage)

            binding.tvName.text = topicPosts.autor.fkUsuario.name
            binding.tvTextPost.text = topicPosts.commentReplyDtio.description

            isLiked(topicPosts.liked, binding.icPostLikes)

            postLiked = topicPosts.liked
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
            card.setImageResource(R.drawable.ic_post_likes)
        }
    }

    /*private fun likeAPost(idTopic: Int) {
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
    }*/

    /*private fun unlikePost(idTopic: Int) {
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
    }*/

    private fun setDefaultImage(avatarImg: String, card: View) {
        if (avatarImg.isEmpty()) {
            card.setBackgroundResource(R.drawable.ic_account)
        }
        //else {
//            card.background = avatarImg.toUri()
//        }
    }
}