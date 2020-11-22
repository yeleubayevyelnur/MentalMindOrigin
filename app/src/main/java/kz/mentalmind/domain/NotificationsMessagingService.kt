package kz.mentalmind.domain

import android.app.NotificationChannel
import android.app.NotificationManager
import android.content.Context
import android.os.Build
import android.util.Log
import androidx.core.app.NotificationCompat
import androidx.core.content.ContextCompat
import com.google.firebase.messaging.RemoteMessage
import com.pusher.pushnotifications.fcm.MessagingService
import kz.mentalmind.R

class NotificationsMessagingService : MessagingService() {

    override fun onMessageReceived(remoteMessage: RemoteMessage) {
        Log.d(
            "yel",
            "remote message from NotificationsMessagingService ${remoteMessage.data.values}"
        )
        val notificationBuilder = NotificationCompat.Builder(this, channelId)
            .setContentTitle(getString(R.string.app_name))
            .setPriority(NotificationCompat.PRIORITY_HIGH)
            .setColor(ContextCompat.getColor(this, R.color.gray))
            .setSmallIcon(R.drawable.ic_menatal_mind)
            .setAutoCancel(true)

        val notificationManager =
            getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            val adminChannel =
                NotificationChannel(channelId, channelName, NotificationManager.IMPORTANCE_HIGH)
            notificationManager.createNotificationChannel(adminChannel)
        }
    }


    companion object {
        const val notificationId = 12321
        const val channelId = "CHANNEL_ID"
        const val channelName = "IMPORTANT_NOTIFICATION_CHANNEL"
    }
}