package com.lovehack.carecompanion.reminders

import android.app.Notification
import android.app.NotificationManager
import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.util.Log
import android.widget.Toast
import com.lovehack.carecompanion.R
import kotlin.random.Random

class ReminderReceiver: BroadcastReceiver() {
    override fun onReceive(context: Context?, intent: Intent?) {
        var title: String = "Notification title"
        var body: String = "This is the notification body"

        if (context != null) {
            val randomNum = Random.nextInt(from = 0, until = 3)
            val titleId = "${intent?.action}_reminder_title"
            title = context.getString(context.resources.getIdentifier(titleId, "string", context.packageName))
            val body_id = "${intent?.action}_reminder_body_$randomNum"
            body = context.getString(context.resources.getIdentifier(body_id, "string", context.packageName))
        }

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

// How to trigger notification from somewhere else
//            val intent = Intent(view.context, ReminderReceiver::class.java)
//            intent.action = "meal"
//            view.context.sendBroadcast(intent)