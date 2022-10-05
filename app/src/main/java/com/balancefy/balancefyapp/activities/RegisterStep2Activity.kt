package com.balancefy.balancefyapp.activities

import android.content.Intent
import android.content.SharedPreferences
import android.content.res.ColorStateList
import android.os.Bundle
import android.text.Editable
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.balancefy.balancefyapp.R
import com.balancefy.balancefyapp.databinding.ActivityRegisterStep2Binding
import com.balancefy.balancefyapp.databinding.AgreementBottomSheetBinding
import com.balancefy.balancefyapp.databinding.LoginBottomSheetBinding
import com.balancefy.balancefyapp.models.request.RegisterRequestDto
import com.balancefy.balancefyapp.models.request.UserRegisterRequest
import com.balancefy.balancefyapp.models.response.LoginResponseDto
import com.balancefy.balancefyapp.models.response.RegisterResponseDto
import com.balancefy.balancefyapp.rest.Rest
import com.google.android.material.bottomsheet.BottomSheetDialog
import com.google.android.material.button.MaterialButton
import com.google.android.material.datepicker.MaterialDatePicker
import com.google.gson.annotations.SerializedName
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import java.time.LocalDate

class RegisterStep2Activity : AppCompatActivity() {
    private lateinit var binding: ActivityRegisterStep2Binding
    lateinit var preferences : SharedPreferences

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityRegisterStep2Binding.inflate(layoutInflater)
        preferences = getSharedPreferences("Auth", MODE_PRIVATE)

        setContentView(binding.root)

        binding.btnTravel.setOnClickListener {
           changeColor(binding.btnTravel)
        }

        binding.btnRetirement.setOnClickListener {
            changeColor(binding.btnRetirement)
        }

        binding.btnInvestments.setOnClickListener {
            changeColor(binding.btnInvestments)
        }

        binding.btnProperties.setOnClickListener {
            changeColor(binding.btnProperties)
        }

        binding.btnExchange.setOnClickListener {
            changeColor(binding.btnExchange)
        }

        binding.btnVehicles.setOnClickListener {
            changeColor(binding.btnVehicles)
        }

        binding.tvTerms.setOnClickListener {
            openAgreement()
        }

        binding.btnEnd.setOnClickListener {
            register()
        }
    }

    private fun changeColor(btn: MaterialButton) {
        if(btn.strokeColor.equals(getColorStateList(R.color.green_balancefy))) {
            btn.strokeColor = getColorStateList(R.color.white).withAlpha(24)
            btn.setTextColor(getColor(R.color.white))
        } else {
            btn.strokeColor = getColorStateList(R.color.green_balancefy)
            btn.setTextColor(getColor(R.color.green_balancefy))
        }
    }

    private fun openAgreement() {
        val dialog = BottomSheetDialog(this, R.style.BottomSheetDialog)

        val sheetBinding: AgreementBottomSheetBinding =
            AgreementBottomSheetBinding.inflate(layoutInflater, null, false)

        dialog.setContentView(sheetBinding.root)
        dialog.show()

        sheetBinding.btnAgree.setOnClickListener {
            binding.cbAgreement.isChecked = true
            dialog.cancel()
        }

        sheetBinding.btnDisagree.setOnClickListener {
            binding.cbAgreement.isChecked = false
            dialog.cancel()
        }
    }

    private fun register() {
        if(validateFields()) {
            val etIncoming = binding.etIncoming.toString()
            val birthDate = binding.etBirthDate.toString()

            val body = RegisterRequestDto(
                incoming = if (etIncoming.isEmpty()) 0.0 else etIncoming.toDouble(),
                user = UserRegisterRequest(
                    name = preferences.getString("name", null),
                    email = preferences.getString("email", null),
                    pass = preferences.getString("pass", null),
                    birthDate = birthDate
                )
            )

//            Rest.getRegisterInstance().register(body).enqueue(object : Callback<RegisterResponseDto> {
//                override fun onResponse(
//                    call: Call<RegisterResponseDto>,
//                    response: Response<RegisterResponseDto>
//                ) {
//                    val data = response.body()
//                    when(response.code()){
//                        400 -> Toast.makeText(baseContext, "Senha ou Email Inválido", Toast.LENGTH_SHORT).show()
//                        200 -> {
//                            startActivity(Intent(baseContext, IntroActivity::class.java))
//                        }
//                    }
//                }
//
//                override fun onFailure(call: Call<RegisterResponseDto>, t: Throwable) {
//                    Toast.makeText(baseContext, t.message, Toast.LENGTH_SHORT).show()
//                }
//            })
        }
    }

    private fun validateFields(): Boolean {
        val birthDate = binding.etBirthDate.text.toString()

        return when {
            birthDate.isNullOrBlank() -> {
                binding.etBirthDate.error = getString(R.string.error_message_birth_date)
                false
            }
            !binding.cbAgreement.isChecked -> {
                Toast.makeText(baseContext, getString(R.string.error_terms), Toast.LENGTH_SHORT).show()
                false
            }
            else -> true
        }
    }
}