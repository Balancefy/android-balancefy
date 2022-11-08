package com.balancefy.balancefyapp.rest

import com.balancefy.balancefyapp.services.Auth
import com.balancefy.balancefyapp.services.Forum
import com.balancefy.balancefyapp.services.Goal
import com.balancefy.balancefyapp.services.Register
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

object Rest {
    private val baseURL = "https://api-balancefy.ddns.net/"

    fun getInstance(): Retrofit {
        return Retrofit
            .Builder().baseUrl(this.baseURL)
            .addConverterFactory(
                GsonConverterFactory.create()
            ).build()
    }

    fun getAuthInstance() = getInstance().create(Auth::class.java)

    fun getRegisterInstance() = getInstance().create(Register::class.java)

    fun getGoalInstance() = getInstance().create(Goal::class.java)

    fun getListFeedTopic() = getInstance().create(Forum::class.java)
}