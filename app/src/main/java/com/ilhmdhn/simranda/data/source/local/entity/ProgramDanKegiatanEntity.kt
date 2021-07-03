package com.ilhmdhn.simranda.data.source.local.entity

import android.os.Parcelable
import androidx.annotation.NonNull
import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import kotlinx.parcelize.Parcelize

@Entity(tableName = "programdankegiatanentities")

@Parcelize
data class ProgramDanKegiatanEntity(
    @PrimaryKey
    @NonNull
    @ColumnInfo(name = "urut")
    val urut: String,

    @ColumnInfo(name = "kode_kegiatan")
    val kodeKegiatan: String,

    @ColumnInfo(name = "kode_organisasi")
    val kodeOrganisasi: String? = null,

    @ColumnInfo(name = "nama_program")
    val namaProgram: String? = null,

    @ColumnInfo(name = "nama_kegiatan")
    val namaKegiatan: String? = null,

    @ColumnInfo(name = "username")
    val username: String? = null,

    @ColumnInfo(name = "tanggal_input")
    val tanggalEntry: String? = null,

    @ColumnInfo(name = "tahun")
    val tahun: String? = null
) : Parcelable
