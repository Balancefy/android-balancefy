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
import com.balancefy.balancefyapp.adapter.TopicPostsProfileAdapter
import com.balancefy.balancefyapp.databinding.FragmentForumRecomendadosBinding
import com.balancefy.balancefyapp.models.response.FeedTopicoResponseDto
import com.balancefy.balancefyapp.models.response.ListaFeedTopicoResponse
import com.balancefy.balancefyapp.rest.Rest
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class ForumRecomendadosFragment : Fragment() {
    private lateinit var binding: FragmentForumRecomendadosBinding
    private lateinit var preferences: SharedPreferences
    private lateinit var token: String

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentForumRecomendadosBinding.inflate(layoutInflater)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {

        preferences = requireContext().getSharedPreferences("Auth", AppCompatActivity.MODE_PRIVATE)

        token = preferences.getString("token", null)!!

        recyclerViewConfigurationMostLiked()
        super.onViewCreated(view, savedInstanceState)
    }

    private fun recyclerViewConfigurationMostLiked() {
        Rest.getForumInstance().getMostLike("Bearer $token")
            .enqueue(object : Callback<ListaFeedTopicoResponse> {
                override fun onResponse(
                    call: Call<ListaFeedTopicoResponse>,
                    response: Response<ListaFeedTopicoResponse>
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
                            ).show()
                        }
                    }
                }

                override fun onFailure(call: Call<ListaFeedTopicoResponse>, t: Throwable) {
                    Toast.makeText(context, t.message, Toast.LENGTH_SHORT).show()
                }
            })
    }

    private fun showMessageTest(message: String) {
        Toast.makeText(context, message, Toast.LENGTH_SHORT).show()
    }

    private fun configRecycleView(posts: List<FeedTopicoResponseDto>?) {
        if (posts!!.isEmpty()) {
            binding.emptyListOfTopics.visibility = View.VISIBLE

            binding.recyclerContainer.visibility = View.GONE
        } else {
            binding.emptyListOfTopics.visibility = View.GONE

            binding.recyclerContainer.visibility = View.VISIBLE

            val recyclerContainer = binding.recyclerContainer

            recyclerContainer.layoutManager = LinearLayoutManager(context)

            recyclerContainer.adapter = TopicPostsProfileAdapter(
                posts,
                token
            ) { mensagem -> showMessageTest(mensagem) }
        }
    }
}