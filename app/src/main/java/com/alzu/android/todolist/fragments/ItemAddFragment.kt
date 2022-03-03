package com.alzu.android.todolist.fragments


import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.DialogFragment
import androidx.fragment.app.activityViewModels
import androidx.fragment.app.commit
import androidx.fragment.app.replace
import androidx.lifecycle.LifecycleOwner
import com.alzu.android.todolist.Item
import com.alzu.android.todolist.ItemViewModel
import com.alzu.android.todolist.R
import com.alzu.android.todolist.databinding.FragmentItemAddBinding
import com.google.android.material.bottomsheet.BottomSheetDialogFragment
import com.google.android.material.datepicker.MaterialDatePicker
import com.google.android.material.timepicker.MaterialTimePicker
import com.google.android.material.timepicker.TimeFormat
import java.text.SimpleDateFormat

import java.util.*


class ItemAddFragment: BottomSheetDialogFragment() {
    private lateinit var binding: FragmentItemAddBinding
    private val itemsList: ItemViewModel by activityViewModels()
    private var hour: Int = 0
    private var min: Int = 0
    private var date: Long = 0L
    private var btnDateText: String = ""
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setStyle(DialogFragment.STYLE_NORMAL, R.style.DialogStyle)
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentItemAddBinding.inflate(layoutInflater)
        binding.titleTE.requestFocus()
        itemsList.itemsListToDo.observe(activity as LifecycleOwner) {

        }
        binding.saveAddBtn.setOnClickListener {
            createItem()
        }
        binding.calendarAddBtn.setOnClickListener {
            openTimeDialog()

        }
        return binding.root
    }

    private fun openTimeDialog() {
        val timePickerDialog  = MaterialTimePicker.Builder().setTimeFormat(
            TimeFormat.CLOCK_24H)
            .build()

        timePickerDialog.addOnPositiveButtonClickListener {
            hour = timePickerDialog.hour
            min = timePickerDialog.minute
            openDateDialog()
        }
        timePickerDialog.show(parentFragmentManager, "time_tag")
    }

    private fun openDateDialog() {
        val datePickerDialog = MaterialDatePicker.Builder.datePicker()
            .setSelection(MaterialDatePicker.todayInUtcMilliseconds())
            .build()

        datePickerDialog.addOnPositiveButtonClickListener {
            datePickerDialog.selection.also {
                if (it != null) {
                    date = it
                }
            }
            if(date != 0L ) btnDateText = dateStringBuilder(date,hour, min)
            binding.calendarAddBtn.text = btnDateText
        }
        datePickerDialog.show(parentFragmentManager,"date_tag")
    }

    private fun createItem(){
        val freshTitle = binding.titleTE.editText?.text.toString()
        val freshDescription = binding.descriptionTE.editText?.text.toString()
        if (freshTitle.isNotEmpty() || freshTitle.isNotBlank()) {
            itemsList.itemsListToDo.value?.
            add(Item(freshTitle, freshDescription, btnDateText, false))
            parentFragmentManager.commit {
                setReorderingAllowed(true)
                replace<ItemListFragment>(R.id.fragmentCV)
            }
        }
        this.dismiss()
    }

    private fun dateStringBuilder(date: Long, hour: Int, min: Int): String {
        val formatter = SimpleDateFormat("EEE, d MMM")
        val modDate = Date(date)
        val curDate = Date()
        var outDate = ""
        outDate = if (formatter.format(modDate) == formatter.format(curDate)) "today"
        else formatter.format(modDate)
        return "$outDate, $hour:$min"
    }
}