package com.balancefy.balancefyapp.services

import com.balancefy.balancefyapp.models.request.RepeatedTransactionRequest
import com.balancefy.balancefyapp.models.response.TransactionResponse
import retrofit2.Call
import retrofit2.http.*

interface RepeatedTransaction {

    @GET("/transactionFixed/{id}")
    fun getList(@Header("Authorization") token: String, @Path("id") id: Int): Call<List<TransactionResponse>>

    @POST("/transactionFixed")
    fun create(@Header("Authorization") token: String, @Body body: RepeatedTransactionRequest): Call<Unit>
}