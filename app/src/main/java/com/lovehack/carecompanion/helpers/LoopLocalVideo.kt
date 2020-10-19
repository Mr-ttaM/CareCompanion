package com.lovehack.carecompanion.helpers

import android.net.Uri
import android.widget.VideoView
import com.lovehack.carecompanion.BuildConfig

class LoopLocalVideo(videoView: VideoView, resourceId: Int) {
    private val videoView: VideoView?
    private val uri: Uri?

    init {
        this.videoView = videoView
        uri = Uri.parse("android.resource://" + BuildConfig.APPLICATION_ID + "/" + resourceId)
        videoView.setOnPreparedListener { mp -> mp.isLooping = true }
        videoView.setVideoURI(uri)
        videoView.start()
    }
}