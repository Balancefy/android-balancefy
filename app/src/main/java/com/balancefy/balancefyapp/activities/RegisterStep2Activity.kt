package com.balancefy.balancefyapp.activities

import android.content.Intent
import android.content.SharedPreferences
import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.balancefy.balancefyapp.R
import com.balancefy.balancefyapp.databinding.ActivityRegisterStep2Binding
import com.balancefy.balancefyapp.databinding.AgreementBottomSheetBinding
import com.balancefy.balancefyapp.models.request.RegisterRequest
import com.balancefy.balancefyapp.models.request.UserRegisterRequest
import com.balancefy.balancefyapp.rest.Rest
import com.google.android.material.bottomsheet.BottomSheetDialog
import com.google.android.material.button.MaterialButton
import com.google.android.material.datepicker.CalendarConstraints
import com.google.android.material.datepicker.DateValidatorPointBackward
import com.google.android.material.datepicker.MaterialDatePicker
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import java.text.SimpleDateFormat
import java.util.*

class RegisterStep2Activity : AppCompatActivity() {
    private lateinit var binding: ActivityRegisterStep2Binding
    lateinit var preferences : SharedPreferences
    private var date: Long? = null
    private var interestsSelected: Int = 0

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

        binding.btnBirthDate.setOnClickListener {
            selectDate()
        }
    }

    private fun selectDate() {
        val constrains = CalendarConstraints
            .Builder()
            .setValidator(DateValidatorPointBackward.now())
            .build()

        val datePicker = MaterialDatePicker.Builder.datePicker()
            .setTitleText(getString(R.string.birth_date))
            .setTheme(R.style.ThemeOverlay_App_MaterialCalendar)
            .setCalendarConstraints(constrains)
            .build()

        datePicker.show(supportFragmentManager, "MATERIAL_DATE_PICKER")

        datePicker.addOnPositiveButtonClickListener {
            val sdf = SimpleDateFormat("dd/MM/yyyy", Locale.getDefault())
            date = it
            binding.btnBirthDate.text = sdf.format(it)
        }
    }

    private fun changeColor(btn: MaterialButton) {
        if(btn.strokeColor.equals(getColorStateList(R.color.green_balancefy))) {
            btn.strokeColor = getColorStateList(R.color.grey).withAlpha(24)
            btn.setTextColor(getColor(R.color.grey))
            interestsSelected--
        } else {
            btn.strokeColor = getColorStateList(R.color.green_balancefy)
            btn.setTextColor(getColor(R.color.green_balancefy))
            interestsSelected++
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
            val etIncoming = binding.etIncoming.text.toString()

            val body = RegisterRequest(
                incoming = if (etIncoming.isEmpty()) 0.0 else etIncoming.toDouble(),
                user = UserRegisterRequest(
                    name = preferences.getString("name", null),
                    email = preferences.getString("email", null),
                    pass = preferences.getString("pass", null),
                    birthDate = SimpleDateFormat("yyyy-MM-dd", Locale.getDefault()).format(date)
                )
            )

            Rest.getRegisterInstance().register(body).enqueue(object : Callback<Unit> {
                override fun onResponse(
                    call: Call<Unit>,
                    response: Response<Unit>
                ) {
                    when(response.code()){
                        201 -> {
                            startActivity(Intent(baseContext, IntroActivity::class.java))
                        }
                        else -> Toast.makeText(baseContext, R.string.register_error, Toast.LENGTH_SHORT).show()
                    }
                }

                override fun onFailure(call: Call<Unit>, t: Throwable) {
                    println(t.message)
                    Toast.makeText(baseContext, R.string.connection_error, Toast.LENGTH_SHORT).show()
                }
            })
        }
    }

    private fun validateFields(): Boolean {
        return when {
            date == null -> {
                Toast.makeText(baseContext, R.string.error_message_date, Toast.LENGTH_SHORT).show()
                false
            }
            !binding.cbAgreement.isChecked -> {
                Toast.makeText(baseContext, R.string.error_terms, Toast.LENGTH_SHORT).show()
                false
            }
            interestsSelected < 3 -> {
                Toast.makeText(baseContext, R.string.error_less_interests, Toast.LENGTH_SHORT).show()
                false
            }
            else -> true
        }
    }
}