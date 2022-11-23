package com.balancefy.balancefyapp.frames

import android.content.SharedPreferences
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.WindowManager
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import com.balancefy.balancefyapp.R
import com.balancefy.balancefyapp.adapter.TransactionCardsAdapter
import com.balancefy.balancefyapp.databinding.FragmentGoalDetailsBinding
import com.balancefy.balancefyapp.databinding.TransactionBottomSheetBinding
import com.balancefy.balancefyapp.models.response.Transaction
import com.balancefy.balancefyapp.models.request.TransactionRequest
import com.balancefy.balancefyapp.models.response.GoalsDetailsResponse
import com.balancefy.balancefyapp.models.response.GoalsResponse
import com.balancefy.balancefyapp.models.response.TaskResponse
import com.balancefy.balancefyapp.rest.Rest
import com.google.android.material.bottomsheet.BottomSheetBehavior
import com.google.android.material.bottomsheet.BottomSheetDialog
import com.google.android.material.button.MaterialButton
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import java.util.*

class GoalDetailsFragment : Fragment() {
    private lateinit var binding: FragmentGoalDetailsBinding
    private var goalId: Int? = null
    private var token: String? = null
    private lateinit var goalDetails: GoalsResponse
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

    private fun showGoalsDetails() {
        Rest.getGoalInstance().findById("Bearer $token", goalId!!).enqueue(object : Callback<GoalsDetailsResponse> {
            override fun onResponse(
                call: Call<GoalsDetailsResponse>,
                response: Response<GoalsDetailsResponse>
            ) {
                when(response.code()){
                    200 -> {
                        goalDetails = response.body()?.goal!!
                        configScreen(response.body()!!)
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
        setCurrentTask(goal.tasks.first{ it.done == 0})
        setGoalTransaction()

        binding.addTransaction.setOnClickListener {
            showTransactionBottomSheet()
        }
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
        binding.goalTitle.setRemainingDays("dias restantes")
    }

    private fun setCurrentTask(task: TaskResponse) {
        binding.currentTask.setTitle(task.description)
        binding.currentTask.setDescription("R$%.2f".format(task.value))
        binding.currentTask.setScore("+%.0fxp".format(task.score))
    }

    private fun setGoalTransaction() {
        configRecyclerView(
            listOf(
                TransactionRequest(
                    value = 50.0,
                    category = "Lazer",
                    description = "Netflix",
                    type = "Entrada",
                    goal = goalDetails!!
                )
            )
        )
    }

    private fun configRecyclerView(transaction: List<Transaction>) {
        val recyclerContainer = binding.recyclerContainer
        recyclerContainer.layoutManager = LinearLayoutManager(context)

        recyclerContainer.adapter = TransactionCardsAdapter(
            transaction,
            requireContext()
        )
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
                goal = goalDetails!!
            )

            Rest.getTransactionInstance().create("Bearer $token", body).enqueue(object : Callback<Objects> {
                override fun onResponse(
                    call: Call<Objects>,
                    response: Response<Objects>
                ) {
                    when(response.code()){
                        201 -> {
                            Toast.makeText(context, R.string.created_transaction, Toast.LENGTH_SHORT).show()
                        }
                        else -> {
                            Toast.makeText(context, R.string.register_error, Toast.LENGTH_SHORT).show()
                        }
                    }
                }

                override fun onFailure(call: Call<Objects>, t: Throwable) {
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