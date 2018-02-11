package com.example.davegilbier.alarmclockstopwatch

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent


class AlarmReceiver : BroadcastReceiver() {

    override fun onReceive(context: Context, intent: Intent) {
        val state = intent.extras!!.getString("extra")

        val serviceIntent = Intent(context, RingtonePlayingService::class.java)
        serviceIntent.putExtra("extra", state)
        context.startService(serviceIntent)
    }
}
