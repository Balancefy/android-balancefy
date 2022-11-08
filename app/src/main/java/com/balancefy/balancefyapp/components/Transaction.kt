package com.balancefy.balancefyapp.components

import android.content.Context
import android.graphics.Color
import android.util.AttributeSet
import android.view.LayoutInflater
import android.widget.LinearLayout
import androidx.core.content.ContextCompat
import androidx.core.graphics.blue
import androidx.core.graphics.green
import androidx.core.graphics.red
import com.balancefy.balancefyapp.R
import com.balancefy.balancefyapp.databinding.ResTransactionBinding

class Transaction(context: Context, attrs: AttributeSet) : LinearLayout(context, attrs) {

    private val binding: ResTransactionBinding = ResTransactionBinding.inflate(
        LayoutInflater.from(context), this, true
    )

    init {

        val attributes = context.obtainStyledAttributes(attrs, R.styleable.Transaction)

        this.binding.tvCategory.text = attributes.getString(R.styleable.Transaction_category)
        this.binding.tvValue.text = attributes.getString(R.styleable.Transaction_value)
        this.binding.tvDescription.text = attributes.getString(R.styleable.Transaction_description)
        this.binding.tvDate.text = attributes.getString(R.styleable.Transaction_date)

        if(attributes.getString(R.styleable.Transaction_type).equals("out")){
            this.binding.tvValue.setTextColor(ContextCompat.getColor(context, R.color.red_balancefy))
            this.binding.icon.setColorFilter(ContextCompat.getColor(context,R.color.red_balancefy))
        }
    }
}