package com.ilhmdhn.simranda.data

import androidx.lifecycle.LiveData
import androidx.paging.PagedList
import com.ilhmdhn.simranda.data.source.local.entity.*
import com.ilhmdhn.simranda.vo.Resource

interface DataSource {
    fun getRekening(key: String, year: String): LiveData<Resource<PagedList<RekeningEntity>>>

    fun getSelectRekening(key: String, year: String): LiveData<Resource<PagedList<RekeningEntity>>>

    fun getSelectSearchRekening(search: String?): LiveData<PagedList<RekeningEntity>>

    fun getSearchRekening(search: String?): LiveData<PagedList<RekeningEntity>>

//    Org

    fun getOrganisasi(key: String, year: String): LiveData<Resource<PagedList<OrganisasiEntity>>>

    fun getSearchOrganisasi(search: String?): LiveData<PagedList<OrganisasiEntity>>


    fun getSelectOrg(key: String, year: String): LiveData<Resource<PagedList<OrganisasiEntity>>>

    fun getSelectSearchOrg(search: String?): LiveData<PagedList<OrganisasiEntity>>

//    KEG

    fun getKegiatan(
        key: String,
        year: String
    ): LiveData<Resource<PagedList<ProgramDanKegiatanEntity>>>

    fun getSelectKeg(
        key: String,
        year: String,
        kodeOrg: String?
    ): LiveData<Resource<PagedList<ProgramDanKegiatanEntity>>>

    fun getSelectSearchKeg(
        kodeOrg: String,
        search: String?
    ): LiveData<PagedList<ProgramDanKegiatanEntity>>

    fun getSearchKegiatan(search: String?): LiveData<PagedList<ProgramDanKegiatanEntity>>

    fun getNameOrg(search: String): LiveData<getNameOrgModel>


//    Ang

    fun getSearchAnggaran(kodeDok: String, search: String?): LiveData<PagedList<AnggaranEntity>>

    fun getAnggaran(
        key: String,
        year: String,
        kodeDokumen: String
    ): LiveData<Resource<PagedList<AnggaranEntity>>>

    fun getName(id: String): LiveData<getNameModel>
//    Laporan

    fun getAnggaranDokumen(
        key: String,
        kodeDok: String,
        tahun: String
    ): LiveData<Resource<PagedList<AnggaranDokumenEntity>>>


    //    FilterAnggaran
    fun getAnggaranRekening(
        key: String,
        kodeRek: String,
        kodeDok: String,
        tahun: String
    ): LiveData<Resource<AnggaranRekeningEntity>>
}