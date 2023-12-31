package com.example.marinugas

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.Menu
import android.widget.Button
import android.widget.ImageView
import android.widget.SearchView
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
    private lateinit var binding: ActivityMainBinding
    private lateinit var tugas2Adapter: Tugas2Adapter
    private lateinit var imgAdd: FloatingActionButton
    private lateinit var listTugas: RecyclerView
    private lateinit var searchView: SearchView
    private val api by lazy { Api2Retrofit().endpoint }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        setupView()
        setupList()
        setupListener()
        setupSearchView()
    }

    override fun onStart() {
        super.onStart()
        getTugas()
    }

    private fun setupView() {
        listTugas = findViewById(R.id.list_tugas)
        imgAdd = findViewById(R.id.img_add)
    }

    private fun setupList() {
        tugas2Adapter = Tugas2Adapter(arrayListOf(), object : Tugas2Adapter.OnAdapterListener {
            override fun onClick(judul: Tugas2Model.Data) {
                startActivity(
                    Intent(this@MainActivity, detail_tugas::class.java)
                        .putExtra("judul", judul)
                )
            }

            override fun onDelete(note: Tugas2Model.Data) {
                showDeleteConfirmationDialog(note)
            }

            override fun onCheckButtonClicked(data: Tugas2Model.Data) {

                // Simpan nilai-nilai yang sudah ada sebelum update
                val existingJudul = data.judul ?: ""
                val existingDeskripsi = data.deskripsi ?: ""
                val existingTenggatTanggal = data.tenggat_tanggal ?: ""
                val existingTenggatJam = data.tenggat_jam ?: ""

                // Update status only
                val updatedStatus = if (data.status == "finished") "unfinished" else "finished"
                data.status = updatedStatus

                // Call the Retrofit update function, using existing values for other parameters
                val call = api.update(
                    id = data.id ?: "",
                    judul = existingJudul,
                    deskripsi = existingDeskripsi,
                    tenggatTanggal = existingTenggatTanggal,
                    tenggatJam = existingTenggatJam,
                    status = updatedStatus
                )
                fun refreshAdapter() {
                    tugas2Adapter.notifyDataSetChanged()
                }
                call.enqueue(object : Callback<Submit2Model> {
                    override fun onResponse(call: Call<Submit2Model>, response: Response<Submit2Model>) {
                        if (response.isSuccessful) {
                            // Pembaruan berhasil, muat ulang data dari server
                            getTugas() // Misalkan ini adalah fungsi untuk memuat data tugas dari server
                        } else {
                            // Tangani jika terjadi kesalahan saat melakukan permintaan
                            // Kembalikan status ke kondisi sebelumnya jika permintaan gagal
                            data.status = if (data.status == "finished") "unfinished" else "finished"
                            // Perbarui tampilan karena perubahan status gagal terkirim ke server
                            refreshAdapter()

                        }
                    }

                    override fun onFailure(call: Call<Submit2Model>, t: Throwable) {
                        // Tangani jika terjadi kegagalan koneksi atau permintaan
                        // Kembalikan status ke kondisi sebelumnya jika permintaan gagal
                        data.status = if (data.status == "finished") "unfinished" else "finished"
                        // Perbarui tampilan karena perubahan status gagal terkirim ke server
                        refreshAdapter()

                    }
                })
            }

        })
        listTugas.adapter = tugas2Adapter

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
                    // Handle failure
                }
            })

            dialog.dismiss()
        }

        dialog.show()
    }

    private fun setupListener() {
        imgAdd.setOnClickListener {
            startActivity(Intent(this, create_tugas::class.java))
        }
    }

    private fun getTugas() {
        api.data().enqueue(object : Callback<Tugas2Model> {
            override fun onResponse(call: Call<Tugas2Model>, response: Response<Tugas2Model>) {
                if (response.isSuccessful) {
                    val listData = response.body()?.tugas ?: emptyList()
                    tugas2Adapter.setData(listData)
                }
            }

            override fun onFailure(call: Call<Tugas2Model>, t: Throwable) {
                // Handle failure
            }
        })
    }

    private fun setupSearchView() {
        searchView = findViewById(R.id.searchView)
        searchView.setOnQueryTextListener(object : SearchView.OnQueryTextListener {
            override fun onQueryTextSubmit(query: String?): Boolean {
                return false
            }

            override fun onQueryTextChange(newText: String?): Boolean {
                tugas2Adapter.filter.filter(newText)
                return false
            }
        })
    }
}
