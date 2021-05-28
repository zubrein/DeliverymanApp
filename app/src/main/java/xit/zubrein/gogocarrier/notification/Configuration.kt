package xit.zubrein.gogocarrier.notification

class Configuration {

    companion object {
        // global topic to receive app wide push notifications
        val TOPIC_GLOBAL = "global_carrier"

        // broadcast receiver intent filters
        val REGISTRATION_COMPLETE = "registrationComplete"
        val PUSH_NOTIFICATION = "pushNotification"

        // id to handle the notification in the notification tray
        const val NOTIFICATION_ID = 100
        const val NOTIFICATION_ID_BIG_IMAGE = 101

        val SHARED_PREF = "ah_firebase"
    }

}