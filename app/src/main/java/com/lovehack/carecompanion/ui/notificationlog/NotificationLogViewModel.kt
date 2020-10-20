package com.lovehack.carecompanion.ui.notificationlog

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

class NotificationLogViewModel : ViewModel() {

    private val _text = MutableLiveData<String>().apply {
        value = "This is the about Fragment"
    }
    val text: LiveData<String> = _text
}