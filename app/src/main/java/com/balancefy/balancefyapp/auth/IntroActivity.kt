package com.balancefy.balancefyapp.auth

import android.content.Intent
import android.content.SharedPreferences
import android.os.Bundle
import android.view.View
import android.view.WindowManager
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.balancefy.balancefyapp.R
import com.balancefy.balancefyapp.databinding.ActivityIntroBinding
import com.balancefy.balancefyapp.databinding.EmailBottomSheetBinding
import com.balancefy.balancefyapp.databinding.LoginBottomSheetBinding
import com.balancefy.balancefyapp.models.request.LoginRequestDto
import com.balancefy.balancefyapp.models.response.LoginResponseDto
import com.balancefy.balancefyapp.rest.Rest
import com.balancefy.balancefyapp.services.Auth
import com.google.android.material.bottomsheet.BottomSheetBehavior.STATE_EXPANDED
import com.google.android.material.bottomsheet.BottomSheetDialog
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

        val lastUser = preferences.getString("nameUser", null)

        if (lastUser != null) {
            startActivity(Intent(baseContext, MainActivity::class.java))
        }

        binding.btnHasAcc.setOnClickListener {
            showBottomSheetDialog()
        }
    }

    private fun showBottomSheetDialog() {
        val dialog = BottomSheetDialog(this, R.style.BottomSheetDialog)

        val sheetLoginBinding: LoginBottomSheetBinding =
            LoginBottomSheetBinding.inflate(layoutInflater, null, false)

        dialog.setContentView(sheetLoginBinding.root)
        dialog.show()

        sheetLoginBinding.btnEntrarComEmail.setOnClickListener {
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

        sheetEmailBinding.btnLoginContinuar.setOnClickListener {
            authentication()
        }
    }

    private fun authentication() {
        val email = sheetEmailBinding.etLoginEmail.text.toString()
        val password = sheetEmailBinding.etLoginSenha.text.toString()

        val body = LoginRequestDto(email, password)
        println(body)

        val requestAuth = auth()

        requestAuth.login(body).enqueue(object : Callback<LoginResponseDto> {
            override fun onResponse(
                call: Call<LoginResponseDto>,
                response: Response<LoginResponseDto>
            ) {
                val data = response.body()
                when(response.code()){
                    400 -> Toast.makeText(baseContext, "Senha ou Email Inválido", Toast.LENGTH_SHORT).show()
                    200 -> {
                        val editor = preferences.edit()
                        editor.putString("nameUser", data?.conta?.usuario?.nome)
                        editor.apply()
                        startActivity(Intent(baseContext, MainActivity::class.java))

                    }
                }
            }

            override fun onFailure(call: Call<LoginResponseDto>, t: Throwable) {
                Toast.makeText(baseContext, t.message, Toast.LENGTH_SHORT).show()
            }
        })

    }

    fun auth(): Auth? {
        val requestAuth = Rest.getInstance().create(Auth::class.java)
        return requestAuth
    }

    fun cadastrar(view: View) {
        startActivity(Intent(this, LoginActivity::class.java))
    }
}