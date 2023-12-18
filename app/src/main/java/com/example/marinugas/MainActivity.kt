package com.example.marinugas

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val btn_detail = findViewById<Button>(R.id.btn_detail)
        btn_detail.setOnClickListener {
            startActivity(Intent(this@MainActivity, detail_tugas::class.java))
        }

        val btn_list = findViewById<Button>(R.id.btn_list)
        btn_list.setOnClickListener {
            startActivity(Intent(this@MainActivity, list_tugas::class.java))
        }
    }
}