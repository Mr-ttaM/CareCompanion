package com.lovehack.carecompanion.ui.about

import android.net.Uri
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.VideoView
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProviders
import com.lovehack.carecompanion.BuildConfig
import com.lovehack.carecompanion.R


class AboutFragment : Fragment() {

    private lateinit var aboutViewModel: AboutViewModel

    override fun onCreateView(
            inflater: LayoutInflater,
            container: ViewGroup?,
            savedInstanceState: Bundle?
    ): View? {
        aboutViewModel =
                ViewModelProviders.of(this).get(AboutViewModel::class.java)
        val root = inflater.inflate(R.layout.fragment_about, container, false)
        val videoView: VideoView = root.findViewById(R.id.videoView)
        videoView.setOnPreparedListener { mp -> mp.isLooping = true }
        val uri: Uri = Uri.parse("android.resource://" + BuildConfig.APPLICATION_ID + "/" + R.raw.drink)
        println(uri.path)
        videoView.setVideoURI(uri)
        videoView.start()

        return root
    }
}
