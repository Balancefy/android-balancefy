package com.balancefy.balancefyapp.services

import com.balancefy.balancefyapp.models.request.PostRequest
import com.balancefy.balancefyapp.models.response.ListaFeedTopicoResponse
import retrofit2.Call
import retrofit2.http.*

interface Forum {

    @GET("/forum")
    fun getListTopic(@Header("Authorization") token: String): Call<ListaFeedTopicoResponse>

    @POST("/forum")
    fun create(@Header("Authorization") token: String, @Body body: PostRequest): Call<Unit>

    @GET("/forum/accounts/{accountId}")
    fun getListTopicById(@Header("Authorization") token: String, @Path("accountId") accountId:Int): Call<ListaFeedTopicoResponse>
}