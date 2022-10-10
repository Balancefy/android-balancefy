package com.balancefy.balancefyapp.services

import com.balancefy.balancefyapp.models.request.RegisterRequestDto
import retrofit2.Call
import retrofit2.http.Body
import retrofit2.http.POST
import java.util.*

interface Register {

    @POST("/accounts")
    fun register(@Body body: RegisterRequestDto): Call<Objects>
}