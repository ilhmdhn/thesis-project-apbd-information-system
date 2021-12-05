package com.ilhmdhn.simranda.data

import androidx.lifecycle.LiveData
import androidx.paging.PagedList
import com.ilhmdhn.simranda.data.source.local.entity.*
import com.ilhmdhn.simranda.vo.Resource

interface DataSource {
    fun getRekening(key: String, year: String, reload: Boolean): LiveData<Resource<PagedList<RekeningEntity>>>

    fun getSelectRekening(key: String, year: String, reload: Boolean): LiveData<Resource<PagedList<RekeningEntity>>>

    fun getSelectSearchRekening(search: String?): LiveData<PagedList<RekeningEntity>>

    fun getSearchRekening(search: String?): LiveData<PagedList<RekeningEntity>>

//    Org

    fun getOrganisasi(key: String, year: String, reload: Boolean): LiveData<Resource<PagedList<OrganisasiEntity>>>

    fun getSearchOrganisasi(search: String?): LiveData<PagedList<OrganisasiEntity>>


    fun getSelectOrg(key: String, year: String, reload: Boolean): LiveData<Resource<PagedList<OrganisasiEntity>>>

    fun getSelectSearchOrg(search: String?): LiveData<PagedList<OrganisasiEntity>>

    //KEGIATAN
    fun getKegiatan(key: String,year: String, reload: Boolean): LiveData<Resource<PagedList<ProgramDanKegiatanEntity>>>

    fun getSelectKeg(key: String, year: String,kodeOrg: String?, reload: Boolean): LiveData<Resource<PagedList<ProgramDanKegiatanEntity>>>

    fun getSelectSearchKeg(kodeOrg: String, search: String? ): LiveData<PagedList<ProgramDanKegiatanEntity>>

    fun getSearchKegiatan(search: String?): LiveData<PagedList<ProgramDanKegiatanEntity>>

    fun getNameOrg(search: String): LiveData<getNameOrgModel>


    //Anggaran
    fun getSearchAnggaran(kodeDok: String, search: String?): LiveData<PagedList<AnggaranEntity>>

    fun getAnggaran(key: String,year: String,kodeDokumen: String, reload: Boolean): LiveData<Resource<PagedList<AnggaranEntity>>>

    fun getName(id: String): LiveData<getNameModel>

    //Laporan
    fun getAnggaranDokumen(key: String,kodeDok: String,tahun: String, reload: Boolean): LiveData<Resource<PagedList<AnggaranDokumenEntity>>>

    //    FilterAnggaran
    fun getAnggaranRekening(key: String,kodeRek: String,kodeDok: String, tahun: String, reload: Boolean): LiveData<Resource<AnggaranRekeningEntity>>
}