package com.lovehack.carecompanion.reminders

import android.app.Notification
import android.app.NotificationManager
import android.content.Context
import androidx.work.Worker
import androidx.work.WorkerParameters
import com.lovehack.carecompanion.R
import kotlin.random.Random

class ReminderWorker(appContext: Context, workerParams: WorkerParameters) : Worker(appContext, workerParams) {

    override fun doWork(): Result {

        val context = this.applicationContext

        val randomNum = Random.nextInt(from = 0, until = 3)
        val bodyId = "bedtime_reminder_body_$randomNum"
        val body = context.getString(context.resources.getIdentifier(bodyId, "string", context.packageName))

        val notification = Notification.Builder(this.applicationContext, "reminders_channel").run {
            setSmallIcon(R.drawable.ic_bedtime_black)
            setContentTitle(context.getString(R.string.bedtime_reminder_title))
            setContentText(body)
            build()
        }

        val manager = this.applicationContext.getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager
        manager.notify(0, notification)

        return Result.success()
    }

}