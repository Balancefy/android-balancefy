package com.balancefy.balancefyapp.activities

import android.content.Intent
import android.content.SharedPreferences
import android.os.Bundle
import android.view.View
import android.view.WindowManager
import android.view.animation.Animation
import android.view.animation.AnimationUtils
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.core.os.bundleOf
import com.balancefy.balancefyapp.R
import com.balancefy.balancefyapp.adapter.TransactionCardsAdapter
import com.balancefy.balancefyapp.databinding.ActivityMainBinding
import com.balancefy.balancefyapp.databinding.GoalBottomSheetBinding
import com.balancefy.balancefyapp.databinding.PostBottomSheetBinding
import com.balancefy.balancefyapp.databinding.TransactionBottomSheetBinding
import com.balancefy.balancefyapp.frames.*
import com.balancefy.balancefyapp.models.request.CreateGoal
import com.balancefy.balancefyapp.models.request.GoalCategory
import com.balancefy.balancefyapp.models.request.PostRequest
import com.balancefy.balancefyapp.models.request.RepeatedTransactionRequest
import com.balancefy.balancefyapp.rest.Rest
import com.google.android.material.bottomsheet.BottomSheetBehavior
import com.google.android.material.bottomsheet.BottomSheetDialog
import com.google.android.material.button.MaterialButton
import com.google.android.material.datepicker.CalendarConstraints
import com.google.android.material.datepicker.DateValidatorPointForward
import com.google.android.material.datepicker.MaterialDatePicker
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import java.sql.Timestamp
import java.text.SimpleDateFormat
import java.util.*

class MainActivity : AppCompatActivity() {
    // Bindings
    private lateinit var binding: ActivityMainBinding
    private lateinit var sheetGoalBinding: GoalBottomSheetBinding
    private lateinit var sheetTransactionBinding: TransactionBottomSheetBinding
    private lateinit var sheetPostBottomSheetBinding: PostBottomSheetBinding

    // Goal creation
    private var date: String? = null
    // Transaction creation
    private var typeSelected = false

    // Preferences
    lateinit var preferences : SharedPreferences
    private var token: String? = ""

    // Animation
    private val fromBottom: Animation by lazy { AnimationUtils.loadAnimation(this, R.anim.from_bottom_anim) }
    private val toBottom: Animation by lazy { AnimationUtils.loadAnimation(this, R.anim.to_bottom_anim) }
    private var clicked = false

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        preferences = getSharedPreferences("Auth", MODE_PRIVATE)
        setContentView(binding.root)

        token = preferences.getString("token", null)

        initHome()

        binding.topAppBar.setOnMenuItemClickListener(){
            swapFragment(it.itemId)
            true
        }

        binding.bottomMenu.setOnMenuItemClickListener {
            swapFragment(it.itemId)
            true
        }

        binding.fabMenu.setOnClickListener {
            onFabMenuClicked()
        }

        binding.topAppBar.setNavigationOnClickListener {
            logOut()
        }

        binding.fabGoals.setOnClickListener {
            showGoalCreationBottomSheet()
        }

        binding.fabPosts.setOnClickListener {
            showPostBottomSheet()
        }

