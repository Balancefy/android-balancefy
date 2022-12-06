package com.balancefy.balancefyapp.frames

import android.content.Context
import android.content.Context.INPUT_METHOD_SERVICE
import android.content.SharedPreferences
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.inputmethod.InputMethodManager
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat.getSystemService
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import com.balancefy.balancefyapp.R
import com.balancefy.balancefyapp.adapter.CommentReplyAdapter
import com.balancefy.balancefyapp.databinding.FragmentCommentReplyBinding
import com.balancefy.balancefyapp.models.request.CommentReplyRequest
import com.balancefy.balancefyapp.models.response.FeedCommentReplyResponseDto
import com.balancefy.balancefyapp.models.response.ListaFeedCommentReplyResponse
import com.balancefy.balancefyapp.rest.Rest
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import java.time.LocalDate
import java.time.format.DateTimeFormatter

class CommentReplyFragment : Fragment() {
    private lateinit var binding: FragmentCommentReplyBinding
    private lateinit var preferences: SharedPreferences
    private lateinit var token: String
    private var accountId: Int = 0
    private lateinit var accountName: String
    private lateinit var accountTitle: String
    private lateinit var accountContent: String
    private lateinit var accountLikes: String
    private lateinit var accountCreatedAt: String
    private lateinit var accountComments: String
    private lateinit var accountAvatar: String
    private var postLiked: Boolean = false

    private var topicIdPost: Int? = null


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentCommentReplyBinding.inflate(layoutInflater)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        preferences = requireContext().getSharedPreferences("Auth", AppCompatActivity.MODE_PRIVATE)
        token = preferences.getString("token", null)!!
        topicIdPost = preferences.getInt("postId", 3)
        accountId = preferences.getInt("postAccountId", 0)
        accountName = preferences.getString("postAccountName", "")!!
        accountTitle = preferences.getString("postAccountTitle", "")!!
        accountContent = preferences.getString("postAccountContent", "")!!
        accountLikes = preferences.getInt("postAccountLikes", 0).toString()
        accountCreatedAt = preferences.getString("postAccountCreatedAt", "")!!
        accountComments = preferences.getInt("postAccountComments", 0).toString()
        accountAvatar = preferences.getString("postAccountAvatar", "")!!
        postLiked = preferences.getBoolean("postLiked", false)

        recyclerViewConfigurationGeral()

        binding.forumCard.setTitle(accountTitle)
        binding.forumCard.setName(accountName)
        binding.forumCard.setDescription(accountContent)
        binding.forumCard.setLikes(accountLikes)
        binding.forumCard.setCreated(accountCreatedAt.take(10))
        binding.forumCard.setComments(accountComments)
        binding.forumCard.setAvatar(accountAvatar)
        binding.forumCard.isLiked(postLiked)

        binding.sendIcon.setOnClickListener {
            createComment()
        }
    }

    private fun recyclerViewConfigurationGeral() {
        Rest.getForumInstance().getComments("Bearer $token", topicIdPost!!)
            .enqueue(object : Callback<ListaFeedCommentReplyResponse> {
                override fun onResponse(
                    call: Call<ListaFeedCommentReplyResponse>,
                    response: Response<ListaFeedCommentReplyResponse>
                ) {
                    val data = response.body()?.listTopic
                    when (response.code()) {
                        200 -> {
                            println(response.body())
                            println(topicIdPost)
                            configRecycleView(data)
                        }
                        else -> {
                            println(response.code())
                            Toast.makeText(
                                context,
                                getString(R.string.connection_error),
                                Toast.LENGTH_SHORT
                            ).show()
                        }
                    }
                    println(response.code())
                }
                override fun onFailure(call: Call<ListaFeedCommentReplyResponse>, t: Throwable) {
                    Toast.makeText(context, t.message, Toast.LENGTH_SHORT).show()
                    println(t.message)
                }
            })
    }

    private fun configRecycleView(posts: List<FeedCommentReplyResponseDto>?) {
        if (posts!!.isEmpty()) {
            binding.emptyListOfTopics.visibility = View.VISIBLE
            binding.recyclerContainerReply.visibility = View.GONE
        } else {
            binding.emptyListOfTopics.visibility = View.GONE
            binding.recyclerContainerReply.visibility = View.VISIBLE
            val recyclerContainer = binding.recyclerContainerReply
            recyclerContainer.layoutManager = LinearLayoutManager(context)
            recyclerContainer.adapter = CommentReplyAdapter(
                posts
            )
        }
    }

    private fun createComment() {
        val body = CommentReplyRequest(
            content = binding.etContent.text.toString(),
            post = topicIdPost!!
        )
        Rest.getForumInstance().createComment("Bearer $token", body).enqueue(object : Callback<Unit> {
            override fun onResponse(
                call: Call<Unit>,
                response: Response<Unit>
            ) {
                when(response.code()){
                    201 -> {
                        Toast.makeText(context, R.string.created_post, Toast.LENGTH_SHORT).show()
                        recyclerViewConfigurationGeral()
                        closeKeyboard()
                    }
                    else -> {
                        Toast.makeText(context, R.string.register_error, Toast.LENGTH_SHORT).show()
                    }
                }
            }

            override fun onFailure(call: Call<Unit>, t: Throwable) {
                Toast.makeText(context, R.string.connection_error, Toast.LENGTH_SHORT).show()
            }
        })
    }

    private fun closeKeyboard() {
        binding.etContent.text.clear()
        val view: View? = binding.etContent
        view?.let {
            val imm = requireContext().getSystemService(INPUT_METHOD_SERVICE) as InputMethodManager

            imm.hideSoftInputFromWindow(it.windowToken, 0)
        }
    }

}