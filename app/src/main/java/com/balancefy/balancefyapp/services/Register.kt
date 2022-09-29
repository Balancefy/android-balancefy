package com.balancefy.balancefyapp.services

import com.balancefy.balancefyapp.models.request.RegisterRequestDto
import com.balancefy.balancefyapp.models.response.RegisterResponseDto
import retrofit2.Call
import retrofit2.http.Body
import retrofit2.http.POST

interface Register {

    @POST("/accounts")
    fun register(@Body body: RegisterRequestDto): Call<RegisterResponseDto>
}