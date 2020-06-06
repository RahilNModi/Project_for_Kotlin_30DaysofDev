package com.example.google_project

import android.app.KeyguardManager
import android.app.RemoteInput
import android.graphics.Color
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.core.app.NotificationCompat
import androidx.core.app.NotificationManagerCompat
import kotlinx.android.synthetic.main.activity_second_demo.*

class SecondDemo : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_second_demo)
        receive_input()
    }

    private fun receive_input() {
        val KEY_REPLY = "key_reply"
        val intent = this.intent
        val remoteInput = RemoteInput.getResultsFromIntent(intent)
        if (remoteInput != null) {
            val inputString: String = remoteInput.getCharSequence(KEY_REPLY).toString()
            tv_result.text = inputString
            val CHANNEL_ID = "com.example.google_project.channel1"
            val notificationId = 41

            val replyNotificationManager = NotificationCompat.Builder(this, CHANNEL_ID)
                .setSmallIcon(android.R.drawable.sym_action_chat)
                .setContentText("Hurray!!! Reply Received")
                .setColor(Color.BLUE)

            with(NotificationManagerCompat.from(this)) {
                // notificationId is a unique int for each notification that you must define
                notify(notificationId, replyNotificationManager.build())

            }
        }
    }
}