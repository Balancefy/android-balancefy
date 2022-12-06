package com.balancefy.balancefyapp.adapter

import android.annotation.SuppressLint
import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.RecyclerView
import com.balancefy.balancefyapp.R
import com.balancefy.balancefyapp.databinding.ResRankBinding
import com.balancefy.balancefyapp.frames.ProfileAlternativeFragment
import com.balancefy.balancefyapp.models.response.*
import com.squareup.picasso.Picasso

class RankAccountAdapter(
    private val listTopics: List<AccountRankResponseDto>,
    private val token: String,
    private val onClick: (mensagem: String) -> Unit

) : RecyclerView.Adapter<RecyclerView.ViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        val inflater = ResRankBinding.inflate(LayoutInflater.from(parent.context), parent, false)

        return RankAccountHolder(inflater, parent.context)
    }

    inner class RankAccountHolder(
        private val binding: ResRankBinding,
        val context: Context
    ) : RecyclerView.ViewHolder(binding.root) {
        private var preferences =
            context.getSharedPreferences("Auth", AppCompatActivity.MODE_PRIVATE)

        @SuppressLint("SetTextI18n")
        fun attach(actualRank: AccountRankResponseDto, position: Int) {
            binding.position.text = (position + 1).toString()
            binding.name.text = actualRank.name
            binding.progress.text = actualRank.progress.toString()
            binding.goalsCompleted.text = actualRank.goal.toString()
            if(actualRank.avatar.isNotEmpty()) {
                Picasso.get().load(actualRank.avatar).into(binding.image)
            }

            binding.name.setOnClickListener {
                val editor = preferences.edit()
                editor.putString("altAccountName", actualRank.name)
                editor.putInt("altAccountId", actualRank.id)
                editor.putString("altAccountAvatar", actualRank.avatar)
                editor.apply()

                ProfileAlternativeFragment()

                //Mudar de fragment :)
                val activity = it.context as AppCompatActivity

                activity.supportFragmentManager.beginTransaction()
                    .replace(
                        R.id.fragmentContainerView,
                        ProfileAlternativeFragment()
                    ).commitNow()
            }
        }
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        (holder as RankAccountHolder).attach(listTopics[position], position)
    }

    override fun getItemCount(): Int {
        return listTopics.size
    }

}