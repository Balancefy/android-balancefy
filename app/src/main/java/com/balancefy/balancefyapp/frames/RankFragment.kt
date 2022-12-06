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
import com.balancefy.balancefyapp.adapter.RankAccountAdapter
import com.balancefy.balancefyapp.databinding.FragmentRankBinding
import com.balancefy.balancefyapp.models.response.AccountRankResponseDto
import com.balancefy.balancefyapp.models.response.AccountResponseDto
import com.balancefy.balancefyapp.models.response.RankResponse
import com.balancefy.balancefyapp.rest.Rest
import com.squareup.picasso.Picasso
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class RankFragment : Fragment() {
    private lateinit var binding: FragmentRankBinding
    private lateinit var preferences: SharedPreferences
    private var accountId: Int = 0
    private lateinit var token: String

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentRankBinding.inflate(layoutInflater)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        preferences = requireContext().getSharedPreferences("Auth", AppCompatActivity.MODE_PRIVATE)

        token = preferences.getString("token", "")!!

        accountId = preferences.getInt("accountId", 0)

        recyclerViewConfiguration()
    }

    private fun filterUserRankData(listrank: List<AccountRankResponseDto>): Pair<AccountRankResponseDto, Int> {
        val filterRank = listrank.firstOrNull { it.id == accountId }
        return Pair(filterRank!!, listrank.indexOfFirst{it.id == accountId})
    }

    private fun recyclerViewConfiguration() {
        Rest.getAccountInstance().getRank(
            "Bearer $token"
        ).enqueue(object : Callback<RankResponse> {

            override fun onResponse(call: Call<RankResponse>, response: Response<RankResponse>) {
                val data = response.body()!!.rank

                when (response.code()) {
                    200 -> {
                        configRecycleView(data)
                        setUserRank(filterUserRankData(data))
                        Picasso.get().load(data[0].avatar).into(binding.ivFirstUserImg)
                        binding.tvFirstUserName.text = data[0].name
                        Picasso.get().load(data[1].avatar).into(binding.ivSecondUserImg)
                        binding.tvSecondUserName.text = data[1].name
                        Picasso.get().load(data[2].avatar).into(binding.ivThirdUserImg)
                        binding.tvThirdUser.text = data[2].name
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

            override fun onFailure(call: Call<RankResponse>, t: Throwable) {
                Toast.makeText(context, t.message, Toast.LENGTH_SHORT).show()
            }
        })
    }

    private fun showMessageTest(message: String) {
        Toast.makeText(context, message, Toast.LENGTH_SHORT).show()
    }

    private fun configRecycleView(posts: List<AccountRankResponseDto>) {

        val recyclerContainer = binding.recyclerViewRankList

        recyclerContainer.layoutManager = LinearLayoutManager(context)

        recyclerContainer.adapter = RankAccountAdapter(
            posts,
            token
        ) { mensagem -> showMessageTest(mensagem) }
    }

    private fun setUserRank(accountRank: Pair<AccountRankResponseDto, Int>) {
        preferences.getString("avatar", "")?.let { binding.accountUser.setImg(it) }
        binding.accountUser.setPosition((accountRank.second + 1).toString())
        binding.accountUser.setName(resources.getString(R.string.you_position))
        binding.accountUser.setProgress(accountRank.first.progress.toString())
        binding.accountUser.setGoal(accountRank.first.goal.toString())
    }
}