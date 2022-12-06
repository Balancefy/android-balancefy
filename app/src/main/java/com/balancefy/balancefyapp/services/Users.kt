package com.balancefy.balancefyapp.services

import com.balancefy.balancefyapp.models.request.UserEdit
import com.balancefy.balancefyapp.models.response.UserResponseDto
import retrofit2.Call
import retrofit2.http.Body
import retrofit2.http.Header
import retrofit2.http.PUT

interface Users {

    @PUT("/api/users")
    fun editProfile(@Header("Authorization") token: String, @Body body: UserEdit): Call<UserEdit>

}