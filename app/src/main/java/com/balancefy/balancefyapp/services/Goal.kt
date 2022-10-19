package com.balancefy.balancefyapp.services

import com.balancefy.balancefyapp.models.response.GoalsResponse
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Header

interface Goal {
    @GET("/accounts/goals")
    fun listGoals(@Header("Authorization") token: String): Call<List<GoalsResponse>>
}