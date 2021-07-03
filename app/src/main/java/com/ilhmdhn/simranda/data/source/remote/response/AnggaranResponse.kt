package com.ilhmdhn.simranda.data.source.remote.response

import com.google.gson.annotations.SerializedName

data class AnggaranResponse(

    @field:SerializedName("data")
    val data: List<DataItemAnggaran>,

    @field:SerializedName("message")
    val message: String? = null,

    @field:SerializedName("status")
    val status: Boolean? = null
)

data class DataItemAnggaran(

    @field:SerializedName("id")
    val id: String,

    @field:SerializedName("kode_kegiatan")
    val kodeKegiatan: String? = null,

    @field:SerializedName("halaman_dokumen")
    val halDokumen: String? = null,

    @field:SerializedName("kode_rekening")
    val kodeRekening: String? = null,

    @field:SerializedName("kode_bagian")
    val kodeBagian: String? = null,

    @field:SerializedName("kode_organisasi")
    val kodeOrganisasi: String? = null,

    @field:SerializedName("nominal_anggaran")
    val nominalAnggaran: String? = null,

    @field:SerializedName("tahun")
    val tahun: String? = null,

    @field:SerializedName("tanggal_input")
    val tanggalInput: String? = null,

    @field:SerializedName("username")
    val username: String? = null,

    @field:SerializedName("kode_dokumen")
    val kodeDokumen: String? = null
)

data class AnggaranUpdateResponse(
    @field:SerializedName("status")
    val status: Boolean? = null,

    @field:SerializedName("message")
    val message: String? = null
)

data class AnggaranDeleteResponse(
    @field:SerializedName("status")
    val status: Boolean? = null,

    @field:SerializedName("kode")
    val kode: String? = null,

    @field:SerializedName("message")
    val message: String? = null
)

data class AnggaranPostResponse(
    @field:SerializedName("status")
    val status: Boolean? = null,

    @field:SerializedName("message")
    val message: String? = null
)