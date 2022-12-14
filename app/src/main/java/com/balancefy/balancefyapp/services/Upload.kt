package com.balancefy.balancefyapp.services

import com.balancefy.balancefyapp.models.response.UploadResponse
import okhttp3.MultipartBody
import retrofit2.Call
import retrofit2.http.*

interface Upload {

    @Multipart
    @PUT("/api/users/upload/avatar")
    fun uploadAvatar(@Header("Authorization") token: String, @Part filePart: MultipartBody.Part): Call<UploadResponse>

}