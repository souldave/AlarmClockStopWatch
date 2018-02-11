package com.example.davegilbier.alarmclockstopwatch

/**
 * Created by Dave Gilbier on 11/02/2018.
 */


import android.annotation.SuppressLint
import android.content.Intent
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler
import android.os.SystemClock
import android.view.View
import android.widget.ArrayAdapter
import android.widget.Button
import android.widget.ListView
import android.widget.TextView
import java.util.*
import kotlin.collections.ArrayList


class StopWatch : AppCompatActivity() {

    lateinit var textView: TextView
    private lateinit var btnStart: Button
    private lateinit var btnPause: Button
    lateinit var btnReset: Button
    private lateinit var btnSaveLap: Button
    private lateinit var listView: ListView
    lateinit var handler: Handler
    lateinit var listElementsArrayList: MutableList<String>
    lateinit var adapter: ArrayAdapter<String>

    var milliSecondTime: Long = 0
    var startingTime: Long = 0
    var timeBuff: Long = 0
    var updateTime = 0L
    var seconds: Int = 0
    var minutes: Int = 0
    var milliSeconds: Int = 0
    private var listElements = arrayOf<String>()
        @SuppressLint("WrongViewCast")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_stopwatch)

        textView = findViewById(R.id.tvTime) as TextView
        btnStart = findViewById(R.id.btnStart) as Button
        btnPause = findViewById(R.id.btnPause) as Button
        btnReset = findViewById(R.id.btnReset) as Button
        btnSaveLap = findViewById(R.id.btnSaveLap) as Button
        listView = findViewById(R.id.listview) as ListView
        handler = Handler()
        listElementsArrayList = ArrayList<String>(Arrays.asList(*listElements))
        adapter = ArrayAdapter<String>(this@StopWatch, android.R.layout.simple_list_item_1, listElementsArrayList)
        listView.adapter = adapter

        btnStart.setOnClickListener(object : View.OnClickListener{
            override fun onClick(v: View?) {
                startingTime = SystemClock.uptimeMillis()
                handler.postDelayed(runnable, 0)
                btnReset.isEnabled = false
            }
        })

        btnPause.setOnClickListener(object : View.OnClickListener{
            override fun onClick(v: View?) {
                timeBuff += milliSecondTime
                handler.removeCallbacks(runnable)
                btnReset.isEnabled = true
            }
        })

        btnReset.setOnClickListener(object : View.OnClickListener{
            override fun onClick(v: View?) {
                milliSecondTime = 0L
                startingTime = 0L
                timeBuff = 0L
                updateTime = 0L
                seconds = 0
                minutes = 0
                milliSeconds = 0
                textView.text = "00:00:00"
                listElementsArrayList.clear()
                adapter.notifyDataSetChanged()
            }
        })

        btnSaveLap.setOnClickListener(object : View.OnClickListener{
            override fun onClick(v: View?) {
                listElementsArrayList.add(textView.text.toString())
                adapter.notifyDataSetChanged()
            }
        })


        val alarmClock = findViewById(R.id.btnAlarm) as Button
        alarmClock.setOnClickListener {
            val intent = Intent(this@StopWatch, MainActivity::class.java)
            startActivity(intent)
        }
    }

    var runnable: Runnable = object : Runnable {
        override fun run() {
            milliSecondTime = SystemClock.uptimeMillis() - startingTime
            updateTime = timeBuff + milliSecondTime
            seconds = (updateTime / 1000).toInt()
            minutes = seconds / 60
            seconds %= 60
            milliSeconds = (updateTime % 1000).toInt()
            textView.text = ("" + minutes + ":"
                    + String.format("%02d", seconds) + ":"
                    + String.format("%03d", milliSeconds))
            handler.postDelayed(this, 0)
        }

    }}




