package com.balancefy.balancefyapp.services

import com.balancefy.balancefyapp.models.response.AccountResponseDto
import com.balancefy.balancefyapp.models.response.RankResponse
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Header
import retrofit2.http.Path

interface Accounts {

    @GET("/api/accounts/{id}")
    fun getAccountById(@Header("Authorization") token: String, @Path("id") id : Int): Call<AccountResponseDto>

    @GET("/api/accounts/rank")
    fun getRank(@Header("Authorization") token: String): Call<RankResponse>

}