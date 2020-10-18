package com.lovehack.carecompanion.ui.workTracker

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

class WorkTrackerViewModel : ViewModel() {

    private val _text = MutableLiveData<String>().apply {
        value = "This is the work tracker Fragment"
    }
    val text: LiveData<String> = _text
}