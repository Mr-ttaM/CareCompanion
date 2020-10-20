package com.lovehack.carecompanion.ui.workTracker

import android.os.Bundle
import android.os.CountDownTimer
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.*
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProviders
import androidx.work.ExistingPeriodicWorkPolicy
import androidx.work.OneTimeWorkRequestBuilder
import androidx.work.PeriodicWorkRequestBuilder
import androidx.work.WorkManager
import com.lovehack.carecompanion.R
import com.lovehack.carecompanion.reminders.ReminderManager
import com.lovehack.carecompanion.reminders.ReminderWorker
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.android.synthetic.main.fragment_work_tracker.*
import java.util.concurrent.TimeUnit

class WorkTrackerFragment : Fragment() {

    private lateinit var workTrackerViewModel: WorkTrackerViewModel
    private lateinit var breakTimeGoingTextView : TextView
    private lateinit var breakTimeUntilTextView : TextView
    private var isStopped : Boolean = true
    var START_MILLI_SECONDS = 60000L
    lateinit var countdown_timer: CountDownTimer
    var time_in_milli_seconds = 0L

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

        val breakTimeTextView1 : TextView = root.findViewById(R.id.break_textview_1)
        val breakTimeTextView2 : TextView = root.findViewById(R.id.break_textview_2)
        breakTimeUntilTextView = root.findViewById(R.id.break_time_until_next)
        breakTimeGoingTextView = root.findViewById(R.id.break_time_going)

        val breakButton : Button = root.findViewById(R.id.break_button)
        breakButton.setOnClickListener {
//            val workRequest = OneTimeWorkRequestBuilder<ReminderWorker>()
//                .addTag("meal")
//                .setInitialDelay(3, TimeUnit.SECONDS)
//                .build()
//            WorkManager.getInstance(requireView().context).enqueue(workRequest)

            if(isStopped) {
                isStopped = false
                breakTimeTextView1.visibility = View.INVISIBLE
                breakTimeTextView2.visibility = View.INVISIBLE
                spinner.visibility = View.INVISIBLE
                breakTimeGoingTextView.visibility = View.VISIBLE
                breakTimeUntilTextView.visibility = View.VISIBLE
                breakTimeGoingTextView.text = getString(R.string.break_reminder_going_text, 2, 34)
                break_button.text = getString(R.string.break_reminder_button_text_stop)
                val time  = "1"
                time_in_milli_seconds = time.toLong() *60000L
                startTimer(time_in_milli_seconds)
            } else {
                isStopped = true
                breakTimeTextView1.visibility = View.VISIBLE
                breakTimeTextView2.visibility = View.VISIBLE
                spinner.visibility = View.VISIBLE
                breakTimeGoingTextView.visibility = View.INVISIBLE
                breakTimeUntilTextView.visibility = View.INVISIBLE
                countdown_timer.cancel()
                break_button.text = getString(R.string.break_reminder_button_text_start)            }
        }

        return root
    }

    override fun onPause() {
        super.onPause()
        countdown_timer.cancel()
    }

    override fun onDestroy() {
        super.onDestroy()
        countdown_timer.cancel()
    }

    override fun onStop() {
        super.onStop()
        countdown_timer.cancel()
    }

    private fun startTimer(time_in_seconds: Long) {
        countdown_timer = object : CountDownTimer(time_in_seconds, 1000) {
            override fun onFinish() {
//                loadConfetti()
                countdown_timer.cancel()
                break_button.performClick()
            }

            override fun onTick(p0: Long) {
                time_in_milli_seconds = p0
                updateTextUI()
            }
        }
        countdown_timer.start()
        isStopped = false

    }

    private fun resetTimer() {
        time_in_milli_seconds = START_MILLI_SECONDS
        updateTextUI()
    }

    private fun updateTextUI() {
        val minute = (time_in_milli_seconds / 1000) / 60
        val seconds = (time_in_milli_seconds / 1000) % 60

        breakTimeUntilTextView.text = getString(R.string.break_reminder_until_next_text, minute, seconds)
    }

}
