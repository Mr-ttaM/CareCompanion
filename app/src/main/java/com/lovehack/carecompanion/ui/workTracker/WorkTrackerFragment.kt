package com.lovehack.carecompanion.ui.workTracker

import android.app.PendingIntent
import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import android.widget.Spinner
import android.widget.Button
import android.widget.TextView
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import com.lovehack.carecompanion.R
import com.lovehack.carecompanion.reminders.ReminderReceiver

class WorkTrackerFragment : Fragment() {

    private lateinit var workTrackerViewModel: WorkTrackerViewModel
    private var isStopped : Boolean = true

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

        val button:Button = root.findViewById(R.id.button)
        button.setOnClickListener {view ->
            val intent = Intent(view.context, ReminderReceiver::class.java).let {
                PendingIntent.getBroadcast(view.context, 0, it, 0)
            }
            intent.send()
        }

        return root
    }

    fun sendNotification(view: View) {
        Toast.makeText(view.context, "sendNotification called", Toast.LENGTH_SHORT).show()

        val intent = Intent(view.context, ReminderReceiver::class.java)
        intent.putExtra("title", "Notification title")
        intent.putExtra("body", "This is the message for the notification")
        view.context.sendBroadcast(intent)
//        intent.putExtra("title", "Notification title")
//        intent.putExtra("body", "This is the message for the notification")
//        val pending = PendingIntent.getActivity(view.context, 0, intent, 0)
//        val intent = Intent(view.context, ReminderReceiver::class.java)
//            .let {
//            it.putExtra("title", "Notification title")
//            it.putExtra("body", "This is the message for the notification")
//            PendingIntent.getBroadcast(view.context, 0, it, 0)
//        }
//        view.context.sendBroadcast(intent)
    }

}
