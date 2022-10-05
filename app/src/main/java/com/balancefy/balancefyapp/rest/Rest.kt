package com.balancefy.balancefyapp.rest

import com.balancefy.balancefyapp.services.Auth
import com.balancefy.balancefyapp.services.Register
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

object Rest {
    private val baseURL = "http://54.164.8.226:8080"

    fun getInstance(): Retrofit {
        return Retrofit
            .Builder().baseUrl(this.baseURL)
            .addConverterFactory(
                GsonConverterFactory.create()
            ).build()
    }

    fun getAuthInstance() = getInstance().create(Auth::class.java)

    fun getRegisterInstance() = getInstance().create(Register::class.java)
}