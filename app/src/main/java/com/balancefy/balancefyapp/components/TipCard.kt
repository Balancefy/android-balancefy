package com.balancefy.balancefyapp.components

import android.content.Context
import android.util.AttributeSet
import android.view.LayoutInflater
import android.widget.LinearLayout
import com.balancefy.balancefyapp.R
import com.balancefy.balancefyapp.databinding.ResTipComponentBinding

class TipCard(context: Context, attrs: AttributeSet ) : LinearLayout(context, attrs) {
    private val binding: ResTipComponentBinding =  ResTipComponentBinding.inflate(
        LayoutInflater.from(context), this, true
    )

    init {
        val attributes = context.obtainStyledAttributes(attrs, R.styleable.TipCard)

        setTitle(attributes.getString(R.styleable.TipCard_tip_title)?: "")
        setDescription(attributes.getString(R.styleable.TipCard_tip_description) ?: "")
    }

    fun setTitle(value: String ) = value.also { this.binding.title.text = it }


    fun setDescription (value: String) = value.also { this.binding.description.text = it }


}