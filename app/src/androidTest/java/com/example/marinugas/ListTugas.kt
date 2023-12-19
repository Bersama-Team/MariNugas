package com.example.marinugas
import java.time.LocalDateTime

data class ListTugas(
    val id: Int,
    val judul: String?,
    val deskripsi: String?,
    val tenggat: LocalDateTime,
    val status:String?
)
