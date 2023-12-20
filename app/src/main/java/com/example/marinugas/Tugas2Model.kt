package com.example.marinugas

import java.io.Serializable

class Tugas2Model (
    val tugas: List<Data>
){
    data class Data (val id: String,
                     val judul: String,
                     val deskripsi: String?,
                     val tenggat_tanggal: String?,
                     val tenggat_jam: String?,
                     val status: String) : Serializable
}