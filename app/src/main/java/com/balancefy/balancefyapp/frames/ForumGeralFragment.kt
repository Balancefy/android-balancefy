package com.balancefy.balancefyapp.frames

import android.content.SharedPreferences
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import com.balancefy.balancefyapp.R
import com.balancefy.balancefyapp.adapter.TopicPostsProfileAdapter
import com.balancefy.balancefyapp.databinding.FragmentForumGeralBinding
import com.balancefy.balancefyapp.models.response.ListaFeedTopicoResponse
import com.balancefy.balancefyapp.rest.Rest
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class ForumGeralFragment : Fragment() {
    private lateinit var binding: FragmentForumGeralBinding
    private lateinit var preferences : SharedPreferences
    private lateinit var token : String

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentForumGeralBinding.inflate(layoutInflater)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {

        preferences = requireContext().getSharedPreferences("Auth", AppCompatActivity.MODE_PRIVATE)

        token = preferences.getString("token", null)!!

        recyclerViewConfiguration()
        super.onViewCreated(view, savedInstanceState)
    }

    private fun recyclerViewConfiguration() {
        Rest.getForumInstance().getListTopic("Bearer $token")
            .enqueue(object : Callback<ListaFeedTopicoResponse> {
                override fun onResponse(
                    call: Call<ListaFeedTopicoResponse>,
                    response: Response<ListaFeedTopicoResponse>
                ) {
                    val data = response.body()?.listTopic
                    println(token)
                    println(data)
                    when (response.code()) {
                        200 -> {
                            if (data.isNullOrEmpty()) {
                                binding.emptyListOfTopics.text = getString(R.string.no_posts)
                            } else {
                                binding.emptyListOfTopics.text = ""
                            }

                            val recyclerContainer = binding.recyclerContainer

                            recyclerContainer.layoutManager = LinearLayoutManager(context)

                            recyclerContainer.adapter = TopicPostsProfileAdapter(
                                data,
                            ) { mensagem ->
                                showMessageTest(mensagem)
                            }
                        }

                        else -> {
                            println(response.code())
                            Toast.makeText(context, getString(R.string.connection_error), Toast.LENGTH_SHORT)
                                .show()
                        }
                    }
                }
                override fun onFailure(call: Call<ListaFeedTopicoResponse>, t: Throwable) {
                    Toast.makeText(context, t.message, Toast.LENGTH_SHORT).show()
                }
            })
    }

    private fun showMessageTest(message : String) {
        Toast.makeText(context, message, Toast.LENGTH_SHORT).show()
    }
}