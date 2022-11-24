package com.balancefy.balancefyapp.services

import com.balancefy.balancefyapp.models.request.RepeatedTransactionRequest
import retrofit2.Call
import retrofit2.http.Body
import retrofit2.http.Header
import retrofit2.http.POST
import java.util.*

interface RepeatedTransaction {
    @POST("/transactionFixed")
    fun create(@Header("Authorization") token: String, @Body body: RepeatedTransactionRequest): Call<Unit>
}