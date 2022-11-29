package com.balancefy.balancefyapp.activities

import android.content.Intent
import android.content.SharedPreferences
import android.os.Bundle
import android.view.WindowManager
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.balancefy.balancefyapp.R
import com.balancefy.balancefyapp.databinding.ActivityIntroBinding
import com.balancefy.balancefyapp.databinding.EmailBottomSheetBinding
import com.balancefy.balancefyapp.databinding.LoginBottomSheetBinding
import com.balancefy.balancefyapp.models.request.LoginRequestDto
import com.balancefy.balancefyapp.models.response.LoginResponseDto
import com.balancefy.balancefyapp.models.response.UserResponseDto
import com.balancefy.balancefyapp.rest.Rest
import com.google.android.material.bottomsheet.BottomSheetBehavior.STATE_EXPANDED
import com.google.android.material.bottomsheet.BottomSheetDialog
import com.google.android.material.snackbar.Snackbar
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response


class IntroActivity : AppCompatActivity() {

    private lateinit var binding: ActivityIntroBinding
    private lateinit var sheetEmailBinding: EmailBottomSheetBinding
    lateinit var preferences : SharedPreferences

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityIntroBinding.inflate(layoutInflater)

        preferences = getSharedPreferences("Auth", MODE_PRIVATE)

        setContentView(binding.root)

        val firstLogin = preferences.getBoolean("firstLogin", true)

        validateFirstLogin(firstLogin)

        val lastUser = preferences.getString("nameUser", null)

        if (lastUser != null) {
            startActivity(Intent(baseContext, MainActivity::class.java))
        }

        binding.btnHasAcc.setOnClickListener {
            showBottomSheetDialog()
        }

        binding.btnCreateAcc.setOnClickListener {
            changeScreen()
        }
    }

    private fun validateFirstLogin(firstLogin: Boolean) {
        if (firstLogin) {
            startActivity(Intent(baseContext, OnboardingActivity::class.java))
        }
    }

    private fun showBottomSheetDialog() {
        val dialog = BottomSheetDialog(this, R.style.BottomSheetDialog)

        val sheetLoginBinding: LoginBottomSheetBinding =
            LoginBottomSheetBinding.inflate(layoutInflater, null, false)

        dialog.setContentView(sheetLoginBinding.root)
        dialog.show()

        sheetLoginBinding.btnLoginWithEmail.setOnClickListener {
            showEmailSheetDialog()
        }
    }

    private fun showEmailSheetDialog() {

        val dialog = BottomSheetDialog(this, R.style.BottomSheetDialog).apply {
            window?.setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_ADJUST_RESIZE)
        }

        dialog.behavior.skipCollapsed = true
        dialog.behavior.state = STATE_EXPANDED

        sheetEmailBinding = EmailBottomSheetBinding.inflate(layoutInflater, null, false)

        dialog.setContentView(sheetEmailBinding.root)
        dialog.show()

        sheetEmailBinding.btnLogIn.setOnClickListener {
            authentication()
        }
    }

    private fun authentication() {
        val email = sheetEmailBinding.etLoginEmail.text.toString()
        val password = sheetEmailBinding.etLoginPassword.text.toString()

        val body = LoginRequestDto(email, password)

        Rest.getAuthInstance().login(body).enqueue(object : Callback<LoginResponseDto> {
            override fun onResponse(
                call: Call<LoginResponseDto>,
                response: Response<LoginResponseDto>
            ) {
                val data = response.body()
                when(response.code()){
                    400 -> Snackbar.make(sheetEmailBinding.root, R.string.invalid_login, Snackbar.LENGTH_SHORT).show()
                    200 -> {
                        if(data == null){
                            Toast.makeText(baseContext, " data: $data ", Toast.LENGTH_SHORT).show()
                            return
                        }

                        if(data.account == null){
                            Toast.makeText(baseContext, " account: ${data.account} ", Toast.LENGTH_SHORT).show()
                            return
                        }

                        val user = UserResponseDto(
                            id = data.account.id,
                            name = data.account.user.name,
                            birthDate = data.account.user.birthDate,
                            avatar = data.account.user.avatar,
                            banner = data.account.user.banner,
                            type = data.account.user.type
                        )

                        val auth = response.body()!!.token
                        val editor = preferences.edit()
                        editor.putString("nameUser", data?.account?.user?.name)
                        editor.putString("token", data?.token)
                        editor.putString("nameUser", user.name)
                        editor.putString("avatar", user.avatar)
                        editor.putString("accessToken", auth)
                        editor.apply()

                        startActivity(Intent(baseContext, MainActivity::class.java))
                    }
                }
            }
            override fun onFailure(call: Call<LoginResponseDto>, t: Throwable) {
                Snackbar.make(sheetEmailBinding.root, R.string.connection_error, Snackbar.LENGTH_SHORT).show()
            }
        })

    }

    private fun changeScreen() {
        startActivity(Intent(this, RegisterActivity::class.java))
    }
}