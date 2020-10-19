package com.lovehack.carecompanion.helpers

import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.os.AsyncTask
import android.widget.ImageView
import java.net.URL

internal class AsyncTaskLoadImage(imageView: ImageView) :
    AsyncTask<Any?, Void?, Bitmap?>() {
    private val imageView: ImageView?
    private val path: String

    override fun doInBackground(vararg params: Any?): Bitmap? {
        val url = URL(path)
        val extension = url.path.split(".").last()
        println("Loading image of type $extension")
        return BitmapFactory.decodeStream(url.openConnection().getInputStream())
    }

    override fun onPreExecute() {
        super.onPreExecute()
        // TODO loading spinner?
        println("onPreExecute...")
    }

    override fun onPostExecute(result: Bitmap?) {
        // TODO add resilience / error doggo etc
        if (imageView!!.tag.toString() != path) {
            return
        }
        if (result != null) {
            imageView.setImageBitmap(result)
        }
    }

    init {
        this.imageView = imageView
        path = imageView.tag.toString()
    }
}