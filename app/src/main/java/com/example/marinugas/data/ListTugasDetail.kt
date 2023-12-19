package com.example.marinugas.data
import java.time.LocalDateTime

data class ListTugasDetail(
    val id: Int,
    val judul: String?,
    val deskripsi: String?,
    val tenggat: LocalDateTime,
    val status:String?
)
