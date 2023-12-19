package com.example.marinugas

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.ImageView
import com.google.android.material.textfield.TextInputEditText

class create_tugas : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_create_tugas)

        val edJudul = findViewById<TextInputEditText>(R.id.edJudul)
        val edDesk = findViewById<TextInputEditText>(R.id.edDesk)
        val img_back = findViewById<ImageView>(R.id.img_back)

        img_back.setOnClickListener {
            finish()
        }
    }
}