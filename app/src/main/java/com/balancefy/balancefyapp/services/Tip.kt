package com.balancefy.balancefyapp.services

import com.balancefy.balancefyapp.models.response.TipsResponse
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Header

interface Tip {
    @GET("/dicas")
    fun getAllTips(@Header("Authorization") token : String ): Call<List<TipsResponse>>
}