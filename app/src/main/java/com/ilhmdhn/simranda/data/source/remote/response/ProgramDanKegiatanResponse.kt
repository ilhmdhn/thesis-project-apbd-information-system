package com.ilhmdhn.simranda.data.source.remote.response

import com.google.gson.annotations.SerializedName

data class ProgramDanKegiatanResponse(

    @field:SerializedName("data")
    val data: List<DataItemProgramDanKegiatan>,

    @field:SerializedName("message")
    val message: String? = null,

    @field:SerializedName("status")
    val status: Boolean? = null
)

data class DataItemProgramDanKegiatan(

    @field:SerializedName("no_urut")
    val urut: String,

    @field:SerializedName("kode_kegiatan")
    val kodeKegiatan: String,

    @field:SerializedName("kode_organisasi")
    val kodeOrganisasi: String? = null,

    @field:SerializedName("nama_program")
    val namaProgram: String? = null,

    @field:SerializedName("nama_kegiatan")
    val namaKegiatan: String? = null,

    @field:SerializedName("username")
    val username: String? = null,

    @field:SerializedName("tanggal_input")
    val tanggalEntry: String? = null,

    @field:SerializedName("tahun")
    val tahun: String? = null,
)

data class KegiatanDeleteResponse(
    @field:SerializedName("status")
    val status: Boolean? = null,

    @field:SerializedName("kode")
    val kode: String? = null,

    @field:SerializedName("message")
    val message: String? = null
)

data class KegiatanUpdateResponse(
    @field:SerializedName("status")
    val status: Boolean? = null,

    @field:SerializedName("kode")
    val kode: String? = null,

    @field:SerializedName("message")
    val message: String? = null
)

data class KegiatanPostResponse(
    @field:SerializedName("status")
    val status: Boolean? = null,

    @field:SerializedName("message")
    val message: String? = null
)