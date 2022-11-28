package com.balancefy.balancefyapp.services

import com.balancefy.balancefyapp.models.response.BalanceResponse
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Header

interface Balance {
    @GET("/accounts/balance")
    fun getBalance(@Header("Authorization") token: String): Call<BalanceResponse>
}