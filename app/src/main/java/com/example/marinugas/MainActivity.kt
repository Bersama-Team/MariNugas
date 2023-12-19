package com.example.marinugas

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Button
import android.widget.ImageView
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import android.widget.Toast
import com.example.marinugas.data.ListTugas
import com.example.marinugas.databinding.ActivityMainBinding
import com.example.marinugas.rest.RetrofitClient
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response


class MainActivity : AppCompatActivity() {
    private lateinit var binding:ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        val dataList = mutableListOf<TugasModel>()
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        //retrieveListTugas()

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

        adapter.setOnItemClickListener(object : TugasAdapter.onItemClickListener {
            override fun onItemClick(position: Int) {
                // Handle item click here
                val clickedItem = dataList[position]
                Toast.makeText(this@MainActivity, "Clicked: ${clickedItem.title}", Toast.LENGTH_SHORT).show()

                // Contoh pindah ke detail activity
               val intent = Intent(this@MainActivity, detail_tugas::class.java)
                intent.putExtra("title", clickedItem.title)
                intent.putExtra("date", clickedItem.date)
                intent.putExtra("content", clickedItem.content)
                startActivity(intent)
            }
        })

    }

    private fun retrieveListTugas() {
        RetrofitClient.instance.getListTugas()
            .enqueue(object: Callback<ArrayList<ListTugas>> {
                override fun onResponse(call: Call<ArrayList<ListTugas>>, response: Response<ArrayList<ListTugas>>) {
                    if (response.code() == 200) {
                        val list = response.body()
                        Log.d("GET LIST TUGAS", list.toString())

                        if (list!!.isEmpty()) {
                            Toast.makeText(this@MainActivity, "There is no data to display", Toast.LENGTH_LONG).show()
                        } else {
//                            buildListTugas(list)
                        }
                    } else {
                        Toast.makeText(this@MainActivity, "Fail fetching from database response is not 200", Toast.LENGTH_LONG).show()
                        Log.d("GET COUNTRY ITEMS FAIL ${response.code()}", response.body().toString())
                    }
                }

                override fun onFailure(call: Call<ArrayList<ListTugas>>, t: Throwable) {
                    Toast.makeText(this@MainActivity, "Fail fetching from database onFailure", Toast.LENGTH_LONG).show()
                    Log.d("GET LIST TUGAS FAIL", t.toString())
                }
            })
    }

}