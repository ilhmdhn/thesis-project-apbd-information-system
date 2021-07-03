package com.ilhmdhn.simranda.data.source.local.entity

import android.os.Parcelable
import androidx.annotation.NonNull
import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.google.gson.annotations.SerializedName
import kotlinx.parcelize.Parcelize

@Entity(tableName = "anggaranentities")
@Parcelize

data class AnggaranEntity(
    @PrimaryKey
    @NonNull
    @ColumnInfo(name = "id")
    val id: String,

    @ColumnInfo(name = "kode_kegiatan")
    val kodeKegiatan: String? = null,

    @ColumnInfo(name = "halaman_dokumen")
    val halDokumen: String? = null,

    @ColumnInfo(name = "kode_rekening")
    val kodeRekening: String? = null,

    @ColumnInfo(name = "kode_bagian")
    val kodeBagian: String? = null,

    @ColumnInfo(name = "kode_organisasi")
    val kodeOrganisasi: String? = null,

    @ColumnInfo(name = "nominal_anggaran")
    val nominalAnggaran: String? = null,

    @ColumnInfo(name = "tahun")
    val tahun: String? = null,

    @ColumnInfo(name = "tanggal_input")
    val tanggalInput: String? = null,

    @ColumnInfo(name = "username")
    val username: String? = null,

    @ColumnInfo(name = "kode_dokumen")
    val kodeDokumen: String? = null

) : Parcelable
