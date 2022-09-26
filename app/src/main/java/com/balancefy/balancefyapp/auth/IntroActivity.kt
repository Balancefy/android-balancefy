package com.balancefy.balancefyapp.auth

import android.content.Intent
import android.os.Bundle
import android.view.View
import android.view.WindowManager
import androidx.appcompat.app.AppCompatActivity
import com.balancefy.balancefyapp.R
import com.balancefy.balancefyapp.databinding.ActivityIntroBinding
import com.balancefy.balancefyapp.databinding.EmailBottomSheetBinding
import com.balancefy.balancefyapp.databinding.LoginBottomSheetBinding
import com.google.android.material.bottomsheet.BottomSheetBehavior
import com.google.android.material.bottomsheet.BottomSheetBehavior.STATE_EXPANDED
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

        val sheetLoginBinding: LoginBottomSheetBinding =
            LoginBottomSheetBinding.inflate(layoutInflater, null, false)

        dialog.setContentView(sheetLoginBinding.root)
        dialog.show()

        sheetLoginBinding.btnEntrarComEmail.setOnClickListener{
            dialog.dismiss()
            showEmailSheetDialog()
        }
    }

    private fun showEmailSheetDialog() {
        val dialog = BottomSheetDialog(this, R.style.BottomSheetDialog).apply {
            window?.setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_ADJUST_RESIZE)
        }

        dialog.behavior.skipCollapsed = true
        dialog.behavior.state = STATE_EXPANDED

        val sheetEmailBinding: EmailBottomSheetBinding =
            EmailBottomSheetBinding.inflate(layoutInflater, null, false)

        dialog.setContentView(sheetEmailBinding.root)
        dialog.show()
    }

    fun cadastrar(view: View) {
        startActivity(Intent(this, LoginActivity::class.java))
    }

    fun entrar(view: View) {
        startActivity(Intent(this, MainActivity::class.java))
    }
}