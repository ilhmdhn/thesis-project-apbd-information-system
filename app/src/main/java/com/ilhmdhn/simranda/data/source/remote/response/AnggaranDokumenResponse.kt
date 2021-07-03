package com.ilhmdhn.simranda.data.source.remote.response

import com.google.gson.annotations.SerializedName

data class AnggaranDokumenResponse(

    @field:SerializedName("data")
    val data: List<DataItemAnggaranDokumen>,

    @field:SerializedName("message")
    val message: String? = null,

    @field:SerializedName("status")
    val status: Boolean? = null
)

data class DataItemAnggaranDokumen(

    @field:SerializedName("nama_rekening")
    val namaRekening: String? = null,

    @field:SerializedName("tahun")
    val tahun: String? = null,

    @field:SerializedName("jumlah")
    val jumlah: String? = null,

    @field:SerializedName("kode_rekening")
    val kodeRekening: String? = null,

    @field:SerializedName("kode_dokumen")
    val kodeDokumen: String? = null,

    @field:SerializedName("jumlah_dua")
    val jumlahDua: String? = null
)
