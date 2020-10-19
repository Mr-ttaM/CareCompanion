package com.lovehack.carecompanion.reminders

import android.app.Notification
import android.app.NotificationManager
import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.util.Log
import android.widget.Toast
import com.lovehack.carecompanion.R

class ReminderReceiver: BroadcastReceiver() {
    override fun onReceive(context: Context?, intent: Intent?) {
        Toast.makeText(context, "Received message ${intent?.action}", Toast.LENGTH_SHORT).show()
        val title: String? = intent?.getStringExtra("title")
        val body: String? = intent?.getStringExtra("body")
        Toast.makeText(context, "Received title $title", Toast.LENGTH_SHORT).show()


        val notification = Notification.Builder(context, Notification.CATEGORY_REMINDER).run {
            setSmallIcon(R.drawable.ic_menu_camera)
            setContentTitle(title)
            setContentText(body)
            build()
        }

        val manager = context?.getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager
        manager.notify(0, notification)
    }
}