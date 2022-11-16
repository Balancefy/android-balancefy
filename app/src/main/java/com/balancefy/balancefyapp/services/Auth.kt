package com.balancefy.balancefyapp.services

import com.balancefy.balancefyapp.models.request.LoginRequest
import com.balancefy.balancefyapp.models.response.LoginResponseDto
import retrofit2.Call
import retrofit2.http.Body
import retrofit2.http.POST

interface Auth {

    @POST("/auth")
    fun login(@Body body: LoginRequest): Call<LoginResponseDto>
}