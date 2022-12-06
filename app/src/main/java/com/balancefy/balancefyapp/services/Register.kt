package com.balancefy.balancefyapp.services

import com.balancefy.balancefyapp.models.request.RegisterRequest
import retrofit2.Call
import retrofit2.http.Body
import retrofit2.http.POST
import java.util.*

interface Register {

    @POST("/api/accounts")
    fun register(@Body body: RegisterRequest): Call<Unit>
}