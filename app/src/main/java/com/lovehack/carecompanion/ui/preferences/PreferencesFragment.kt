package com.lovehack.carecompanion.ui.preferences

import android.os.Bundle
import android.text.InputType
import androidx.preference.EditTextPreference
import androidx.preference.PreferenceFragmentCompat

import com.lovehack.carecompanion.R

class SettingsFragment : PreferenceFragmentCompat() {
    override fun onCreatePreferences(savedInstanceState: Bundle?, rootKey: String?) {
        setPreferencesFromResource(R.xml.preferences, rootKey)

        findPreference<EditTextPreference>("delayToPick")?.let {
            it.setOnBindEditTextListener { editor ->
                editor.inputType = InputType.TYPE_CLASS_NUMBER
            }
        }
    }
}