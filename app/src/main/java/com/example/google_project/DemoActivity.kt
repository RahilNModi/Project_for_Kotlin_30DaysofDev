package com.example.google_project

import android.app.NotificationChannel
import android.app.NotificationManager
import android.app.PendingIntent
import android.content.Context
import android.content.Intent
import android.graphics.BitmapFactory
import android.graphics.Color
import android.media.RingtoneManager
import android.os.Build
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.provider.Settings
import android.widget.Toast
import androidx.core.app.NotificationCompat
import androidx.core.app.NotificationManagerCompat
import androidx.core.app.RemoteInput
import androidx.core.content.ContextCompat
import kotlinx.android.synthetic.main.activity_demo.*
import java.net.URI

class DemoActivity : AppCompatActivity() {
    private val CHANNEL_ID = "com.example.google_project.channel1"
    private val notificationId = 41
    private val KEY_REPLY = "key_reply"

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_demo)
        createNotificationChannel()
        btn_log_out.setOnClickListener {
            Toast.makeText(this,"Log Out Successful!!!",Toast.LENGTH_SHORT).show()
            startActivity(Intent(this,MainActivity::class.java))
            finish()
        }

        btn_notify.setOnClickListener {
            //Toast.makeText(this, "Notification Sent....", Toast.LENGTH_SHORT).show()
            display_notification()

        }
    }

    private fun display_notification() {
        val intent = Intent(this, SecondDemo::class.java)
        val pendingIntent: PendingIntent = PendingIntent.getActivity(this, 0, intent, PendingIntent.FLAG_UPDATE_CURRENT)
        val logoImage = BitmapFactory.decodeResource(
            applicationContext.resources,
            R.drawable.logo
        )
        val bigPicStyle = NotificationCompat.BigPictureStyle()
            .bigPicture(logoImage)
            .bigLargeIcon(null)
        //reply action
        val remoteInput: RemoteInput = RemoteInput.Builder(KEY_REPLY).run {
            setLabel("Insert your feedback here!!!")
            build()
        }
        val remoteAction: NotificationCompat.Action = NotificationCompat.Action.Builder(
            0, "Reply", pendingIntent).addRemoteInput(remoteInput).build()

        //action button1
        val intent2 = Intent(this, DetailsActivity::class.java)
        val pendingIntent2: PendingIntent = PendingIntent.getActivity(this, 0, intent2, PendingIntent.FLAG_UPDATE_CURRENT)
        val action2:NotificationCompat.Action = NotificationCompat.Action.Builder(0,"Details",pendingIntent2).build()

        //action button2
        val intent3 = Intent(this, SettingsActivity::class.java)
        val pendingIntent3: PendingIntent = PendingIntent.getActivity(this, 0, intent3, PendingIntent.FLAG_UPDATE_CURRENT)
        val action3: NotificationCompat.Action = NotificationCompat.Action.Builder(0,"Settings",pendingIntent3).build()

        val builder = NotificationCompat.Builder(this, CHANNEL_ID)
            .setSmallIcon(android.R.drawable.sym_action_chat)
            .setContentTitle("Project_Notification")
            .setContentText("Guys, please give feedback about my app's login??")
            .setColor(Color.BLUE)
            .setStyle(bigPicStyle)
            .setLargeIcon(logoImage)
            .setContentIntent(pendingIntent)
            .setPriority(NotificationCompat.PRIORITY_DEFAULT)
            // Set the intent that will fire when the user taps the notification
            .addAction(action2)
            .addAction(action3)
            .addAction(remoteAction)
            .setAutoCancel(true)
        with(NotificationManagerCompat.from(this)) {
            // notificationId is a unique int for each notification that you must define
            notify(notificationId, builder.build())
        }
    }

    private fun createNotificationChannel() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            val name = getString(R.string.channel_name)
            val descriptionText = getString(R.string.channel_description)
            val importance = NotificationManager.IMPORTANCE_DEFAULT
            val channel = NotificationChannel(CHANNEL_ID, name, importance).apply {
                description = descriptionText
            }
            // Register the channel with the system
            val notificationManager: NotificationManager =
                getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager
            notificationManager.createNotificationChannel(channel)
        }
    }
}

