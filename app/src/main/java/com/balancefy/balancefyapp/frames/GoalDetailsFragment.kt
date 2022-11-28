package com.balancefy.balancefyapp.frames

import android.content.SharedPreferences
import android.os.Build
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.WindowManager
import android.widget.LinearLayout
import android.widget.Toast
import androidx.annotation.RequiresApi
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.marginTop
import androidx.core.view.updatePadding
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.balancefy.balancefyapp.R
import com.balancefy.balancefyapp.adapter.GoalCardsAdapter
import com.balancefy.balancefyapp.adapter.TransactionCardsAdapter
import com.balancefy.balancefyapp.databinding.FragmentGoalDetailsBinding
import com.balancefy.balancefyapp.databinding.TransactionBottomSheetBinding
import com.balancefy.balancefyapp.models.request.TransactionRequest
import com.balancefy.balancefyapp.models.response.*
import com.balancefy.balancefyapp.rest.Rest
import com.google.android.material.bottomsheet.BottomSheetBehavior
import com.google.android.material.bottomsheet.BottomSheetDialog
import com.google.android.material.button.MaterialButton
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import java.time.LocalDate
import java.time.format.DateTimeFormatter
import java.time.temporal.ChronoUnit
import java.util.*

class GoalDetailsFragment : Fragment() {
    private lateinit var binding: FragmentGoalDetailsBinding
    private var goalId: Int? = null
    private var token: String? = null
    private lateinit var goalDetails: GoalsDetailsResponse
    private lateinit var preferences: SharedPreferences
    private lateinit var sheetTransactionBinding: TransactionBottomSheetBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentGoalDetailsBinding.inflate(layoutInflater)
        return binding.root
    }
    // Falta o get das transações

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        preferences = context?.getSharedPreferences("Auth", AppCompatActivity.MODE_PRIVATE)!!

        token = preferences.getString("token", null)
        goalId = preferences.getInt("goalId", 0)

        showGoalsDetails()
    }

    private fun showGoalsDetails(refresh: Boolean = false) {
        Rest.getGoalInstance().findById("Bearer $token", goalId!!).enqueue(object : Callback<GoalsDetailsResponse> {
            override fun onResponse(
                call: Call<GoalsDetailsResponse>,
                response: Response<GoalsDetailsResponse>
            ) {
                when(response.code()){
                    200 -> {
                        goalDetails = response.body()!!

                        if(refresh) {
                            configScreenRefresh(response.body()!!)
                        } else {
                            configScreen(response.body()!!)
                        }
                    }
                    else -> {
                        Toast.makeText(context, R.string.connection_error, Toast.LENGTH_SHORT).show()
                    }
                }
            }

            override fun onFailure(call: Call<GoalsDetailsResponse>, t: Throwable) {
                Toast.makeText(context, R.string.connection_error, Toast.LENGTH_SHORT).show()
            }
        })
    }

    private fun configScreen(goal: GoalsDetailsResponse) {
        setProgress(goal)
        setGoalDescription(goal.goal)
        setCurrentTask(goal.tasks.firstOrNull{ it.done == 0})
        setGoalTransaction()

        binding.addTransaction.setOnClickListener {
            showTransactionBottomSheet()
        }
    }

    private fun configScreenRefresh(goal: GoalsDetailsResponse) {
        setProgress(goal)
        setGoalDescription(goal.goal)
        setCurrentTask(goal.tasks.firstOrNull{ it.done == 0})
    }

    private fun setProgress(goal: GoalsDetailsResponse) {
        binding.goalProgress.setTotalScore("%.0fxp".format(goal.goal.score))

        var progress = 0.0

        goal.tasks.map {
            if(it.done == 1) {
                progress += it.score
            }
        }

        binding.goalProgress.setProgress(progress)
    }

    private fun setGoalDescription(goal: GoalsResponse) {
        binding.goalTitle.setTitle(goal.description)
        binding.goalTitle.setRemainingDays("${ChronoUnit.DAYS.between(LocalDate.now(), LocalDate.parse(goal.estimatedTime.replace("-", ""), DateTimeFormatter.BASIC_ISO_DATE))} ")
    }

    private fun setCurrentTask(task: TaskResponse?) {
        if(task == null) {
            binding.currentTask.visibility = View.GONE
            binding.tvNoCurrentTask.visibility = View.VISIBLE
            binding.transactionTitle.updatePadding(top = 50)
        } else {
            binding.currentTask.setTitle(task.description)
            binding.currentTask.setDescription("R$%.2f".format(task.value))
            binding.currentTask.setScore("+%.0fxp".format(task.score))

            binding.currentTask.setCompleteOnClickListener {
                Rest.getGoalInstance().completeTask("Bearer ${token!!}", task.id)
                    .enqueue(object : Callback<Unit> {
                        override fun onResponse(
                            call: Call<Unit>,
                            response: Response<Unit>
                        ) {
                            when (response.code()) {
                                200 -> {
                                    showGoalsDetails(true)
                                    Toast.makeText(context, R.string.complete_task, Toast.LENGTH_SHORT).show()
                                }
                                else -> {
                                    Toast.makeText(context, R.string.error_complete_task, Toast.LENGTH_SHORT).show()
                                }
                            }
                        }

                        override fun onFailure(call: Call<Unit>, t: Throwable) {
                            Toast.makeText(context, R.string.error_complete_task, Toast.LENGTH_SHORT).show()
                        }
                    }
                    )
            }
        }
    }

    private fun setGoalTransaction(refresh: Boolean = false) {
        Rest.getTransactionInstance().getTransactionByGoal("Bearer ${token!!}", goalId!!).enqueue(object : Callback<List<TransactionResponse>> {
            override fun onResponse(
                call: Call<List<TransactionResponse>>,
                response: Response<List<TransactionResponse>>
            ) {
                val data = response.body()

                when(response.code()){
                    200 -> {
                        if(refresh) {
                            configRecyclerViewRefresh(data ?: emptyList())
                        } else {
                            configRecyclerView(data ?: emptyList())
                        }
                    }
                    else -> {
                        binding.tvError.text = context?.getString(R.string.no_transactions)
                    }
                }
            }
            override fun onFailure(call: Call<List<TransactionResponse>>, t: Throwable) {
                binding.tvError.text = context?.getString(R.string.connection_error)
            }
        })
    }

    private fun configRecyclerView(transaction: List<TransactionResponse>) {
        if(transaction.isEmpty()) {
            binding.tvError.visibility = View.VISIBLE
        } else {
            binding.tvError.visibility = View.GONE

            val recyclerContainer = binding.recyclerContainer
            recyclerContainer.layoutManager = LinearLayoutManager(context, LinearLayoutManager.HORIZONTAL, false)

            recyclerContainer.adapter = TransactionCardsAdapter(
                transaction,
                requireContext()
            )
        }
    }

    private fun configRecyclerViewRefresh(transaction: List<TransactionResponse>) {
        if(transaction.isEmpty()) {
            binding.tvError.visibility = View.VISIBLE
        } else {
            binding.tvError.visibility = View.GONE

            val recyclerContainer = binding.recyclerContainer

            (recyclerContainer.adapter as TransactionCardsAdapter).notifyDataSetChanged()
        }
    }

    private fun showTransactionBottomSheet() {
        val dialog = BottomSheetDialog(requireContext(), R.style.BottomSheetDialog).apply {
            window?.setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_ADJUST_RESIZE)
        }

        dialog.behavior.skipCollapsed = true
        dialog.behavior.state = BottomSheetBehavior.STATE_EXPANDED

        sheetTransactionBinding = TransactionBottomSheetBinding.inflate(layoutInflater, null, false)

        dialog.setContentView(sheetTransactionBinding.root)
        dialog.show()

        var type = ""

        sheetTransactionBinding.btnIncoming.setOnClickListener {
            changeColor(sheetTransactionBinding.btnIncoming)

            if(type.isNotEmpty()) {
                changeColor(sheetTransactionBinding.btnSpending)
            }

            type = "Entrada"
        }

        sheetTransactionBinding.btnSpending.setOnClickListener {
            changeColor(sheetTransactionBinding.btnSpending)

            if(type.isNotEmpty()) {
                changeColor(sheetTransactionBinding.btnIncoming)
            }

            type = "Saída"
        }

        sheetTransactionBinding.btnCreate.setOnClickListener {
            if(createTransaction(type)) {
                dialog.dismiss()
            }
        }
    }

    private fun createTransaction(type: String): Boolean {
        if(validateTransactionFields(type)) {
            val body = TransactionRequest(
                value = sheetTransactionBinding.etValue.text.toString().toDouble(),
                category = sheetTransactionBinding.transactionCategory.text.toString(),
                description = sheetTransactionBinding.etDescription.text.toString(),
                type = type,
                goal = goalDetails.goal
            )

            println(body)

            Rest.getTransactionInstance().create("Bearer $token", body).enqueue(object : Callback<Unit> {
                override fun onResponse(
                    call: Call<Unit>,
                    response: Response<Unit>
                ) {
                    when(response.code()){
                        201 -> {
                            Toast.makeText(context, R.string.created_transaction, Toast.LENGTH_SHORT).show()
                            setGoalTransaction()
                        }
                        else -> {
                            Toast.makeText(context, R.string.register_error, Toast.LENGTH_SHORT).show()
                        }
                    }
                }

                override fun onFailure(call: Call<Unit>, t: Throwable) {
                    Toast.makeText(context, R.string.connection_error, Toast.LENGTH_SHORT).show()
                }
            })

            return true
        }

        return false
    }

    private fun validateTransactionFields(type: String): Boolean {
        return when {
            sheetTransactionBinding.etDescription.text.toString().isEmpty() -> {
                sheetTransactionBinding.etDescription.error = getString(R.string.error_empty_field)
                false
            }
            sheetTransactionBinding.etValue.text.toString().isEmpty() -> {
                sheetTransactionBinding.etValue.error = getString(R.string.error_empty_field)
                false
            }
            sheetTransactionBinding.etValue.text.toString().toDouble() <= 0 -> {
                sheetTransactionBinding.etValue.error = getString(R.string.error_empty_value)
                false
            }
            sheetTransactionBinding.transactionCategory.text.toString().isEmpty() -> {
                sheetTransactionBinding.transactionCategory.error = getString(R.string.error_empty_field)
                false
            }
            type.isEmpty() -> {
                Toast.makeText(context, R.string.error_transaction_type, Toast.LENGTH_SHORT).show()
                false
            }
            else -> true
        }
    }

    private fun changeColor(btn: MaterialButton) {
        if(btn.strokeColor.equals(context?.getColorStateList(R.color.green_balancefy))) {
            btn.strokeColor = context?.getColorStateList(R.color.grey)?.withAlpha(24)
            btn.setTextColor(context?.getColor(R.color.grey)!!)
        } else {
            btn.strokeColor = context?.getColorStateList(R.color.green_balancefy)
            btn.setTextColor(context?.getColor(R.color.green_balancefy)!!)
        }
    }
}