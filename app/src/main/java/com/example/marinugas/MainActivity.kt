package com.example.marinugas

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.ImageView
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        val dataList = mutableListOf<TugasModel>()
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val img_add = findViewById<ImageView>(R.id.img_add)
        img_add.setOnClickListener {
            startActivity(Intent(this@MainActivity, create_tugas::class.java))
        }
        // Inisialisasi RecyclerView
        val recyclerView: RecyclerView = findViewById(R.id.recyclerView)
        val adapter = TugasAdapter(this, dataList)
        recyclerView.layoutManager = LinearLayoutManager(this)
        recyclerView.adapter = adapter

        // Tambahkan contoh data ke dataList
        dataList.add(TugasModel("Judul Tugas 1", "Tenggat 1", "Deskripsi Tugas 1"))
        dataList.add(TugasModel("Judul Tugas 2", "Tenggat 2", "Deskripsi Tugas 2"))


    }
}