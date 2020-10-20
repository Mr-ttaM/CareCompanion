package com.lovehack.carecompanion.ui.help

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ScrollView
import android.widget.TextView
import android.widget.VideoView
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import com.lovehack.carecompanion.R
import com.lovehack.carecompanion.helpers.LoopLocalVideo

class HelpFragment : Fragment() {

    private lateinit var helpViewModel: HelpViewModel

    override fun onCreateView(
            inflater: LayoutInflater,
            container: ViewGroup?,
            savedInstanceState: Bundle?
    ): View? {
        helpViewModel =
                ViewModelProviders.of(this).get(HelpViewModel::class.java)
        val root = inflater.inflate(R.layout.fragment_help, container, false)

        // load in the videos
        val mealVideoView: VideoView = root.findViewById(R.id.mealVideoView)
        LoopLocalVideo(mealVideoView, R.raw.meal)

        val sleepVideoView: VideoView = root.findViewById(R.id.sleepVideoView)
        LoopLocalVideo(sleepVideoView, R.raw.sleep)

        val drinkVideoView: VideoView = root.findViewById(R.id.drinkVideoView)
        LoopLocalVideo(drinkVideoView, R.raw.drink)
        
        return root
    }
}
