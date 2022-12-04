package com.balancefy.balancefyapp.services

import com.balancefy.balancefyapp.models.request.PostRequest
import com.balancefy.balancefyapp.models.response.ListaFeedTopicoResponse
import com.balancefy.balancefyapp.models.response.TopicoResponseDto
import retrofit2.Call
import retrofit2.http.*

interface Forum {

    @GET("/api/forum")
    fun getListTopic(@Header("Authorization") token: String): Call<ListaFeedTopicoResponse>

    @POST("/api/forum")
    fun create(@Header("Authorization") token: String, @Body body: PostRequest): Call<Unit>

    @GET("/api/forum/accounts/{accountId}")
    fun getListTopicById(@Header("Authorization") token: String, @Path("accountId") accountId:Int): Call<ListaFeedTopicoResponse>

    @GET("/api/forum/mostLike")
    fun getMostLike(@Header("Authorization") token: String): Call<ListaFeedTopicoResponse>

    @PATCH("/api/forum/like/{topicId}")
    fun addLike(@Header("Authorization") token: String, @Path("topicId") topicId:Int): Call<TopicoResponseDto>

    @PATCH("/api/forum/unlike/{topicId}")
    fun unlike(@Header("Authorization") token: String, @Path("topicId") topicId:Int): Call<TopicoResponseDto>
}