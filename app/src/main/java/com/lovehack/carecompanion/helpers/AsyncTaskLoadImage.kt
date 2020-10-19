package com.lovehack.carecompanion.helpers

import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.os.AsyncTask
import android.view.View
import android.widget.ImageView
import java.io.BufferedInputStream
import java.net.URL

internal class AsyncTaskLoadImage(imv: ImageView) :
    AsyncTask<Any?, Void?, Bitmap?>() {
    private val imv: ImageView?
    private val path: String

    override fun doInBackground(vararg params: Any?): Bitmap? {
        val url = URL(path)
        val extension = url.path.split(".").last()
        println("Loading image of type $extension")
        return BitmapFactory.decodeStream(url.openConnection().getInputStream())
    }

    override fun onPostExecute(result: Bitmap?) {
        if (imv!!.tag.toString() != path) {
            return
        }
        if (result != null) {
            imv.setImageBitmap(result)
        }
    }

    init {
        this.imv = imv
        path = imv.tag.toString()
    }
}

