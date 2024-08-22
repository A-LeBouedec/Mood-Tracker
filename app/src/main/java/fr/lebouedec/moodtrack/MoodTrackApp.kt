package fr.lebouedec.moodtrack

import android.app.Application
import android.app.NotificationChannel
import android.app.NotificationManager
import androidx.core.content.ContextCompat
import fr.lebouedec.moodtrack.ui.notification.RemindersManager

class MoodTrackApp : Application() {
    override fun onCreate() {
        super.onCreate()
        createNotificationsChannels()
        RemindersManager.startReminder(this)
    }

    private fun createNotificationsChannels() {
        val channel = NotificationChannel(
            "notification_channel_id",
            "Notification name",
            NotificationManager.IMPORTANCE_HIGH
        )
        ContextCompat.getSystemService(this, NotificationManager::class.java)
            ?.createNotificationChannel(channel)
    }
}
