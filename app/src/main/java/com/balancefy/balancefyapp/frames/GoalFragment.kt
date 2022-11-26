package com.balancefy.balancefyapp.frames

import android.content.SharedPreferences
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.app.AppCompatActivity
import androidx.core.os.bundleOf
import androidx.fragment.app.Fragment
import androidx.fragment.app.setFragmentResult
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout
import com.balancefy.balancefyapp.R
import com.balancefy.balancefyapp.adapter.GoalCardsAdapter
import com.balancefy.balancefyapp.adapter.TransactionCardsAdapter
import com.balancefy.balancefyapp.databinding.FragmentGoalBinding
import com.balancefy.balancefyapp.models.response.GoalsResponse
import com.balancefy.balancefyapp.models.response.TransactionResponse
import com.balancefy.balancefyapp.rest.Rest
import com.balancefy.balancefyapp.services.Goal
import com.google.android.material.tabs.TabLayout
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class GoalFragment : Fragment() {

    private lateinit var binding: FragmentGoalBinding
    lateinit var preferences : SharedPreferences
    private lateinit var swipeContainer: SwipeRefreshLayout

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentGoalBinding.inflate(layoutInflater)

        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        preferences = context?.getSharedPreferences("Auth", AppCompatActivity.MODE_PRIVATE)!!
        swipeContainer = binding.swipe
        findGoals()

        var actualTab: Int = 0

        binding.tabLayout.addOnTabSelectedListener(object : TabLayout.OnTabSelectedListener {
            override fun onTabSelected(tab: TabLayout.Tab?) {
                when(tab?.position) {
                    0 -> {
                        actualTab = 0
                        findGoals()
                    }
                    1 -> {
                        actualTab = 1
                        findGoals("DONE")
                    }
                }
            }

            override fun onTabReselected(tab: TabLayout.Tab?) {
                // Handle tab reselect
            }

            override fun onTabUnselected(tab: TabLayout.Tab?) {
                // Handle tab unselect
            }
        })

        swipeContainer.setOnRefreshListener {
            when(actualTab) {
                0 -> {
                    findGoals()
                }
                1 -> {
                    findGoals("DONE")
                }
            }
            swipeContainer.isRefreshing = false
        }
    }

    private fun findGoals(status: String = "ACTIVE") {
        val token = preferences.getString("token", null)

        when (status) {
            "ACTIVE" -> Rest.getGoalInstance().listGoals("Bearer ${token!!}").enqueue(object : Callback<List<GoalsResponse>> {
                override fun onResponse(
                    call: Call<List<GoalsResponse>>,
                    response: Response<List<GoalsResponse>>
                ) {
                    val data = response.body()
                    when(response.code()){
                        200 -> {
                            configRecyclerView(data ?: emptyList())
                        }
                        else -> configRecyclerView(emptyList())
                    }
                }

                override fun onFailure(call: Call<List<GoalsResponse>>, t: Throwable) {
                    binding.tvError.text = context?.getString(R.string.connection_error)
                }
            })
            "DONE" -> Rest.getGoalInstance().listDoneGoals("Bearer ${token!!}").enqueue(object : Callback<List<GoalsResponse>> {
                override fun onResponse(
                    call: Call<List<GoalsResponse>>,
                    response: Response<List<GoalsResponse>>
                ) {
                    val data = response.body()

                    when(response.code()){
                        200 -> {
                            configRecyclerView(data ?: emptyList())
                        }
                        else -> configRecyclerView(emptyList())
                    }
                }
                override fun onFailure(call: Call<List<GoalsResponse>>, t: Throwable) {
                    binding.tvError.text = context?.getString(R.string.connection_error)
                }
            })
            else -> {
                configRecyclerView(emptyList())
            }
        }
    }

    private fun configRecyclerView(goals: List<GoalsResponse>) {
        if(goals.isEmpty()) {
            binding.tvError.visibility = View.VISIBLE

            binding.recyclerContainer.visibility = View.GONE
        } else {
            binding.tvError.visibility = View.GONE
            binding.recyclerContainer.visibility = View.VISIBLE

            val recyclerContainer = binding.recyclerContainer
            recyclerContainer.layoutManager = LinearLayoutManager(context)

            recyclerContainer.adapter = GoalCardsAdapter(
                goals
            ) { id -> getGoalDetails(id) }
        }
    }

    private fun configRecyclerViewRefresh(transaction: List<TransactionResponse>) {
        if(transaction.isEmpty()) {
            binding.tvError.visibility = View.VISIBLE
        } else {
            binding.tvError.visibility = View.GONE

            val recyclerContainer = binding.recyclerContainer

            (recyclerContainer.adapter as TransactionCardsAdapter).notifyDataSetChanged()
        }
    }


    private fun getGoalDetails(id : Int) {
        val editor = preferences.edit()
        editor.putInt("goalId", id)
        editor.apply()
        findNavController().navigate(R.id.fromGoalToGoalDetails)
    }
}