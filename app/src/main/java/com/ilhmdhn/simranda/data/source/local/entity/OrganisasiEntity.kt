package com.ilhmdhn.simranda.data.source.local.entity

import android.os.Parcelable
import androidx.annotation.NonNull
import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import kotlinx.parcelize.Parcelize

@Entity(tableName = "organisasientities")

@Parcelize
data class OrganisasiEntity(
    @PrimaryKey
    @NonNull
    @ColumnInfo(name = "urut")
    val urut: String,

    @ColumnInfo(name = "kode_organisasi")
    val kodeOrg: String,

    @ColumnInfo(name = "kode_bagian")
    val kodeBag: String? = null,

    @ColumnInfo(name = "nama_organisasi")
    val namaOrg: String? = null,

    @ColumnInfo(name = "nama_bagian")
    val namaBag: String? = null,

    @ColumnInfo(name = "nama_fungsi")
    val namaFungsi: String? = null,

    @ColumnInfo(name = "tahun")
    val tahun: String? = null,

    @ColumnInfo(name = "username")
    val username: String? = null,

    @ColumnInfo(name = "tanggal_input")
    val tanggalEntry: String? = null

) : Parcelable