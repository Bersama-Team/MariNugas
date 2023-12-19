package com.example.marinugas

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.CheckBox
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView

class TugasAdapter(private val context: Context, private val dataList: List<TugasModel>) :
    RecyclerView.Adapter<TugasAdapter.TugasViewHolder>() {

    inner class TugasViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val checkboxList: CheckBox = itemView.findViewById(R.id.checkbox_list)
        val deleteButton: ImageView = itemView.findViewById(R.id.deleteButton)
        val titleTextView: TextView = itemView.findViewById(R.id.titleTextView)
        val contentTanggalView: TextView = itemView.findViewById(R.id.contentTanggalview)
        val contentTextView: TextView = itemView.findViewById(R.id.contentTextview)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): TugasViewHolder {
        val view =
            LayoutInflater.from(context).inflate(R.layout.activity_list_tugas, parent, false)
        return TugasViewHolder(view)
    }

    override fun onBindViewHolder(holder: TugasViewHolder, position: Int) {
        val data = dataList[position]

        holder.titleTextView.text = data.title
        holder.contentTanggalView.text = data.date
        holder.contentTextView.text = data.content

        holder.checkboxList.isChecked = data.isChecked

        holder.checkboxList.setOnCheckedChangeListener { _, isChecked ->
            // Handle checkbox click event
            data.isChecked = isChecked
        }

        holder.deleteButton.setOnClickListener {
            // Handle delete button click event
            // Implement delete functionality here
            // You may want to remove the item from dataList and notify the adapter
        }
    }

    override fun getItemCount(): Int {
        return dataList.size
    }
}
