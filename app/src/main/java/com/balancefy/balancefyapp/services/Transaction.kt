package com.balancefy.balancefyapp.services

import com.balancefy.balancefyapp.models.request.RepeatedTransactionRequest
import com.balancefy.balancefyapp.models.request.TransactionRequest
import com.balancefy.balancefyapp.models.response.TransactionResponse
import retrofit2.Call
import retrofit2.http.*
import java.util.*

interface Transaction {
    @POST("/transactions")
    fun create(@Header("Authorization") token: String, @Body body: TransactionRequest): Call<Objects>

    @GET("/transactions/goal/{id}")
    fun getTransactionByGoal(@Header("Authorization") token: String,  @Path("id") id: Int): Call<List<TransactionResponse>>
}