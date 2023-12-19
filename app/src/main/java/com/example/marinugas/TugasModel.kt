package com.example.marinugas

data class TugasModel(
    val title: String,
    val date: String,
    val content: String,
    var isChecked: Boolean = false
)


