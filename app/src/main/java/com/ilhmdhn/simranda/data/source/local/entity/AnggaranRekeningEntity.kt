package com.ilhmdhn.simranda.data.source.local.entity

import androidx.annotation.NonNull
import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "anggaranrekeningentities")

data class AnggaranRekeningEntity(

    @PrimaryKey(autoGenerate = true)
    @NonNull
    val id: Int,

    @ColumnInfo(name = "kode_rekening")
    val kodeRekening: String? = null,

    @ColumnInfo(name = "kode_dokumen")
    val kodeDok: String? = null,

    @ColumnInfo(name = "tahun")
    val tahun: String? = null,

    @ColumnInfo(name = "total")
    val total: String? = null
)
