package com.balancefy.balancefyapp.services

import com.balancefy.balancefyapp.models.request.RepeatedTransactionRequest
import com.balancefy.balancefyapp.models.request.TransactionRequest
import retrofit2.Call
import retrofit2.http.Body
import retrofit2.http.Header
import retrofit2.http.POST
import java.util.*

interface Transaction {
    @POST("/transactions")
    fun create(@Header("Authorization") token: String, @Body body: TransactionRequest): Call<Objects>
}