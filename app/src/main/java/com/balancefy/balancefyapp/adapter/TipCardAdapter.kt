package com.balancefy.balancefyapp.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.balancefy.balancefyapp.databinding.ResTipComponentBinding
import com.balancefy.balancefyapp.models.response.TipsResponse

class TipCardAdapter(
    private val tipList : List <TipsResponse>
): RecyclerView.Adapter<RecyclerView.ViewHolder>() {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        val inflater = ResTipComponentBinding.inflate(LayoutInflater.from(parent.context), parent, false )

        return TipCardHolder(inflater)
    }
    inner class TipCardHolder(
        private val binding: ResTipComponentBinding
    ): RecyclerView.ViewHolder(binding.root){
        fun attach(tip: TipsResponse){
            binding.title.text = tip.titulo
            binding.description.text = tip.descricao
        }
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        (holder as TipCardHolder).attach(tipList[position])
    }

    override fun getItemCount(): Int {
        return tipList.size
    }

}