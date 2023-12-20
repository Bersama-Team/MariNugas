package com.example.marinugas

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.CheckBox
import android.widget.Filter
import android.widget.Filterable
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import java.util.*
import kotlin.collections.ArrayList
import android.app.AlertDialog
import android.graphics.Paint
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class Tugas2Adapter(
    private var tugas: ArrayList<Tugas2Model.Data>,
    private val listener: OnAdapterListener,
) : RecyclerView.Adapter<Tugas2Adapter.ViewHolder>(), Filterable {

    private var tugasFiltered: ArrayList<Tugas2Model.Data> = tugas
    private val api by lazy { Api2Retrofit().endpoint }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int) = ViewHolder(
        LayoutInflater.from(parent.context)
            .inflate(R.layout.activity_list_tugas, parent, false)
    )

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val data = tugasFiltered[position]
        holder.titleTextView.text = data.judul
        holder.contentTextview.text = data.deskripsi
        holder.contentTanggalview.text = data.tenggat_tanggal
        holder.contentJamview.text = data.tenggat_jam
        holder.itemView.setOnClickListener {
            listener.onClick(data)
        }
        holder.deleteButton.setOnClickListener {
            listener.onDelete(data)
        }
        holder.checkbox_list.isChecked = data.status == "finished"
        holder.checkbox_list.setOnClickListener {
            listener.onCheckButtonClicked(data)
        }


    }

    override fun getItemCount() = tugasFiltered.size

    fun filterList(filteredCourseList: ArrayList<Tugas2Model.Data>) {
        tugasFiltered = filteredCourseList
        notifyDataSetChanged()
    }

    override fun getFilter(): Filter {
        return object : Filter() {
            override fun performFiltering(constraint: CharSequence?): FilterResults {
                val charSequenceString = constraint.toString().toLowerCase(Locale.getDefault())
                val filteredList: ArrayList<Tugas2Model.Data> = ArrayList()

                for (data in tugas) {
                    // Use the safe call operator (?) to handle nullable properties
                    val judulMatches = data.judul?.toLowerCase(Locale.getDefault())?.contains(charSequenceString) == true
                    val deskripsiMatches = data.deskripsi?.toLowerCase(Locale.getDefault())?.contains(charSequenceString) == true

                    if (judulMatches || deskripsiMatches) {
                        filteredList.add(data)
                    }
                }


                val filterResults = FilterResults()
                filterResults.values = filteredList
                return filterResults
            }

            override fun publishResults(constraint: CharSequence?, results: FilterResults?) {
                tugasFiltered = results?.values as ArrayList<Tugas2Model.Data>
                notifyDataSetChanged()
            }
        }
    }

    class ViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        val titleTextView = view.findViewById<TextView>(R.id.titleTextView)
        val contentTextview = view.findViewById<TextView>(R.id.contentTextview)
        val contentTanggalview = view.findViewById<TextView>(R.id.contentTanggalview)
        val contentJamview = view.findViewById<TextView>(R.id.contentJamview)
        val deleteButton = view.findViewById<ImageView>(R.id.deleteButton)
        val checkbox_list = view.findViewById<CheckBox>(R.id.checkbox_list)


        init {
            // Set a listener on the checkbox to toggle the strike-through effect
            checkbox_list.setOnCheckedChangeListener { _, isChecked ->
                if (isChecked) {
                    // Apply strike-through if checked
                    titleTextView.paintFlags =
                        titleTextView.paintFlags or Paint.STRIKE_THRU_TEXT_FLAG
                } else {
                    // Remove strike-through if unchecked
                    titleTextView.paintFlags =
                        titleTextView.paintFlags and Paint.STRIKE_THRU_TEXT_FLAG.inv()
                }
            }


        }

    }



    public fun setData(data: List<Tugas2Model.Data>) {
        tugas.clear()
        tugas.addAll(data)
        notifyDataSetChanged()
    }

    interface OnAdapterListener {
        fun onClick(judul: Tugas2Model.Data)
        fun onDelete(judul: Tugas2Model.Data)
        fun onCheckButtonClicked(data: Tugas2Model.Data)
    }

}