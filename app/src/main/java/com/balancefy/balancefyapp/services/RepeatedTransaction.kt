package com.balancefy.balancefyapp.services

import com.balancefy.balancefyapp.models.request.RepeatedTransactionRequest
import com.balancefy.balancefyapp.models.response.GoalsResponse
import com.balancefy.balancefyapp.models.response.RepeatedTransactionResponse
import retrofit2.Call
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.Header
import retrofit2.http.POST
import retrofit2.http.Path
import java.util.*

interface RepeatedTransaction {

    @GET("/transactionFixed/{id}")
    fun getList(@Header("Authorization") token: String, @Path("id") id: Int): Call<List<RepeatedTransactionResponse>>

    @POST("/transactionFixed")
    fun create(@Header("Authorization") token: String, @Body body: RepeatedTransactionRequest): Call<Unit>
}