package com.example.davegilbier.alarmclockstopwatch

import android.app.Notification
import android.app.NotificationManager
import android.app.PendingIntent
import android.app.Service
import android.content.Context
import android.content.Intent
import android.media.MediaPlayer
import android.os.Build
import android.os.IBinder
import android.support.annotation.RequiresApi

import java.util.Random

class RingtonePlayingService : Service() {

    private var isRunning: Boolean = false
    internal lateinit var mMediaPlayer: MediaPlayer
    private var startId: Int = 0

    override fun onBind(intent: Intent): IBinder? {
        return null
    }

    @RequiresApi(api = Build.VERSION_CODES.JELLY_BEAN)
    override fun onStartCommand(intent: Intent, flags: Int, startId: Int): Int {
        var startId: Int

        val mNM = getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager

        val intent1 = Intent(this.applicationContext, MainActivity::class.java)
        val pIntent = PendingIntent.getActivity(this, 0, intent1, 0)

        val mNotify = Notification.Builder(this)
                .setContentTitle("ALARM ALARM ALARM" + "!")
                .setContentText("Alarmmmmmmmmm!")
                .setSmallIcon(R.drawable.ic_action_call)
                .setContentIntent(pIntent)
                .setAutoCancel(true)
                .build()

        val state = intent.extras!!.getString("extra")!!

        startId = when (state) {
            "no" -> 0
            "yes" -> 1
            else -> 0
        }


        if (!this.isRunning && startId == 1) {

            val min = 1
            val max = 9

            val r = Random()
            val random_number = r.nextInt(max - min + 1) + min

            if (random_number == 1) {
                mMediaPlayer = MediaPlayer.create(this, R.raw.musicalarm_1)
            } else if (random_number == 2) {
                mMediaPlayer = MediaPlayer.create(this, R.raw.musicalarm_2)
            } else if (random_number == 3) {
                mMediaPlayer = MediaPlayer.create(this, R.raw.musicalarm_3)
            } else if (random_number == 4) {
                mMediaPlayer = MediaPlayer.create(this, R.raw.musicalarm_4)
            } else if (random_number == 5) {
                mMediaPlayer = MediaPlayer.create(this, R.raw.musicalarm_5)
            } else if (random_number == 6) {
                mMediaPlayer = MediaPlayer.create(this, R.raw.musicalarm_6)
            } else if (random_number == 7) {
                mMediaPlayer = MediaPlayer.create(this, R.raw.musicalarm_7)
            } else if (random_number == 8) {
                mMediaPlayer = MediaPlayer.create(this, R.raw.musicalarm_8)
            } else if (random_number == 9) {
                mMediaPlayer = MediaPlayer.create(this, R.raw.musicalarm_9)
            } else {
                mMediaPlayer = MediaPlayer.create(this, R.raw.musicalarm_1)
            }

            mMediaPlayer.start()


            mNM.notify(0, mNotify)

            this.isRunning = true
            this.startId = 0

        } else if (!this.isRunning && startId == 0) {

            this.isRunning = false
            this.startId = 0

        } else if (this.isRunning && startId == 1) {

            this.isRunning = true
            this.startId = 0

        } else {
            mMediaPlayer.stop()
            mMediaPlayer.reset()
            this.isRunning = false
            this.startId = 0
        }
        return Service.START_NOT_STICKY

    }
    override fun onDestroy() {
        super.onDestroy()
        this.isRunning = false
    }


}
