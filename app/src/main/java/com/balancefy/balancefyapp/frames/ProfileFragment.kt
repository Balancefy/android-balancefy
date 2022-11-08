package com.balancefy.balancefyapp.frames

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import com.balancefy.balancefyapp.R
import com.balancefy.balancefyapp.adapter.TopicPostsProfileAdapter
import com.balancefy.balancefyapp.databinding.FragmentProfileBinding
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
        recyclerViewConfiguration()
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        binding.nameProfile.text = arguments?.getString("nameUser") ?: "Ze ninguem"
    }

    private fun recyclerViewConfiguration() {
        val testPost = listOf(
            TopicoRequestDto(
                1,
                "Economizar Dinheiro",
                "Como vocës fazem para não gastar o dinheiro assim que ele cai na conta?\n" +
                    "Preciso economizar dinheiro, mas tenho muita dificuldade em lidar com gastos."),
            TopicoRequestDto(
                2,
                "Economizar Dinheiro",
                "Como vocës fazem para não gastar o dinheiro assim que ele cai na conta?\n" +
                        "Preciso economizar dinheiro, mas tenho muita dificuldade em lidar com gastos."),
            TopicoRequestDto(
                3,
                "Economizar Dinheiro",
                "Como vocës fazem para não gastar o dinheiro assim que ele cai na conta?\n" +
                        "Preciso economizar dinheiro, mas tenho muita dificuldade em lidar com gastos.")
        )

        val recyclerContainer = binding.recyclerContainer
        recyclerContainer.layoutManager = LinearLayoutManager(context)

        recyclerContainer.adapter = TopicPostsProfileAdapter(
            testPost
        ) { message ->
            showMessageTest(message)
        }
    }

    private fun showMessageTest(message : String) {
        Snackbar.make(binding.root, message, Snackbar.LENGTH_SHORT).show()
    }
}