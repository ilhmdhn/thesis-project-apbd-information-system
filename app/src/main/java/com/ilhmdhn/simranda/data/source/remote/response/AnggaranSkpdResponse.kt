package com.ilhmdhn.simranda.data.source.remote.response

import com.google.gson.annotations.SerializedName

data class AnggaranSkpdResponse(

    @field:SerializedName("data")
    val data: List<DataItemAnggaranSkpd>,

    @field:SerializedName("message")
    val message: String? = null,

    @field:SerializedName("status")
    val status: Boolean? = null
)

data class DataItemAnggaranSkpd(

    @field:SerializedName("TT1")
    val jumlah1: String? = null,

    @field:SerializedName("kode_rekening")
    val kodeRekening: String? = null,

    @field:SerializedName("URAIAN")
    val namaRekening: String? = null,

    @field:SerializedName("TAHUN")
    val tahun: String? = null,

    @field:SerializedName("T1")
    val jumlah: String? = null,

    @field:SerializedName("kode_dokumen")
    val kodeDok: String? = null
)