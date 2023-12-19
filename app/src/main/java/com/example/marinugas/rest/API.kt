package com.example.marinugas.rest

import com.example.marinugas.data.*
import retrofit2.Call
import retrofit2.http.*
import retrofit2.http.GET
import retrofit2.http.Query
import java.util.ArrayList // Add this import for ArrayList

interface API {
    @GET("read.php")
    fun getListTugas(): Call<ArrayList<ListTugas>>

    @GET("detail.php")
    fun getListTugasDetail(
        @Query("tugasId") tugasId: Int
    ):Call<ListTugasDetail>
}