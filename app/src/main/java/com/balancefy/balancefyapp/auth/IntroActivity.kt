package com.balancefy.balancefyapp.auth

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import com.balancefy.balancefyapp.R
import com.balancefy.balancefyapp.databinding.ActivityIntroBinding
import com.balancefy.balancefyapp.databinding.LoginBottomSheetBinding
import com.balancefy.balancefyapp.frames.MainActivity
import com.google.android.material.bottomsheet.BottomSheetDialog

class IntroActivity : AppCompatActivity() {

    private lateinit var binding: ActivityIntroBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityIntroBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.btnHasAcc.setOnClickListener{
            showBottomSheetDialog()
        }
    }

    private fun showBottomSheetDialog() {
        val dialog = BottomSheetDialog(this, R.style.BottomSheetDialog)

        val sheetBinding: LoginBottomSheetBinding =
            LoginBottomSheetBinding.inflate(layoutInflater, null, false)

        dialog.setContentView(sheetBinding.root)
        dialog.show()
    }

    fun cadastrar(view: View) {
        startActivity(Intent(this, LoginActivity::class.java))
    }

    fun entrar(view: View) {
        startActivity(Intent(this, MainActivity::class.java))
    }
}