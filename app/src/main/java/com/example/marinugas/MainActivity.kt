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
import com.example.marinugas.databinding.ActivityMainBinding
import com.google.android.material.floatingactionbutton.FloatingActionButton
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import android.app.Dialog


class MainActivity : AppCompatActivity() {
    private lateinit var binding:ActivityMainBinding
    private lateinit var tugas2Adapter: Tugas2Adapter
    private lateinit var imgAdd: FloatingActionButton
    private lateinit var listTugas: RecyclerView


    private val api by lazy { Api2Retrofit().endpoint }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        setupView()
        setupList()
        setupListener()


    }

    override fun onStart() {
        super.onStart()
        getTugas()
    }

    private fun setupView(){
        listTugas = findViewById(R.id.list_tugas)
        imgAdd = findViewById(R.id.img_add)

    }

    private fun setupList() {
        tugas2Adapter = Tugas2Adapter(arrayListOf(), object :  Tugas2Adapter.OnAdapterListener {
            override fun onClick(judul: Tugas2Model.Data) {
                startActivity(Intent(this@MainActivity, detail_tugas::class.java)
                    .putExtra("judul", judul)
                )
            }
            override fun onDelete(note: Tugas2Model.Data){
                showDeleteConfirmationDialog(note)
            }


        })
        listTugas.adapter = tugas2Adapter
    }

    private fun setupListener(){
        imgAdd.setOnClickListener {
            startActivity(Intent(this, create_tugas::class.java))
        }
    }

    private fun showDeleteConfirmationDialog(note: Tugas2Model.Data) {
        val dialog = Dialog(this)
        dialog.setContentView(R.layout.dialog_delete_confirmation)

        val btnCancel = dialog.findViewById<Button>(R.id.btnCancel)
        val btnDelete = dialog.findViewById<Button>(R.id.btnDelete)

        btnCancel.setOnClickListener {
            dialog.dismiss()
        }

        btnDelete.setOnClickListener {
            api.delete(note.id!!).enqueue(object : Callback<Submit2Model> {
                override fun onResponse(call: Call<Submit2Model>, response: Response<Submit2Model>) {
                    if (response.isSuccessful) {
                        val submit = response.body()
                        Toast.makeText(
                            applicationContext,
                            submit!!.message,
                            Toast.LENGTH_SHORT
                        ).show()
                        getTugas()
                    }
                }

                override fun onFailure(call: Call<Submit2Model>, t: Throwable) {
                }
            })

            dialog.dismiss()
        }

        dialog.show()
    }

    private fun getTugas(){
        api.data().enqueue(object : Callback<Tugas2Model> {
            override fun onResponse(call: Call<Tugas2Model>, response: Response<Tugas2Model>) {
                if (response.isSuccessful) {
                    val listData = response.body()!!.tugas
                    tugas2Adapter.setData(listData)
//                    listData.forEach {
//                        Log.e("MainActivity", "status ${it.status}")
//                    }
                }
            }

            override fun onFailure(call: Call<Tugas2Model>, t: Throwable) {
                Log.e("MainActivity", t.toString())

            }

        })
    }

}