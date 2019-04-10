package com.project.hyperfood.common.utils

import android.app.DatePickerDialog
import android.app.Dialog
import android.app.DialogFragment
import android.os.Bundle
import android.widget.DatePicker

import java.util.Calendar

class DatePickerFragment : DialogFragment(), DatePickerDialog.OnDateSetListener {

    private var listener: OnDateSteListener? = null

    fun setListener(listener: OnDateSteListener?) {
        this.listener = listener
    }

    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
        val c = Calendar.getInstance()
        val year = c.get(Calendar.YEAR)
        val month = c.get(Calendar.MONTH)
        val day = c.get(Calendar.DAY_OF_MONTH)
        var picker = DatePickerDialog(activity, this, year, month, day)
        return picker
    }

    override fun onDateSet(view: DatePicker, year: Int, month: Int, day: Int) {
        listener!!.onDateSet(String.format("%02d-%02d-%d", day, month+1, year))
    }

    interface OnDateSteListener {
        fun onDateSet(date: String?)
    }
}