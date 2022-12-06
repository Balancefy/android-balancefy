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
import com.balancefy.balancefyapp.databinding.FragmentProfileAlternativeBinding
import com.balancefy.balancefyapp.models.response.FeedTopicoResponseDto
import com.balancefy.balancefyapp.models.response.ListaFeedTopicoResponse
import com.balancefy.balancefyapp.rest.Rest
import com.squareup.picasso.Picasso
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class ProfileAlternativeFragment : Fragment() {
    private lateinit var binding: FragmentProfileAlternativeBinding
    private lateinit var preferences: SharedPreferences
    private lateinit var token: String
    private var altAccountId: Int = 0
    private lateinit var altAccountName: String
    private lateinit var altAccountAvatar: String
    private lateinit var altAccountBanner: String

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentProfileAlternativeBinding.inflate(inflater)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {

        preferences = requireContext().getSharedPreferences("Auth", AppCompatActivity.MODE_PRIVATE)

        token = preferences.getString("token", "")!!

        altAccountId = preferences.getInt("altAccountId", 0)
        altAccountName = preferences.getString("altAccountName", "Ze Ninguem")!!
        altAccountAvatar = preferences.getString("altAccountAvatar", "")!!
        altAccountBanner = preferences.getString("altAccountBanner", "")!!

        if (altAccountAvatar != "") {
            Picasso.get().load(altAccountAvatar).into(binding.avatarProfile)
        }else{
            binding.avatarProfile.setImageResource(R.drawable.ic_account_white)
        }

        if (altAccountBanner != "") {
            Picasso.get().load(altAccountAvatar).into(binding.backgroundProfile)
        }else{
            binding.backgroundProfile.setImageResource(R.drawable.fundo_perfil)
        }

        binding.nameProfile.text = altAccountName
        recyclerViewConfiguration()

    }

    private fun recyclerViewConfiguration() {
        Rest.getForumInstance().getListTopicById(
            "Bearer $token",
            altAccountId
        )
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
                    binding.emptyListOfTopics.text = getString(R.string.no_posts)
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