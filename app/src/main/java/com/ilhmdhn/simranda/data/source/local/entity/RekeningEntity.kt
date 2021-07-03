package com.ilhmdhn.simranda.data.source.local.entity

import android.os.Parcelable
import androidx.annotation.NonNull
import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import kotlinx.parcelize.Parcelize

@Entity(tableName = "rekeningentities")

@Parcelize
data class RekeningEntity(
    @PrimaryKey
    @NonNull
    @ColumnInfo(name = "urutan")
    var urutan: String,

    @ColumnInfo(name = "kode_rekening")
    var kodeRekening: String,

    @ColumnInfo(name = "nama_rekening")
    var namaRekening: String?,

    @ColumnInfo(name = "username")
    var username: String?,

    @ColumnInfo(name = "tahun")
    var tahun: String?,

    @ColumnInfo(name = "tanggal_input")
    var tanggalInput: String?,
) : Parcelable