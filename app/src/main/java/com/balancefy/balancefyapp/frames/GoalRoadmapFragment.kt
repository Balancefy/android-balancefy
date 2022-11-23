package com.balancefy.balancefyapp.frames

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.recyclerview.widget.LinearLayoutManager
import com.balancefy.balancefyapp.R
import com.balancefy.balancefyapp.adapter.GoalCardsAdapter
import com.balancefy.balancefyapp.adapter.RoadmapCardsAdapter
import com.balancefy.balancefyapp.components.RoadmapCard
import com.balancefy.balancefyapp.databinding.FragmentGoalRoadmapBinding
import com.balancefy.balancefyapp.models.response.GoalsDetailsResponse
import com.balancefy.balancefyapp.models.response.GoalsResponse
import com.balancefy.balancefyapp.models.response.TaskResponse
import com.balancefy.balancefyapp.rest.Rest
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class GoalRoadmapFragment : Fragment() {
    private lateinit var binding: FragmentGoalRoadmapBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentGoalRoadmapBinding.inflate(layoutInflater)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        Rest.getGoalInstance().findById("Bearer teste", id).enqueue(object :
            Callback<GoalsDetailsResponse> {
            override fun onResponse(
                call: Call<GoalsDetailsResponse>,
                response: Response<GoalsDetailsResponse>
            ) {
                val data = response.body()

                when(response.code()){
                    200 -> {
                        configRecyclerView(data?.tasks ?: emptyList())
                    }
                }
            }

            override fun onFailure(call: Call<GoalsDetailsResponse>, t: Throwable) {
                Toast.makeText(context, getString(R.string.connection_error), Toast.LENGTH_SHORT).show()
            }
        })
        configRecyclerView(emptyList())
    }

    private fun configRecyclerView(tasks: List<TaskResponse>) {
        val recyclerContainer = binding.recyclerContainer
        recyclerContainer.layoutManager = LinearLayoutManager(context)

        recyclerContainer.adapter = RoadmapCardsAdapter(
            tasks
        )
    }
}