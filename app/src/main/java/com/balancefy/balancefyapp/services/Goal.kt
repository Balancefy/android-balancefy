package com.balancefy.balancefyapp.services

import com.balancefy.balancefyapp.models.request.CreateGoal
import com.balancefy.balancefyapp.models.response.GoalsDetailsResponse
import com.balancefy.balancefyapp.models.response.GoalsResponse
import retrofit2.Call
import retrofit2.http.*
import java.util.*

interface Goal {
    @GET("/accounts/goals")
    fun listGoals(@Header("Authorization") token: String): Call<List<GoalsResponse>>

    @GET("/accounts/goals/done")
    fun listDoneGoals(@Header("Authorization") token: String): Call<List<GoalsResponse>>

    @POST("/accounts/goals")
    fun createGoal(@Header("Authorization") token: String, @Body body: CreateGoal): Call<Objects>

    @GET("/accounts/goals/{id}")
    fun findById(@Header("Authorization") token: String, @Path("id") id: Int): Call<GoalsDetailsResponse>
}