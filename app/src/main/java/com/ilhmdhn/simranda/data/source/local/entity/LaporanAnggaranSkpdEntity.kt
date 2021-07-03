package com.ilhmdhn.simranda.data.source.local.entity

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "laporananggaranskpdentities")

data class LaporanAnggaranSkpdEntity(
    @PrimaryKey(autoGenerate = true)
    val id: Int,

    @ColumnInfo(name = "kode_rekening")
    val kodeRekening: String? = null,

    @ColumnInfo(name = "nama_rekening")
    val namaRekening: String? = null,

    @ColumnInfo(name = "kode_dokumen")
    val kodeDok: String? = null,

    @ColumnInfo(name = "jumlah")
    val jumlah: String? = null,

    @ColumnInfo(name = "jumlah_dua")
    val jumlah1: String? = null,

    @ColumnInfo(name = "tahun")
    val tahun: String? = null
)
