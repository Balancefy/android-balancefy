package com.balancefy.balancefyapp.services

import okhttp3.MultipartBody
import retrofit2.Call
import retrofit2.http.*

interface Upload {

    @Multipart
    @PUT("/users/upload/avatar")
    fun uploadAvatar(@Header("Authorization") token: String, @Part filePart: MultipartBody.Part): Call<Unit>

}