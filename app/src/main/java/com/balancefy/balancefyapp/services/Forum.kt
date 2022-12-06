package com.balancefy.balancefyapp.services

import com.balancefy.balancefyapp.models.request.CommentReplyRequest
import com.balancefy.balancefyapp.models.request.PostRequest
import com.balancefy.balancefyapp.models.response.ListaFeedCommentReplyResponse
import com.balancefy.balancefyapp.models.response.ListaFeedTopicoResponse
import com.balancefy.balancefyapp.models.response.TopicoResponseDto
import retrofit2.Call
import retrofit2.http.*

interface Forum {

    @GET("/forum")
    fun getListTopic(@Header("Authorization") token: String): Call<ListaFeedTopicoResponse>

    @POST("/forum")
    fun create(@Header("Authorization") token: String, @Body body: PostRequest): Call<Unit>

    @GET("/forum/accounts/{accountId}")
    fun getListTopicById(@Header("Authorization") token: String, @Path("accountId") accountId:Int): Call<ListaFeedTopicoResponse>

    @GET("/forum/mostLike")
    fun getMostLike(@Header("Authorization") token: String): Call<ListaFeedTopicoResponse>

    @PATCH("/forum/like/{topicId}")
    fun addLike(@Header("Authorization") token: String, @Path("topicId") topicId:Int): Call<TopicoResponseDto>

    @PATCH("/forum/unlike/{topicId}")
    fun unlike(@Header("Authorization") token: String, @Path("topicId") topicId:Int): Call<TopicoResponseDto>

    @GET("/comentario/{idTopico}")
    fun getComments(@Header("Authorization") token: String, @Path("idTopico") topicId:Int): Call<ListaFeedCommentReplyResponse>

    @POST("/comentario")
    fun createComment(@Header("Authorization") token: String, @Body body: CommentReplyRequest): Call<Unit>
}