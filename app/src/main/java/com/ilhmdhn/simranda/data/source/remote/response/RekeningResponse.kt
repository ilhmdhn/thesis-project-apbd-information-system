package com.ilhmdhn.simranda.data.source.remote.response

import com.google.gson.annotations.SerializedName

data class RekeningResponse(

    @field:SerializedName("data")
    val data: List<DataItemRekening>,

    @field:SerializedName("message")
    val message: String? = null,

    @field:SerializedName("status")
    val status: Boolean? = null
)

data class DataItemRekening(

    @field:SerializedName("no_urut")
    val urutan: String,

    @field:SerializedName("kode_rekening")
    val kodeRekening: String,

    @field:SerializedName("nama_rekening")
    val namaRekening: String? = null,

    @field:SerializedName("username")
    val username: String? = null,

    @field:SerializedName("tahun")
    val tahun: String? = null,

    @field:SerializedName("tanggal_input")
    val tanggalInput: String? = null,
)

data class RekeningPostResponse(


    @field:SerializedName("status")
    val status: Boolean? = null
)

data class RekeningChangeResponse(
    @field:SerializedName("status")
    val status: Boolean,

    @field:SerializedName("message")
    val message: String
)

data class RekeningUpdateResponse(
    @field:SerializedName("status")
    val status: Boolean? = null,

    @field:SerializedName("message")
    val message: String? = null
)