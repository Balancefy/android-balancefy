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
import com.balancefy.balancefyapp.R
import com.balancefy.balancefyapp.adapter.GoalCardsAdapter
import com.balancefy.balancefyapp.databinding.FragmentGoalBinding
import com.balancefy.balancefyapp.models.response.GoalsResponse
import com.balancefy.balancefyapp.rest.Rest
import com.google.android.material.tabs.TabLayout
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class GoalFragment : Fragment() {

    private lateinit var binding: FragmentGoalBinding
    lateinit var preferences : SharedPreferences

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentGoalBinding.inflate(layoutInflater)

        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        preferences = context?.getSharedPreferences("Auth", AppCompatActivity.MODE_PRIVATE)!!
        findGoals()

        binding.tabLayout.addOnTabSelectedListener(object : TabLayout.OnTabSelectedListener {
            override fun onTabSelected(tab: TabLayout.Tab?) {
                when(tab?.position) {
                    0 -> {
                        findGoals()
                    }
                    1 -> {
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
        val recyclerContainer = binding.recyclerContainer
        recyclerContainer.layoutManager = LinearLayoutManager(context)

        if(goals.isEmpty()) {
            binding.tvError.text = context?.getString(R.string.no_goal)
        } else {
            binding.tvError.text = ""
        }

        recyclerContainer.adapter = GoalCardsAdapter(
            goals
        ) { id -> getGoalDetails(id) }
    }

    private fun getGoalDetails(id : Int) {
        val bundle = bundleOf(
            "goalId" to id
        )
        setFragmentResult("requestKey", bundle)
        findNavController().navigate(R.id.fromGoalToGoalDetails)
    }
}