        binding.fabTransaction.setOnClickListener {
            showTransactionBottomSheet()
        }
    }

    override fun onBackPressed() {
        if(supportFragmentManager.backStackEntryCount >= 1) {
            super.onBackPressed()
        }
    }

    private fun initHome() {

        val homeFragment = HomeFragment()

        val bundle = bundleOf(
            "nameUser" to preferences.getString("nameUser", null),
            "accountId" to preferences.getInt("accountId", 0),
            "token" to token
        )

        homeFragment.arguments = bundle

        supportFragmentManager.beginTransaction().add(
            binding.fragmentContainerView.id,
            homeFragment
        ).commit()

        binding.topAppBar.title =  getString(R.string.description_home)
    }

    private fun swapFragment(fragmentId : Int){
        val transaction = supportFragmentManager.beginTransaction()
        val container = binding.fragmentContainerView.id
        val bundle = bundleOf(
            "nameUser" to preferences.getString("nameUser", null),
            "accountId" to preferences.getInt("accountId", 0),
            "token" to token
        )

        val fragment = when(fragmentId){
            R.id.home_fragment -> {
                binding.topAppBar.title =  getString(R.string.description_home)
                HomeFragment()
            }
            R.id.goal_fragment -> {
                binding.topAppBar.title =  getString(R.string.description_goal)
                GoalPagesFragment()
            }
            R.id.forum_fragment -> {
                binding.topAppBar.title =  getString(R.string.description_forum)
                ForumFragment()
            }
            R.id.rank_fragment -> {
                binding.topAppBar.title =  getString(R.string.description_rank)
                RankFragment()
            }
            R.id.profile_fragment -> {
                binding.topAppBar.title =  getString(R.string.description_profile)
                ProfileFragment()
            }
            else -> return
        }

        fragment.arguments = bundle

        transaction
            .replace(container, fragment)
            .addToBackStack(null)
            .commit()
    }

    private fun onFabMenuClicked() {
        setVisibility()
        setAnimation()

        clicked = !clicked
    }

    private fun setVisibility() {
        if (!clicked) {
            binding.llMenu.visibility = View.VISIBLE
        } else {
            binding.llMenu.visibility = View.GONE
        }
    }

    private fun setAnimation() {
        if (!clicked) {
            binding.llMenu.startAnimation(fromBottom)
        } else {
            binding.llMenu.startAnimation(toBottom)
        }
    }

    private fun showGoalCreationBottomSheet() {
        val dialog = BottomSheetDialog(this, R.style.BottomSheetDialog).apply {
            window?.setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_ADJUST_RESIZE)
        }

        dialog.behavior.skipCollapsed = true
        dialog.behavior.state = BottomSheetBehavior.STATE_EXPANDED

        sheetGoalBinding = GoalBottomSheetBinding.inflate(layoutInflater, null, false)

        dialog.setContentView(sheetGoalBinding.root)
        dialog.show()

        sheetGoalBinding.btnEstimatedDate.setOnClickListener {
            selectDate()
        }

        sheetGoalBinding.btnCreate.setOnClickListener {
            if(createGoal()) {
                dialog.dismiss()
            }
        }
    }

    private fun selectDate() {
        val constrains = CalendarConstraints
            .Builder()
            .setValidator(DateValidatorPointForward.now())
            .build()

        val datePicker = MaterialDatePicker.Builder.datePicker()
            .setTitleText(getString(R.string.birth_date))
            .setTheme(R.style.ThemeOverlay_App_MaterialCalendar)
            .setCalendarConstraints(constrains)
            .build()

        datePicker.show(supportFragmentManager, "MATERIAL_DATE_PICKER")

        datePicker.addOnPositiveButtonClickListener {
            val sdf = SimpleDateFormat("dd/MM/yyyy", Locale.getDefault())

            var ts = Timestamp(it)
            val cal = Calendar.getInstance()
            cal.timeInMillis = ts.time
            cal.add(Calendar.DAY_OF_MONTH, +1)
            ts = Timestamp(cal.time.time)

            date = sdf.format(ts)
            sheetGoalBinding.btnEstimatedDate.text = date
        }
    }

    private fun createGoal(): Boolean {
        val initialValue = sheetGoalBinding.etGoalInitialValue.text.toString()

        if(validateGoalFields()) {
            val body = CreateGoal(
                goal = GoalCategory(getCategory()),
                description = sheetGoalBinding.etDescription.text.toString(),
                totalValue = sheetGoalBinding.etGoalValue.text.toString().toDouble(),
                initialValue = if (initialValue.isEmpty()) 0.0 else initialValue.toDouble(),
                estimatedTime = date!!
            )

            Rest.getGoalInstance().createGoal("Bearer $token", body).enqueue(object : Callback<Unit> {
                override fun onResponse(
                    call: Call<Unit>,
                    response: Response<Unit>
                ) {
                    when(response.code()){
                        201 -> {
                            Toast.makeText(baseContext, R.string.created_goal, Toast.LENGTH_SHORT).show()
                        }
                        else -> {
                            Toast.makeText(baseContext, R.string.register_error, Toast.LENGTH_SHORT).show()
                        }
                    }
                }

                override fun onFailure(call: Call<Unit>, t: Throwable) {
                    Toast.makeText(baseContext, R.string.connection_error, Toast.LENGTH_SHORT).show()
                }
            })

            return true
        }

        return false
    }

    private fun getCategory(): Int = when(sheetGoalBinding.goalCategory.text.toString()) {
            getString(R.string.goal_category_internacional_trip) -> 1
            getString(R.string.goal_category_nacional_trip) -> 2
            getString(R.string.goal_category_purchase_house) -> 3
            getString(R.string.goal_category_purchase_car) -> 4
            getString(R.string.goal_category_college) -> 5
            getString(R.string.goal_category_debt_discharge) -> 6
            else -> 7
    }

    private fun validateGoalFields(): Boolean {
        return when {
            sheetGoalBinding.etDescription.text.toString().isEmpty() -> {
                sheetGoalBinding.etDescription.error = getString(R.string.error_empty_field)
                false
            }
            sheetGoalBinding.etGoalValue.text.toString().isEmpty() -> {
                sheetGoalBinding.etGoalValue.error = getString(R.string.error_empty_field)
                false
            }
            sheetGoalBinding.etGoalValue.text.toString().toDouble() <= 0 -> {
                sheetGoalBinding.etGoalValue.error = getString(R.string.error_empty_field)
                false
            }
            date == null -> {
                Toast.makeText(baseContext, R.string.error_message_date, Toast.LENGTH_SHORT).show()
                false
            }
            sheetGoalBinding.goalCategory.text.toString().isEmpty() -> {
                sheetGoalBinding.goalCategory.error = getString(R.string.error_empty_field)
                false
            }
            else -> true
        }
    }

    private fun showTransactionBottomSheet() {
        val dialog = BottomSheetDialog(this, R.style.BottomSheetDialog).apply {
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
            val body = RepeatedTransactionRequest(
                value = sheetTransactionBinding.etValue.text.toString().toDouble(),
                category = sheetTransactionBinding.transactionCategory.text.toString(),
                description = sheetTransactionBinding.etDescription.text.toString(),
                type = type
            )

            Rest.getRepeatedTransactionInstance().create("Bearer $token", body).enqueue(object : Callback<Unit> {
                override fun onResponse(
                    call: Call<Unit>,
                    response: Response<Unit>
                ) {
                    when(response.code()){
                        201 -> {
                            Toast.makeText(baseContext, R.string.created_transaction, Toast.LENGTH_SHORT).show()
                        }
                        else -> {
                            Toast.makeText(baseContext, R.string.register_error, Toast.LENGTH_SHORT).show()
                        }
                    }
                }

                override fun onFailure(call: Call<Unit>, t: Throwable) {
                    Toast.makeText(baseContext, R.string.connection_error, Toast.LENGTH_SHORT).show()
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
                Toast.makeText(baseContext, R.string.error_transaction_type, Toast.LENGTH_SHORT).show()
                false
            }
            else -> true
        }
    }

    private fun changeColor(btn: MaterialButton) {
        if(btn.strokeColor.equals(getColorStateList(R.color.green_balancefy))) {
            btn.strokeColor = getColorStateList(R.color.grey).withAlpha(24)
            btn.setTextColor(getColor(R.color.grey))
        } else {
            btn.strokeColor = getColorStateList(R.color.green_balancefy)
            btn.setTextColor(getColor(R.color.green_balancefy))
        }
    }

    private fun showPostBottomSheet() {
        val dialog = BottomSheetDialog(this, R.style.BottomSheetDialog).apply {
            window?.setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_ADJUST_RESIZE)
        }

        dialog.behavior.skipCollapsed = true
        dialog.behavior.state = BottomSheetBehavior.STATE_EXPANDED

        sheetPostBottomSheetBinding = PostBottomSheetBinding.inflate(layoutInflater, null, false)

        dialog.setContentView(sheetPostBottomSheetBinding.root)
        dialog.show()

        sheetPostBottomSheetBinding.btnCreate.setOnClickListener {
            if(createPost()) {
                dialog.dismiss()
            }
        }
    }

    private fun createPost(): Boolean {
        if(validatePostFields()) {
            val body = PostRequest(
                title = sheetPostBottomSheetBinding.etTitle.text.toString(),
                content = sheetPostBottomSheetBinding.etTitle.text.toString()
            )

            Rest.getPostInstance().create("Bearer $token", body).enqueue(object : Callback<Unit> {
                override fun onResponse(
                    call: Call<Unit>,
                    response: Response<Unit>
                ) {
                    when(response.code()){
                        201 -> {
                            Toast.makeText(baseContext, R.string.created_post, Toast.LENGTH_SHORT).show()
                        }
                        else -> {
                            Toast.makeText(baseContext, R.string.register_error, Toast.LENGTH_SHORT).show()
                        }
                    }
                }

                override fun onFailure(call: Call<Unit>, t: Throwable) {
                    Toast.makeText(baseContext, R.string.connection_error, Toast.LENGTH_SHORT).show()
                }
            })

            return true
        }

        return false
    }

    private fun validatePostFields(): Boolean {
        return when {
            sheetPostBottomSheetBinding.etTitle.text.toString().isEmpty() -> {
                sheetPostBottomSheetBinding.etTitle.error = getString(R.string.error_empty_field)
                false
            }
            sheetPostBottomSheetBinding.etDescription.text.toString().isEmpty() -> {
                sheetPostBottomSheetBinding.etDescription.error = getString(R.string.error_empty_field)
                false
            }
            else -> true
        }
    }

    private fun logOut() {
        val editor = preferences.edit()
        editor.putString("token", null)
        editor.putString("avatar", null)
        editor.putString("nameUser", null)
        editor.apply()
        startActivity(Intent(this, IntroActivity::class.java))
    }
}