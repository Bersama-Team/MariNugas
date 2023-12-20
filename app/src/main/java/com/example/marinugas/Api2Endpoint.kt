package com.example.marinugas

import retrofit2.Call
import retrofit2.http.Field
import retrofit2.http.FormUrlEncoded
import retrofit2.http.GET
import retrofit2.http.POST
import java.sql.Date
import java.sql.Time

interface Api2Endpoint {

    @GET("data.php")
    fun data() : Call<Tugas2Model>

    @FormUrlEncoded
    @POST("create.php")
    fun create(
        @Field("judul") judul: String,
        @Field("deskripsi") deskripsi: String,
        @Field("tenggat_tanggal") tenggatTanggal: String,
        @Field("tenggat_jam") tenggatJam: String,
        @Field("status") status: String = "unfinished"
    ) : Call<Submit2Model>

    @FormUrlEncoded
    @POST("update.php")
    fun update(
        @Field("id") id: String,
        @Field("judul") judul: String,
        @Field("deskripsi") deskripsi: String,
        @Field("tenggat_tanggal") tenggatTanggal: String,
        @Field("tenggat_jam") tenggatJam: String,
        @Field("status") status: String = "unfinished"
    ) : Call<Submit2Model>
}