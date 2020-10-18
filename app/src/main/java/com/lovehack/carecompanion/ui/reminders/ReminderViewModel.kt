package com.lovehack.carecompanion.ui.reminders

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

class ReminderViewModel : ViewModel() {

    private val _text = MutableLiveData<String>().apply {
        value = "This is reminders Fragment"
    }
    val text: LiveData<String> = _text
}