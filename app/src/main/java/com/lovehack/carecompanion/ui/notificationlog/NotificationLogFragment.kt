package com.lovehack.carecompanion.ui.notificationlog

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProviders
import com.lovehack.carecompanion.R


class NotificationLogFragment : Fragment() {

    private lateinit var notificationLogViewModel: NotificationLogViewModel

    override fun onCreateView(
            inflater: LayoutInflater,
            container: ViewGroup?,
            savedInstanceState: Bundle?
    ): View? {
        notificationLogViewModel =
                ViewModelProviders.of(this).get(NotificationLogViewModel::class.java)
        val root = inflater.inflate(R.layout.fragment_notification_log, container, false)

        val imageView : ImageView = root.findViewById(R.id.notification_log_image)
//        imageView.setImageResource(R.drawable.notification_log.png)

        return root
    }
}
