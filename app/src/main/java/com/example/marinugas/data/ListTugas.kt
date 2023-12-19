package com.example.marinugas.data
import java.time.LocalDateTime

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize

@Parcelize
data class ListTugas(
    val id_tugas: Int,
    val judul_tugas: String?,
    val deskripsi_tugas: String?,
    val tenggat: LocalDateTime,
    val is_finished:String?
) : Parcelable
