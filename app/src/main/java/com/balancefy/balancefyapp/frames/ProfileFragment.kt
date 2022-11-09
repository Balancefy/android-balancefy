package com.balancefy.balancefyapp.frames

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import com.balancefy.balancefyapp.R
import com.balancefy.balancefyapp.adapter.TopicPostsProfileAdapter
import com.balancefy.balancefyapp.databinding.FragmentProfileBinding
import com.balancefy.balancefyapp.models.response.*
import com.balancefy.balancefyapp.rest.Rest
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import com.balancefy.balancefyapp.models.request.TopicoRequestDto
import com.google.android.material.snackbar.Snackbar

class ProfileFragment : Fragment() {
    private lateinit var binding: FragmentProfileBinding

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentProfileBinding.inflate(inflater)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        //TODO atualizar background e profile image do usuario
        binding.nameProfile.text = arguments?.getString("nameUser") ?: "Ze ninguem"
        recyclerViewConfiguration()
    }

    private fun recyclerViewConfiguration() {

        Rest.getListFeedTopic().getListTopicById("Bearer ${arguments?.getString("token")}")
            .enqueue(object : Callback<ListaFeedTopicoResponse> {
                override fun onResponse(
                    call: Call<ListaFeedTopicoResponse>,
                    response: Response<ListaFeedTopicoResponse>
                ) {
                    val data = response.body()?.listTopic

                    when (response.code()) {
                        200 -> {
                            if (data == null) {
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

                        else -> Toast.makeText(context, getString(R.string.connection_error), Toast.LENGTH_SHORT)
                            .show()
                    }
                }

                override fun onFailure(call: Call<ListaFeedTopicoResponse>, t: Throwable) {
                    Toast.makeText(context, t.message, Toast.LENGTH_SHORT).show()
                    binding.emptyListOfTopics.text =  getString(R.string.no_posts)
                }
            })

    }

    private fun showMessageTest(message : String) {
        Snackbar.make(binding.root, message, Snackbar.LENGTH_SHORT).show()
    }
}