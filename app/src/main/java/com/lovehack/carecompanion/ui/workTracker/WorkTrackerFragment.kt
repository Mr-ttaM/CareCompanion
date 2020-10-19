package com.lovehack.carecompanion.ui.workTracker

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import android.widget.Spinner
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import com.lovehack.carecompanion.R

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
        return root
    }

}
