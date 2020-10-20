package com.lovehack.carecompanion.ui.workTracker

import android.os.Bundle
import android.os.CountDownTimer
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import android.widget.Spinner
import android.widget.Button
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProviders
import androidx.work.ExistingPeriodicWorkPolicy
import androidx.work.OneTimeWorkRequestBuilder
import androidx.work.PeriodicWorkRequestBuilder
import androidx.work.WorkManager
import com.lovehack.carecompanion.R
import com.lovehack.carecompanion.reminders.ReminderManager
import com.lovehack.carecompanion.reminders.ReminderWorker
import kotlinx.android.synthetic.main.fragment_work_tracker.*
import java.util.concurrent.TimeUnit

class WorkTrackerFragment : Fragment() {

    private lateinit var workTrackerViewModel: WorkTrackerViewModel
    private var isStopped : Boolean = true
    private lateinit var timer: CountDownTimer

    override fun onCreateView(
            inflater: LayoutInflater,
            container: ViewGroup?,
            savedInstanceState: Bundle?
    ): View? {
        workTrackerViewModel =
                ViewModelProviders.of(this).get(WorkTrackerViewModel::class.java)
        val root = inflater.inflate(R.layout.fragment_work_tracker, container, false)
        val spinner : Spinner = root.findViewById(R.id.break_time_spinner)
        ArrayAdapter.createFromResource(
            root.context,
            R.array.break_time_array,
            android.R.layout.simple_spinner_item
        ).also { adapter ->
            // Specify the layout to use when the list of choices appears
            adapter.setDropDownViewResource(R.layout.spinner_style)
            // Apply the adapter to the spinner
            spinner.adapter = adapter
        }

        val breakButton : Button = root.findViewById(R.id.break_button)
        breakButton.setOnClickListener {
            val workRequest = OneTimeWorkRequestBuilder<ReminderWorker>()
                .addTag("meal")
                .setInitialDelay(3, TimeUnit.SECONDS)
                .build()
            WorkManager.getInstance(requireView().context).enqueue(workRequest)

            if(isStopped) {
                isStopped = false
                Toast.makeText(root.context, "Is no longer stopped", Toast.LENGTH_SHORT).show()
                break_button.text = getString(R.string.break_reminder_button_text_stop)
            } else {
                isStopped = true
                Toast.makeText(root.context, "Is no longer running", Toast.LENGTH_SHORT).show()
                break_button.text = getString(R.string.break_reminder_button_text_start)            }
        }

        return root
    }
}
