package com.ilhmdhn.simranda.data.source.local.entity

import androidx.annotation.NonNull
import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.google.gson.annotations.SerializedName

@Entity(tableName = "anggarandokumenentities")

data class AnggaranDokumenEntity(
    @PrimaryKey(autoGenerate = true)
    @NonNull
    val id: Int,

    @ColumnInfo(name = "nama_rekening")
    val namaRekening: String? = null,

    @ColumnInfo(name = "tahun")
    val tahun: String? = null,

    @ColumnInfo(name = "jumlah")
    val jumlah: String? = null,

    @ColumnInfo(name = "kode_rekening")
    val kodeRekening: String? = null,

    @ColumnInfo(name = "kode_dokumen")
    val kodeDokumen: String? = null,

    @ColumnInfo(name = "jumlah_dua")
    val jumlahDua: String? = null
)
