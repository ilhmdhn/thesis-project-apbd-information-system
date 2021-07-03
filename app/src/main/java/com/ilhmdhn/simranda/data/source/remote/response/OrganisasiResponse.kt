package com.ilhmdhn.simranda.data.source.remote.response

import com.google.gson.annotations.SerializedName

data class OrganisasiResponse(

    @field:SerializedName("data")
    val data: List<DataItemOrganisasi>,

    @field:SerializedName("message")
    val message: String? = null,

    @field:SerializedName("status")
    val status: Boolean? = null
)

data class DataItemOrganisasi(

    @field:SerializedName("no_urut")
    val urut: String,

    @field:SerializedName("kode_organisasi")
    val kodeOrg: String,

    @field:SerializedName("kode_bagian")
    val kodeBag: String? = null,

    @field:SerializedName("nama_organisasi")
    val namaOrg: String? = null,

    @field:SerializedName("nama_bagian")
    val namaBag: String? = null,

    @field:SerializedName("nama_fungsi")
    val namaFungsi: String? = null,

    @field:SerializedName("tahun")
    val tahun: String? = null,

    @field:SerializedName("username")
    val username: String? = null,

    @field:SerializedName("tanggal_input")
    val tanggalEntry: String? = null,
)

data class organisasiPostResponse(
    @field:SerializedName("message")
    val message: DataItemOrganisasi? = null,

    @field:SerializedName("status")
    val status: Boolean? = null
)

data class organisasiDeleteResponse(
    @field:SerializedName("status")
    val status: Boolean? = null,

    @field:SerializedName("kode")
    val kode: String? = null,

    @field:SerializedName("message")
    val message: String? = null
)
