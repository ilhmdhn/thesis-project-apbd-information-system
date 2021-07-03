package com.ilhmdhn.simranda.data.source.remote.response

import com.google.gson.annotations.SerializedName

data class DataAnggaranRekeningResponse(

    @field:SerializedName("data")
    val dataRekening: DataAnggaranRekening,

    @field:SerializedName("message")
    val message: String? = null,

    @field:SerializedName("status")
    val status: Boolean? = null
)

data class DataAnggaranRekening(
    @field:SerializedName("kode_dokumen")
    val kodeDok: String? = null,

    @field:SerializedName("kode_rekening")
    val kodeRekening: String? = null,

    @field:SerializedName("tahun")
    val tahun: String? = null,

    @field:SerializedName("total")
    val total: String? = null,
)
