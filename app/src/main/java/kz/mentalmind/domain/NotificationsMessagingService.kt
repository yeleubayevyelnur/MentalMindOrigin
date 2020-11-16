package kz.mentalmind.domain

import android.util.Log
import com.google.firebase.messaging.RemoteMessage
import com.pusher.pushnotifications.fcm.MessagingService

class NotificationsMessagingService : MessagingService() {

    override fun onMessageReceived(remoteMessage: RemoteMessage) {
        Log.d("yel", "remote message from NotificationsMessagingService ${remoteMessage.data.values}")
    }
}