package com.ilhmdhn.simranda.data.source.local.room

import androidx.lifecycle.LiveData
import androidx.paging.DataSource
import androidx.room.*
import com.ilhmdhn.simranda.data.source.local.entity.*

@Dao
interface SimrandaDao {
    // db rekening
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertRekening(rekening: List<RekeningEntity>)

    @Query("SELECT * FROM rekeningentities WHERE tahun = :tahun")
    fun getRekening(tahun: String): DataSource.Factory<Int, RekeningEntity>

    @Update
    fun updateRekening(rekening: RekeningEntity)

    @Query("SELECT * FROM rekeningentities WHERE  nama_rekening LIKE '%'||:search||'%' OR kode_rekening LIKE '%'||:search||'%'")
    fun getSearchRekening(search: String?): DataSource.Factory<Int, RekeningEntity>

    @Query("DELETE FROM rekeningentities")
    fun deleteRekening()

    @Query("SELECT * FROM rekeningentities where length(kode_rekening)= 7")
    fun getSelectRekening(): DataSource.Factory<Int, RekeningEntity>

    @Query("SELECT * FROM rekeningentities where length(kode_rekening)= 7 AND (kode_rekening LIKE '%'|| :search ||'%' OR nama_rekening LIKE '%' || :search ||'%' OR urutan LIKE '%' || :search ||'%')")
    fun getSelectSearchRekening(search: String?): DataSource.Factory<Int, RekeningEntity>


    //db organisasi
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertOrganisasi(organisasi: List<OrganisasiEntity>)

    @Query("SELECT * FROM organisasientities")
    fun getOrganisasi(): DataSource.Factory<Int, OrganisasiEntity>

    @Query("SELECT * FROM organisasientities where length (kode_organisasi) = 5")
    fun getSelectOrganisasi(): DataSource.Factory<Int, OrganisasiEntity>

    @Query("SELECT * FROM organisasientities where length (kode_organisasi) = 5 AND (kode_organisasi LIKE '%'||:search||'%' OR nama_organisasi LIKE '%'||:search||'%' OR kode_bagian LIKE '%'||:search||'%' OR nama_bagian LIKE '%'||:search||'%')")
    fun getSelectSearchOrganisasi(search: String?): DataSource.Factory<Int, OrganisasiEntity>

    @Update
    fun updateOrganisasi(organisasi: OrganisasiEntity)

    @Query("DELETE FROM organisasientities")
    fun deleteOrganisasi()

    @Query("SELECT * FROM organisasientities WHERE kode_organisasi LIKE '%'|| :search ||'%' OR nama_organisasi LIKE '%' || :search || '%'")
    fun searchOrganisasi(search: String?): DataSource.Factory<Int, OrganisasiEntity>

    //db program dan kegiatan

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertKegiatan(kegiatan: List<ProgramDanKegiatanEntity>)

    @Query("SELECT * FROM programdankegiatanentities")
    fun getKegiatan(): DataSource.Factory<Int, ProgramDanKegiatanEntity>

    @Query("SELECT * FROM programdankegiatanentities WHERE length (kode_kegiatan) = 6 AND kode_organisasi LIKE '%'||:kodeOrg||'%'")
    fun getSelectKeg(kodeOrg: String?): DataSource.Factory<Int, ProgramDanKegiatanEntity>

    @Query("SELECT * FROM programdankegiatanentities WHERE length(kode_kegiatan = 6) AND kode_organisasi = :kodeOrg AND (kode_kegiatan LIKE '%'||:search||'%' OR kode_organisasi LIKE '%'||:search||'%' OR nama_program LIKE '%'||:search||'%' OR nama_kegiatan LIKE '%'||:search||'%')")
    fun getSelectSearchKeg(
        kodeOrg: String?,
        search: String?
    ): DataSource.Factory<Int, ProgramDanKegiatanEntity>

    @Update
    fun updateKegiatan(kegiatan: ProgramDanKegiatanEntity)

    @Query("DELETE FROM programdankegiatanentities")
    fun deleteKegiatan()

    @Query("SELECT * FROM programdankegiatanentities WHERE kode_kegiatan LIKE '%' || :search || '%' OR kode_organisasi LIKE '%' || :search || '%' OR kode_kegiatan LIKE '%' || :search || '%' OR nama_program LIKE '%' || :search || '%' OR nama_program LIKE '%' || :search ||'%'")
    fun searchKegiatan(search: String?): DataSource.Factory<Int, ProgramDanKegiatanEntity>

    @Query("SELECT nama_organisasi FROM organisasientities WHERE kode_organisasi = :search")
    fun getNameOrg(search: String): LiveData<getNameOrgModel>


    //db anggaran
    @Query("SELECT * FROM anggaranentities WHERE kode_dokumen =:search ")
    fun getAnggaran(search: String?): DataSource.Factory<Int, AnggaranEntity>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertAnggaran(anggaran: List<AnggaranEntity>)

    @Query("DELETE FROM anggaranentities Where kode_dokumen = :kodeDok")
    fun deleteAnggaran(kodeDok: String)

    @Query("SELECT * FROM anggaranentities WHERE kode_dokumen = :kodeDok AND (id LIKE '%' || :search||'%' OR kode_rekening LIKE '%'||:search||'%' OR kode_organisasi LIKE '%'||:search||'%' OR kode_kegiatan LIKE '%'||:search||'%')")
    fun getSearchAnggaran(kodeDok: String, search: String?): DataSource.Factory<Int, AnggaranEntity>

    @Query("SELECT R.nama_rekening, O.nama_organisasi, O.nama_bagian, K.nama_program, K.nama_kegiatan from anggaranentities A, rekeningentities R, organisasientities O, programdankegiatanentities K where A.kode_rekening = R.kode_rekening and A.kode_organisasi = O.kode_organisasi and A.kode_kegiatan = K.kode_kegiatan and A.kode_organisasi = K.kode_organisasi and A.id = :id")
    fun getName(id: String): LiveData<getNameModel>


    // Anggaran Dokumen

    @Query("SELECT * FROM anggarandokumenentities WHERE kode_dokumen = :kodeDok AND tahun = :tahun")
    fun getAnggaranDokumen(
        kodeDok: String,
        tahun: String
    ): DataSource.Factory<Int, AnggaranDokumenEntity>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertAnggaranDokumen(anggaran: List<AnggaranDokumenEntity>)

    @Query("DELETE FROM anggarandokumenentities WHERE kode_dokumen = :kodeDok AND tahun = :tahun")
    fun deleteAnggaranDokumen(kodeDok: String, tahun: String)

//    Ringkasan Anggaran


    //    Filter Anggaran
    @Query("SELECT * FROM anggaranrekeningentities WHERE kode_rekening = :kodeRek and kode_dokumen = :kodeDok AND tahun = :tahun")
    fun getAnggaranRekening(
        kodeRek: String,
        kodeDok: String,
        tahun: String
    ): LiveData<AnggaranRekeningEntity>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertAnggaranRekening(anggaranRekeningEntity: AnggaranRekeningEntity)

    @Query("DELETE FROM anggarandokumenentities WHERE kode_rekening = :kodeRek AND kode_dokumen = :kodeDok AND tahun = :tahun")
    fun deleteAnggaranRekening(kodeRek: String, kodeDok: String, tahun: String)
}