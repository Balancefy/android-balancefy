package com.balancefy.balancefyapp.frames

import android.content.SharedPreferences
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import com.balancefy.balancefyapp.R
import com.balancefy.balancefyapp.adapter.CommentReplyAdapter
import com.balancefy.balancefyapp.databinding.FragmentCommentReplyBinding
import com.balancefy.balancefyapp.models.response.FeedCommentReplyResponseDto
import com.balancefy.balancefyapp.models.response.ListaFeedCommentReplyResponse
import com.balancefy.balancefyapp.models.response.ListaFeedTopicoResponse
import com.balancefy.balancefyapp.rest.Rest
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class CommentReplyFragment : Fragment() {
    private lateinit var binding: FragmentCommentReplyBinding
    private lateinit var preferences: SharedPreferences
    private lateinit var token: String
    private var topicId: Int? = null


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
        topicId = preferences.getInt("topicId", 0)

        recyclerViewConfigurationGeral()
        super.onViewCreated(view, savedInstanceState)
    }

    private fun recyclerViewConfigurationGeral() {
        Rest.getForumInstance().getComments("Bearer $token", topicId!!)
            .enqueue(object : Callback<ListaFeedCommentReplyResponse> {
                override fun onResponse(
                    call: Call<ListaFeedCommentReplyResponse>,
                    response: Response<ListaFeedCommentReplyResponse>
                ) {
                    val data = response.body()?.listTopic
                    when (response.code()) {
                        200 -> {
                            configRecycleView(data)
                        }
                        else -> {
                            println(response.code())
                            Toast.makeText(
                                context,
                                getString(R.string.connection_error),
                                Toast.LENGTH_SHORT
                            )
                                .show()
                        }
                    }
                }

                override fun onFailure(call: Call<ListaFeedCommentReplyResponse>, t: Throwable) {
                    Toast.makeText(context, t.message, Toast.LENGTH_SHORT).show()
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
                posts,
                token
            ) { mensagem -> showMessageTest(mensagem as String) }
        }
    }

    private fun showMessageTest(message: String) {
        Toast.makeText(context, message, Toast.LENGTH_SHORT).show()
    }
}