package com.balancefy.balancefyapp.services

import com.balancefy.balancefyapp.models.response.RankResponse
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Header

interface Rank {
    @GET("/rank")
    fun getRank(@Header("Authorization") token: String): Call<RankResponse>
}