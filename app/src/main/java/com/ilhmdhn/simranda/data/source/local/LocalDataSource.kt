package com.ilhmdhn.simranda.data.source.local

import androidx.lifecycle.LiveData
import androidx.paging.DataSource
import com.ilhmdhn.simranda.data.source.local.entity.*
import com.ilhmdhn.simranda.data.source.local.room.SimrandaDao

class LocalDataSource private constructor(private val mSimrandaDao: SimrandaDao) {

    companion object {
        private val INSTANCE: LocalDataSource? = null

        fun getInstance(simrandaDao: SimrandaDao): LocalDataSource =
            INSTANCE ?: LocalDataSource(simrandaDao)
    }

    //Rekening
    fun insertRekening(rekening: List<RekeningEntity>) = mSimrandaDao.insertRekening(rekening)

    fun getRekening(year: String): DataSource.Factory<Int, RekeningEntity> =
        mSimrandaDao.getRekening(year)

    fun getSelectRekening(): DataSource.Factory<Int, RekeningEntity> =
        mSimrandaDao.getSelectRekening()

    fun getSelectSearchRekening(search: String?): DataSource.Factory<Int, RekeningEntity> =
        mSimrandaDao.getSelectSearchRekening(search)

    fun getSearchRekening(search: String?): DataSource.Factory<Int, RekeningEntity> =
        mSimrandaDao.getSearchRekening(search)

    fun deleteRekening() = mSimrandaDao.deleteRekening()

    //Organisasi
    fun insertOrganisasi(organisasi: List<OrganisasiEntity>) =
        mSimrandaDao.insertOrganisasi(organisasi)

    fun getOrganisasi(): DataSource.Factory<Int, OrganisasiEntity> = mSimrandaDao.getOrganisasi()

    fun getSearchOrganisasi(search: String?): DataSource.Factory<Int, OrganisasiEntity> =
        mSimrandaDao.searchOrganisasi(search)

    fun deleteOrganisasi() = mSimrandaDao.deleteOrganisasi()

    fun getSelectOrg(): DataSource.Factory<Int, OrganisasiEntity> =
        mSimrandaDao.getSelectOrganisasi()

    fun getSelectSearchOrg(search: String?): DataSource.Factory<Int, OrganisasiEntity> =
        mSimrandaDao.getSelectSearchOrganisasi(search)

    fun getNameOrg(search: String): LiveData<getNameOrgModel> = mSimrandaDao.getNameOrg(search)

    //Program dan Kegiatan
    fun insertKegiatan(kegiatan: List<ProgramDanKegiatanEntity>) =
        mSimrandaDao.insertKegiatan(kegiatan)

    fun getKegiatan(): DataSource.Factory<Int, ProgramDanKegiatanEntity> =
        mSimrandaDao.getKegiatan()

    fun getSelectKeg(kodeOrg: String?): DataSource.Factory<Int, ProgramDanKegiatanEntity> =
        mSimrandaDao.getSelectKeg(kodeOrg)

    fun getSelectSearchKeg(
        kodeOrg: String?,
        search: String?
    ): DataSource.Factory<Int, ProgramDanKegiatanEntity> =
        mSimrandaDao.getSelectSearchKeg(kodeOrg, search)

    fun getSearchKegiatan(search: String?): DataSource.Factory<Int, ProgramDanKegiatanEntity> =
        mSimrandaDao.searchKegiatan(search)

    fun deleteKegiatan() = mSimrandaDao.deleteKegiatan()

    fun getName(id: String) = mSimrandaDao.getName(id)

    //Anggaran
    fun getAnggaran(search: String?) = mSimrandaDao.getAnggaran(search)

    fun insertAnggaran(anggaran: List<AnggaranEntity>) = mSimrandaDao.insertAnggaran(anggaran)

    fun deleteAnggaran(kodeDok: String) = mSimrandaDao.deleteAnggaran(kodeDok)

    fun getSearchAnggaran(
        kodeDok: String,
        search: String?
    ): DataSource.Factory<Int, AnggaranEntity> = mSimrandaDao.getSearchAnggaran(kodeDok, search)

    //Laporan

    //Ringkasan Anggaran

    fun getAnggaranDokumen(kodeDok: String, tahun: String) =
        mSimrandaDao.getAnggaranDokumen(kodeDok, tahun)

    fun insertAnggaranDokumen(anggaran: List<AnggaranDokumenEntity>) =
        mSimrandaDao.insertAnggaranDokumen(anggaran)

    fun deleteAnggaranDokumen(kodeDok: String, tahun: String) =
        mSimrandaDao.deleteAnggaranDokumen(kodeDok, tahun)

//    FilterAnggaran

    fun getAnggaranRekening(
        kodeRek: String,
        kodeDok: String,
        tahun: String
    ): LiveData<AnggaranRekeningEntity> = mSimrandaDao.getAnggaranRekening(kodeRek, kodeDok, tahun)

    fun insertFilterAnggaran(anggaran: AnggaranRekeningEntity) =
        mSimrandaDao.insertAnggaranRekening(anggaran)

    fun deleteAnggaranRekening(kodeRek: String, kodeDok: String, tahun: String) =
        mSimrandaDao.deleteAnggaranRekening(kodeRek, kodeDok, tahun)
}