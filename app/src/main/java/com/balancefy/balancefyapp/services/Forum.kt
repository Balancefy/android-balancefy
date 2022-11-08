package com.balancefy.balancefyapp.services

import com.balancefy.balancefyapp.models.response.ListaFeedTopicoResponse
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Header

interface Forum {

    @GET("/forum")
    fun getListTopicById(@Header("Authorization") token: String): Call<ListaFeedTopicoResponse>
}