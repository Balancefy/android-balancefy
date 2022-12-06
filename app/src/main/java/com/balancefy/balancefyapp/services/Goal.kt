package com.balancefy.balancefyapp.services

import com.balancefy.balancefyapp.models.request.CreateGoal
import com.balancefy.balancefyapp.models.response.GoalsDetailsResponse
import com.balancefy.balancefyapp.models.response.GoalsResponse
import com.balancefy.balancefyapp.models.response.TaskGoalKey
import com.balancefy.balancefyapp.models.response.Teste
import retrofit2.Call
import retrofit2.http.*
import java.util.*

interface Goal {
    @GET("/api/accounts/goals")
    fun listGoals(@Header("Authorization") token: String): Call<List<GoalsResponse>>

    @GET("/api/accounts/goals/done")
    fun listDoneGoals(@Header("Authorization") token: String): Call<List<GoalsResponse>>

    @POST("/api/accounts/goals")
    fun createGoal(@Header("Authorization") token: String, @Body body: CreateGoal): Call<Unit>

    @GET("/api/accounts/goals/{id}")
    fun findById(@Header("Authorization") token: String, @Path("id") id: Int): Call<GoalsDetailsResponse>

    @PATCH("/api/accounts/goals/tasks/")
    fun completeTask(@Header("Authorization") token: String, @Body body: TaskGoalKey): Call<Unit>
}