package com.lovehack.carecompanion.ui.reminders

import android.annotation.SuppressLint
import android.app.DatePickerDialog
import android.app.TimePickerDialog
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import android.widget.Spinner
import android.widget.TextView
import android.widget.*
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import androidx.work.OneTimeWorkRequestBuilder
import androidx.work.WorkManager
import com.lovehack.carecompanion.R
import com.lovehack.carecompanion.reminders.ReminderWorker
import java.sql.Time
import java.text.SimpleDateFormat
import java.time.LocalTime
import java.util.*
import java.util.*
import java.util.concurrent.TimeUnit

class ReminderFragment : Fragment() {

    private lateinit var reminderViewModel: ReminderViewModel

    @SuppressLint("SimpleDateFormat")
    override fun onCreateView(
            inflater: LayoutInflater,
            container: ViewGroup?,
            savedInstanceState: Bundle?
    ): View? {
        reminderViewModel =
                ViewModelProviders.of(this).get(ReminderViewModel::class.java)
        val root = inflater.inflate(R.layout.fragment_reminders, container, false)

        setUpSwitchListeners(root)


        val waterSpinner : Spinner = root.findViewById(R.id.water_reminder_spinner)
        ArrayAdapter.createFromResource(
            root.context,
            R.array.water_reminder_num_array,
            android.R.layout.simple_spinner_item
        ).also { adapter ->
            // Specify the layout to use when the list of choices appears
            adapter.setDropDownViewResource(R.layout.spinner_style)
            // Apply the adapter to the spinner
            waterSpinner.adapter = adapter
        }

        val breakfastTimePicker : TextView = root.findViewById(R.id.breakfast_time_picker)
        val lunchTimePicker : TextView = root.findViewById(R.id.lunch_time_picker)
        val dinnerTimePicker : TextView = root.findViewById(R.id.dinner_time_picker)
        val wakeUpTimePicker : TextView = root.findViewById(R.id.wake_up_time_picker)
        val bedtimeTimePicker : TextView = root.findViewById(R.id.bedtime_time_picker)

        breakfastTimePicker.setOnClickListener {
            pickTime(breakfastTimePicker, root)
        }

        lunchTimePicker.setOnClickListener {
            pickTime(lunchTimePicker, root)
        }

        dinnerTimePicker.setOnClickListener {
            pickTime(dinnerTimePicker, root)
        }

        wakeUpTimePicker.setOnClickListener {
            pickTime(wakeUpTimePicker, root)
        }

        bedtimeTimePicker.setOnClickListener {
            val cal = Calendar.getInstance()
            val bedtimeSetListener = TimePickerDialog.OnTimeSetListener { _, hour, minute ->
                cal.set(Calendar.HOUR_OF_DAY, hour)
                cal.set(Calendar.MINUTE, minute)
                bedtimeTimePicker.text =
                    SimpleDateFormat(getString(R.string.time_format)).format(cal.time)
                val workRequest = OneTimeWorkRequestBuilder<ReminderWorker>()
                    .addTag("meal")
                    .setInitialDelay(3, TimeUnit.SECONDS)
                    .build()
                WorkManager.getInstance(requireView().context).enqueue(workRequest)
            }
            TimePickerDialog(
                root.context,
                bedtimeSetListener,
                cal.get(Calendar.HOUR_OF_DAY),
                cal.get(Calendar.MINUTE),
                true
            ).show()
        }

        return root
    }

    fun setUpSwitchListeners(root: View) {
        setUpSwitchListener(root.findViewById(R.id.water_reminder_switch), root.findViewById(R.id.water_contents))
        setUpSwitchListener(root.findViewById(R.id.meal_reminder_switch), root.findViewById(R.id.meal_contents))
        setUpSwitchListener(root.findViewById(R.id.sleep_reminder_switch), root.findViewById(R.id.sleep_contents))
    }

    fun setUpSwitchListener(switch: Switch, view: View) {
        switch.setOnClickListener { it as Switch
            view.visibility = if (it.isChecked) View.VISIBLE else View.GONE
        }

        // set initial state
        switch.isChecked = true
        view.visibility = if (switch.isChecked) View.VISIBLE else View.GONE
    }

    private fun pickTime(bedtimeTimePicker: TextView, root: View) {
        val cal = Calendar.getInstance()
        val bedtimeSetListener = TimePickerDialog.OnTimeSetListener { _, hour, minute ->
            cal.set(Calendar.HOUR_OF_DAY, hour)
            cal.set(Calendar.MINUTE, minute)
            bedtimeTimePicker.text =
                SimpleDateFormat(getString(R.string.time_format)).format(cal.time)
        }
        TimePickerDialog(
            root.context,
            bedtimeSetListener,
            cal.get(Calendar.HOUR_OF_DAY),
            cal.get(Calendar.MINUTE),
            true
        ).show()
    }
}
