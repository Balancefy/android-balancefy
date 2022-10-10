package com.balancefy.balancefyapp.activities

import android.content.Intent
import android.content.SharedPreferences
import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.balancefy.balancefyapp.R
import com.balancefy.balancefyapp.databinding.ActivityRegisterBinding
import com.balancefy.balancefyapp.databinding.AgreementBottomSheetBinding
import com.balancefy.balancefyapp.databinding.PasswordRequirmentsBottomSheetBinding
import com.google.android.material.bottomsheet.BottomSheetDialog

class RegisterActivity : AppCompatActivity() {
    private lateinit var binding: ActivityRegisterBinding
    lateinit var preferences : SharedPreferences

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityRegisterBinding.inflate(layoutInflater)
        preferences = getSharedPreferences("Auth", MODE_PRIVATE)
        setContentView(binding.root)

        binding.btnNextStep.setOnClickListener {
            changeScreen()
        }

        binding.etLayoutPass.setEndIconOnClickListener {
            showPasswordsRequirment()
        }
    }

    private fun changeScreen() {
        if(validateFields()) {
            val editor = preferences.edit()
            editor.putString("name", "${binding.etName.text.toString().trim()} ${binding.etLastName.text.toString().trim()}")
            editor.putString("email", binding.etEmail.text.toString().trim())
            editor.putString("pass", binding.etPassword.text.toString().trim())
            editor.apply()
            startActivity(Intent(this, RegisterStep2Activity::class.java))
        }
    }

    private fun validateFields(): Boolean {
        val name = binding.etName.text.toString()
        val lastName = binding.etLastName.text.toString()
        val email = binding.etEmail.text.toString()
        val pass = binding.etPassword.text.toString()
        val confirmPass = binding.etConfirmPassword.text.toString()

        return when {
            name.isNullOrEmpty()  -> {
                binding.etName.error = getString(R.string.error_empty_field)
                false
            }
            name.length < 3 -> {
                binding.etName.error = getString(R.string.error_invalid_field)
                false
            }
            lastName.length < 3 -> {
                binding.etLastName.error = getString(R.string.error_invalid_field)
                false
            }
            lastName.isNullOrEmpty() -> {
                binding.etLastName.error = getString(R.string.error_empty_field)
                false
            }
            email.isNullOrEmpty() -> {
                binding.etEmail.error = getString(R.string.error_empty_field)
                false
            }
            validateEmail(email) -> {
                binding.etEmail.error = getString(R.string.error_invalid_field)
                false
            }
            pass.isNullOrEmpty() -> {
                binding.etPassword.error = getString(R.string.error_empty_field)
                false
            }
            validatePass(pass) -> {
                binding.etPassword.error = getString(R.string.error_invalid_pass)
                false
            }
            confirmPass.isNullOrEmpty() -> {
                binding.etConfirmPassword.error = getString(R.string.error_empty_field)
                false
            }
            pass != confirmPass -> {
                binding.etConfirmPassword.error = getString(R.string.error_different_pass)
                false
            }
            else -> true
        }
    }

    private fun validateEmail(email: String) = email.length < 3 || !email.contains("@") || !email.contains(".com")

    private fun validatePass(pass: String) = !pass.contains(regex = "^(?=.*\\d)(?=.*[a-z])(?=.*[A-Z])(?=.*[$*&@#])[0-9a-zA-Z$*&@#]{8,}$".toRegex())

    private fun showPasswordsRequirment() {
        val dialog = BottomSheetDialog(this, R.style.BottomSheetDialog)

        val sheetBinding: PasswordRequirmentsBottomSheetBinding =
            PasswordRequirmentsBottomSheetBinding.inflate(layoutInflater, null, false)

        dialog.setContentView(sheetBinding.root)
        dialog.show()

        sheetBinding.btnNext.setOnClickListener {
            dialog.cancel()
        }
    }
}