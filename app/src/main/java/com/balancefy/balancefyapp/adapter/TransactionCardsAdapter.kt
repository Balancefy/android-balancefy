package com.balancefy.balancefyapp.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.RecyclerView
import com.balancefy.balancefyapp.R
import com.balancefy.balancefyapp.databinding.ResTransactionBinding
import com.balancefy.balancefyapp.models.response.Transaction

class TransactionCardsAdapter(
    private val transactionList: List<Transaction>,
    private val context: Context
): RecyclerView.Adapter<RecyclerView.ViewHolder>() {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        val inflater = ResTransactionBinding.inflate(LayoutInflater.from(parent.context), parent, false)

        return TransactionCardHolder(inflater)
    }

    inner class TransactionCardHolder(
        private val binding: ResTransactionBinding
    ) : RecyclerView.ViewHolder(binding.root) {

        fun attach(transaction : Transaction) {
            binding.tvValue.text = "%.2f".format(transaction.value)
            binding.tvCategory.text = transaction.category
            binding.tvDescription.text = transaction.description
            binding.tvDate.text = transaction.createdAt.toString()

            if(transaction.type == "Saída") {
                binding.tvValue.setTextColor(ContextCompat.getColor(context, R.color.red_balancefy))
                binding.icon.setColorFilter(ContextCompat.getColor(context,R.color.red_balancefy))
            }
        }
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        (holder as TransactionCardHolder).attach(transactionList[position])
    }

    override fun getItemCount(): Int {
        return transactionList.size
    }
}