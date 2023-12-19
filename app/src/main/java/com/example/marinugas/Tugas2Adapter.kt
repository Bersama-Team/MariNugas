package com.example.marinugas

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.CheckBox
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView

class Tugas2Adapter(val tugas: ArrayList<Tugas2Model.Data>
): RecyclerView.Adapter<Tugas2Adapter.ViewHolder>()
{
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int) = ViewHolder(
        LayoutInflater.from(parent.context)
            .inflate(R.layout.activity_list_tugas, parent, false)
    )

    override fun onBindViewHolder(holder: Tugas2Adapter.ViewHolder, position: Int) {
        val data = tugas[position]
        holder.titleTextView.text = data.judul
        holder.contentTextview.text = data.deskripsi
        holder.contentTanggalview.text = data.tenggat_tanggal
        holder.contentJamview.text = data.tenggat_jam

    }

    override fun getItemCount() = tugas.size

    class ViewHolder(view: View): RecyclerView.ViewHolder(view) {
        val titleTextView = view.findViewById<TextView>(R.id.titleTextView)
        val contentTextview = view.findViewById<TextView>(R.id.contentTextview)
        val contentTanggalview = view.findViewById<TextView>(R.id.contentTanggalview)
        val contentJamview = view.findViewById<TextView>(R.id.contentJamview)
        val deleteButton = view.findViewById<ImageView>(R.id.deleteButton)
        val checkbox_list = view.findViewById<CheckBox>(R.id.checkbox_list)

    }

    public fun setData(data: List<Tugas2Model.Data>){
        tugas.clear()
        tugas.addAll(data)
        notifyDataSetChanged()
    }

}