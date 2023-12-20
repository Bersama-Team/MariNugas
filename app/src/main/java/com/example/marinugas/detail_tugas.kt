package com.example.marinugas

import android.app.DatePickerDialog
import android.app.TimePickerDialog
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.DatePicker
import android.widget.TimePicker
import android.widget.Button
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import java.util.Calendar
import com.google.android.material.textfield.TextInputEditText
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class detail_tugas : AppCompatActivity(), DatePickerDialog.OnDateSetListener, TimePickerDialog.OnTimeSetListener {

    private lateinit var edJudul: TextInputEditText
    private lateinit var edDesk: TextInputEditText
    private lateinit var btn_simpan: Button
    private lateinit var btn_datePicker: Button
    private lateinit var btn_timePicker: Button
    private lateinit var img_back: ImageView
    private val api by lazy { Api2Retrofit().endpoint }
    private val judul by lazy { intent.getSerializableExtra("judul") as Tugas2Model.Data}

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
        setupView()
        setupListener()
        pickDate()
        pickTime()

    }

    private fun setupView(){
        edJudul = findViewById(R.id.edJudul)
        edDesk = findViewById(R.id.edDesk)
        btn_simpan = findViewById(R.id.btn_simpan)
        btn_datePicker = findViewById(R.id.btn_datePicker)
        btn_timePicker = findViewById(R.id.btn_timePicker)
        edJudul.setText( judul.judul )
        edDesk.setText( judul.deskripsi )
        btn_datePicker.setText( judul.tenggat_tanggal )
        btn_timePicker.setText( judul.tenggat_jam )

    }

    private fun setupListener(){
        btn_simpan.setOnClickListener {
            api.update(judul.id!!, edJudul.text.toString(),  edDesk.text.toString(),  btn_datePicker.text.toString(),  btn_timePicker.text.toString())
                .enqueue(object : Callback<Submit2Model> {
                    override fun onResponse(
                        call: Call<Submit2Model>,
                        response: Response<Submit2Model>
                    ) {
                        if (response.isSuccessful) {
                            val submit = response.body()
                            Toast.makeText(
                                applicationContext, submit!!.message, Toast.LENGTH_SHORT
                            ).show()
                            finish()
                        }
                    }

                    override fun onFailure(call: Call<Submit2Model>, t: Throwable) {
                    }

                })
        }

        val img_back = findViewById<ImageView>(R.id.img_back)
        img_back.setOnClickListener {
            startActivity(Intent(this@detail_tugas, MainActivity::class.java))
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
        btn_timePicker.text = "$savedHour:$savedMinute"

    }
}