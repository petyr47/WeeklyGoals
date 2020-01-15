package com.inits.ng.weeklygoals.goals

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.Observer
import com.google.android.material.datepicker.MaterialDatePicker
import com.google.android.material.snackbar.Snackbar
import com.inits.ng.weeklygoals.R
import com.inits.ng.weeklygoals.databinding.LayoutInputGoalBinding
import kotlinx.android.synthetic.main.layout_input_goal.*
import org.koin.android.viewmodel.ext.android.viewModel

class InputActivity : AppCompatActivity() {

    private val  inputViewModel : InputViewModel by viewModel()
    private lateinit var binding : LayoutInputGoalBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.layout_input_goal)

        binding = DataBindingUtil.setContentView(this, R.layout.layout_input_goal)
        binding.lifecycleOwner = this
        binding.viewModel = inputViewModel

        time_input.setOnFocusChangeListener { _, hasFocus ->
            if (hasFocus){
                makePicker()
            }
        }
        back_image_btn.setOnClickListener { onBackPressed() }
        time_input.setOnClickListener { makePicker() }
        setupObservers()

    }

    private fun makePicker(){
        val picker = MaterialDatePicker.Builder.dateRangePicker().build()
        picker.addOnCancelListener {
            inputViewModel.errorMessage.value = "Date Picker cancelled"
        }
        picker.addOnNegativeButtonClickListener {
            inputViewModel.errorMessage.value ="Date picker negative button clicked"
        }
        picker.addOnPositiveButtonClickListener {
            inputViewModel.startStamp.value = it.first
            inputViewModel.endStamp.value = it.second

            inputViewModel.displayDate.value = picker.headerText
        }
        picker.show(supportFragmentManager, picker.toString())
    }

    private fun setupObservers(){
        inputViewModel.errorMessage.observe(this, Observer {
            it?.let {
                Snackbar.make(add_goal_root, it, Snackbar.LENGTH_LONG).apply {
                    anchorView = extendedFloatingActionButton
                    show()
                }
            }
        })

        inputViewModel.success.observe(this, Observer {
            it?.let {
                if(it){
                    Handler().postDelayed({
                        finish()
                    },1000)
                }
            }
        })
    }
}
