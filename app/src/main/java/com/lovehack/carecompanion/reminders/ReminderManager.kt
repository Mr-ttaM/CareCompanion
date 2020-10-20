package com.lovehack.carecompanion.reminders

import android.app.AlarmManager
import android.app.PendingIntent
import android.content.Context
import android.content.Intent
import com.lovehack.carecompanion.enums.MealTime
import java.util.*

const val bedtimeAlarmId = 100
const val mealtimeAlarmId1 = 101
const val mealtimeAlarmId2 = 102
const val mealtimeAlarmId3 = 103
const val waterReminderId = 104


class ReminderManager() {

    fun setUpBedtimeReminder(context: Context, bedHour: Int, bedMinute: Int) {
        val time = Calendar.getInstance().apply {
            timeInMillis = System.currentTimeMillis()
            set(Calendar.HOUR_OF_DAY, bedHour)
            set(Calendar.MINUTE, bedMinute)
        }

        setAlarm(context, time, "bedtime", bedtimeAlarmId)

//        val intent = Intent(context, ReminderReceiver::class.java).let {
//            it.action = "bedtime"
//            PendingIntent.getBroadcast(context, bedtimeAlarmId, it, PendingIntent.FLAG_UPDATE_CURRENT)
//        }
//
//        val alarmManager: AlarmManager = context.getSystemService(Context.ALARM_SERVICE) as AlarmManager
//        alarmManager.setRepeating(AlarmManager.RTC_WAKEUP, time.timeInMillis, AlarmManager.INTERVAL_DAY, intent)
    }

    fun removeBedtimeReminder(context: Context) {
        cancelAlarm(context, bedtimeAlarmId)
    }

    fun setUpMealTimeReminders(context: Context, meal: MealTime, hour: Int, minute: Int) {
        val mealtimeAlarmId = when(meal) {
            MealTime.BREAKFAST -> mealtimeAlarmId1
            MealTime.LUNCH -> mealtimeAlarmId2
            MealTime.DINNER -> mealtimeAlarmId3
        }

        val time = Calendar.getInstance().apply {
            timeInMillis = System.currentTimeMillis()
            set(Calendar.HOUR_OF_DAY, hour)
            set(Calendar.MINUTE, minute)
        }

        setAlarm(context, time, "meal", bedtimeAlarmId)

//        val intent = Intent(context, ReminderReceiver::class.java).let {
//            it.action = "meal"
//            PendingIntent.getBroadcast(context, mealtimeAlarmId, it, PendingIntent.FLAG_UPDATE_CURRENT)
//        }
//
//        val alarmManager: AlarmManager = context.getSystemService(Context.ALARM_SERVICE) as AlarmManager
//        alarmManager.setRepeating(AlarmManager.RTC_WAKEUP, time.timeInMillis, AlarmManager.INTERVAL_DAY, intent)
    }

    fun removeMealtimeAlarms(context: Context) {
        for (id in listOf<Int>(mealtimeAlarmId1, mealtimeAlarmId2, mealtimeAlarmId3)) {
            cancelAlarm(context, id)
        }
    }

    fun setAlarm(context: Context, time: Calendar, action: String, id: Int) {
        val intent = Intent(context, ReminderReceiver::class.java).let {
            it.action = action
            PendingIntent.getBroadcast(context, id, it, PendingIntent.FLAG_UPDATE_CURRENT)
        }

        val alarmManager: AlarmManager = context.getSystemService(Context.ALARM_SERVICE) as AlarmManager
        alarmManager.setRepeating(AlarmManager.RTC_WAKEUP, time.timeInMillis, AlarmManager.INTERVAL_DAY, intent)
//        alarmManager.setE
    }

    private fun cancelAlarm(context: Context, id: Int) {
        val intent = Intent(context, ReminderReceiver::class.java).let {
            PendingIntent.getBroadcast(context, id, it, PendingIntent.FLAG_UPDATE_CURRENT)
        }
        val alarmManager: AlarmManager = context.getSystemService(Context.ALARM_SERVICE) as AlarmManager
        alarmManager.cancel(intent)
    }
}