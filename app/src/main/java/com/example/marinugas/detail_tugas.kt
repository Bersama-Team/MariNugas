package com.example.marinugas

import android.app.DatePickerDialog
import android.app.TimePickerDialog
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.DatePicker
import android.widget.TimePicker
import android.widget.Button
import android.widget.ImageView
import android.widget.TextView
import java.util.Calendar

class detail_tugas : AppCompatActivity(), DatePickerDialog.OnDateSetListener, TimePickerDialog.OnTimeSetListener {

    var day = 0
    var month = 0
    var year = 0
    var hour = 0
    var minute = 0

    var savedDay = 0
    var savedMonth = 0
    var savedYear = 0
    var savedHour = 0
    var savedMinute = 0

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_detail_tugas)
        pickDate()
        pickTime()

        val btn_back = findViewById<ImageView>(R.id.btn_back)

        btn_back.setOnClickListener {
            finish()
        }
    }

    private fun getDateCalendar(){
        val cal = Calendar.getInstance()
        day = cal.get(Calendar.DAY_OF_MONTH)
        month = cal.get(Calendar.MONTH)
        year = cal.get(Calendar.YEAR)
        hour = cal.get(Calendar.HOUR)
        minute = cal.get(Calendar.MINUTE)
    }

    private fun pickDate() {
        val btn_datePicker = findViewById<Button>(R.id.btn_datePicker)
        btn_datePicker.setOnClickListener {
            getDateCalendar()

            DatePickerDialog(this,this,year,month,day).show()
        }
    }

    override fun onDateSet(view: DatePicker?, year: Int, month: Int, dayOfMonth: Int) {
        savedDay = dayOfMonth
        savedMonth = month +1
        savedYear = year

        getDateCalendar()
        val btn_datePicker = findViewById<Button>(R.id.btn_datePicker)
        btn_datePicker.text = "$savedDay-$savedMonth-$savedYear"
    }

    private fun pickTime() {
        val btn_timePicker = findViewById<Button>(R.id.btn_timePicker)
        btn_timePicker.setOnClickListener {

            TimePickerDialog(this, this, hour, minute, true).show()
        }
    }
    override fun onTimeSet(view: TimePicker?, hourOfDay: Int, minute: Int) {
        savedHour = hourOfDay
        savedMinute = minute
        val btn_timePicker = findViewById<Button>(R.id.btn_timePicker)
        btn_timePicker.text = " $savedHour : $savedMinute"

    }
}