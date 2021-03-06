package com.lovehack.carecompanion.ui.workTracker

import android.os.Bundle
import android.os.CountDownTimer
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.*
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProviders
import com.lovehack.carecompanion.R
import kotlinx.android.synthetic.main.fragment_work_tracker.*

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

        val time  = "10"
        time_in_milli_seconds = time.toLong() *60000L

        countdown_timer = object : CountDownTimer(time_in_milli_seconds, 1000) {
            override fun onFinish() {
                countdown_timer.cancel()
                break_button.performClick()
            }

            override fun onTick(p0: Long) {
                time_in_milli_seconds = p0
                updateTextUI()
            }
        }

        val breakTimeTextView1 : TextView = root.findViewById(R.id.break_textview_1)
        val breakTimeTextView2 : TextView = root.findViewById(R.id.break_textview_2)
        breakTimeUntilTextView = root.findViewById(R.id.break_time_until_next)
        breakTimeGoingTextView = root.findViewById(R.id.break_time_going)

        val breakButton : Button = root.findViewById(R.id.break_button)
        breakButton.setOnClickListener {

            if(isStopped) {
                isStopped = false
                breakTimeTextView1.visibility = View.INVISIBLE
                breakTimeTextView2.visibility = View.INVISIBLE
                spinner.visibility = View.INVISIBLE
                breakTimeGoingTextView.visibility = View.VISIBLE
                breakTimeUntilTextView.visibility = View.VISIBLE
                breakTimeGoingTextView.text = getString(R.string.break_reminder_going_text, 2, 34)
                break_button.text = getString(R.string.break_reminder_button_text_stop)
                startTimer()
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

    private fun startTimer() {
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
