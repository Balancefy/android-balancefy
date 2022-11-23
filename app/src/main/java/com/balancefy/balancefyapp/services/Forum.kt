package com.balancefy.balancefyapp.services

import com.balancefy.balancefyapp.models.request.PostRequest
import com.balancefy.balancefyapp.models.response.ListaFeedTopicoResponse
import retrofit2.Call
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.Header
import retrofit2.http.POST
import java.util.*
import retrofit2.http.Path

interface Forum {

    @GET("/forum")
    fun getListTopic(@Header("Authorization") token: String): Call<ListaFeedTopicoResponse>

    @POST("/forum")
    fun create(@Header("Authorization") token: String, @Body body: PostRequest): Call<Objects>

    @GET("/forum/accounts/{accountId}")
    fun getListTopicById(@Header("Authorization") token: String, @Path("accountId") accountId:Int): Call<ListaFeedTopicoResponse>
}