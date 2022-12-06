package com.balancefy.balancefyapp.rest

import com.balancefy.balancefyapp.services.*
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

object Rest {
    // private val baseURL = "http://balancefy.ddns.net:8080/"
    // Mudar todos os endpoints para /api/...
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

    fun getBalanceInstance() = getInstance().create(Balance::class.java)

    fun getForumInstance() = getInstance().create(Forum::class.java)

    fun getRepeatedTransactionInstance() = getInstance().create(RepeatedTransaction::class.java)

    fun getTransactionInstance() = getInstance().create(Transaction::class.java)

    fun getTipsInstance() = getInstance().create(Tip::class.java)

    fun getUploadInstance() = getInstance().create(Upload::class.java)

    fun getUserInstance() = getInstance().create(Users::class.java)

    fun getAccountInstance() = getInstance().create(Accounts::class.java)

}