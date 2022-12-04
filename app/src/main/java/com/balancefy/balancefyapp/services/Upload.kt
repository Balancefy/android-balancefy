package com.balancefy.balancefyapp.services

import okhttp3.MultipartBody
import retrofit2.Call
import retrofit2.http.Header
import retrofit2.http.Multipart
import retrofit2.http.POST
import retrofit2.http.Part

interface Upload {

    @Multipart
    @POST("/users/upload/avatar")
    fun uploadAvatar(@Header("Authorization") token: String, @Part filePart: MultipartBody.Part): Call<Unit>

}