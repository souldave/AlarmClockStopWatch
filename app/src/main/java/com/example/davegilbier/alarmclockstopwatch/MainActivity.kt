package com.example.davegilbier.alarmclockstopwatch

import android.app.AlarmManager
import android.app.PendingIntent
import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.support.v4.content.ContextCompat.startActivity
import android.support.v7.app.AppCompatActivity
import android.view.View
import android.widget.Button
import android.widget.TextView
import android.widget.TimePicker
import android.widget.Toast
import com.example.davegilbier.alarmclockstopwatch.R.id.stop_watch
import java.util.*

class MainActivity : AppCompatActivity() {

    private lateinit var alarmManager: AlarmManager
    private var pIntent: PendingIntent? = null
    private var alarmTimePicker: TimePicker? = null
    private var alarmTextView: TextView? = null
    private lateinit var stop_watch: Button

    private lateinit var inst: MainActivity
    private lateinit var context: Context

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)



        this.context = this
        alarmTextView = findViewById(R.id.alarmText) as TextView
        val myIntent = Intent(this.context, AlarmReceiver::class.java)
        // access alarmManager
        alarmManager = getSystemService(Context.ALARM_SERVICE) as AlarmManager
        // iset ang time
        val calendar = Calendar.getInstance()
        alarmTimePicker = findViewById(R.id.alarmTimePicker) as TimePicker






        val startAlarm = findViewById(R.id.start_alarm) as Button
        startAlarm.setOnClickListener {
            calendar.add(Calendar.SECOND, 3)
            val hour = alarmTimePicker!!.currentHour
            val minute = alarmTimePicker!!.currentMinute

            setAlarmText("You clicked a $hour and $minute")

            calendar.set(Calendar.HOUR_OF_DAY, alarmTimePicker!!.currentHour)
            calendar.set(Calendar.MINUTE, alarmTimePicker!!.currentMinute)

            myIntent.putExtra("extra", "yes")
            pIntent = PendingIntent.getBroadcast(this@MainActivity, 0, myIntent, PendingIntent.FLAG_UPDATE_CURRENT)
            alarmManager.set(AlarmManager.RTC_WAKEUP, calendar.timeInMillis, pIntent)


            setAlarmText("Alarm set to $hour:$minute")
            Toast.makeText(applicationContext, "You set the alarm", Toast.LENGTH_SHORT).show();
        }

        val stopAlarm = findViewById(R.id.stop_alarm) as Button
        stopAlarm.setOnClickListener {
            val min = 1
            val max = 9

            val r = Random()
            r.nextInt(max - min + 1) + min

            myIntent.putExtra("extra", "no")
            sendBroadcast(myIntent)

            alarmManager.cancel(pIntent)
            setAlarmText("Alarm canceled")
            //setAlarmText("You clicked a " + " canceled");
        }

        val stopwatch = findViewById(R.id.stop_watch) as Button
        stopwatch.setOnClickListener{
        var myintent = Intent(this@MainActivity, StopWatch::class.java)
        startActivity(myintent)
        }

    }

    private fun setAlarmText(alarmText: String) {
        alarmTextView!!.text = alarmText
    }


    public override fun onStart() {
        super.onStart()
        inst = this
    }

    public override fun onDestroy() {
        super.onDestroy()
    }





}
