package com.ilhmdhn.simranda.ui.laporan

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.paging.PagedList
import com.ilhmdhn.simranda.data.DataRepository
import com.ilhmdhn.simranda.data.source.local.entity.AnggaranDokumenEntity
import com.ilhmdhn.simranda.data.source.local.entity.AnggaranRekeningEntity
import com.ilhmdhn.simranda.data.source.local.entity.RekeningEntity
import com.ilhmdhn.simranda.vo.Resource

class LaporanViewModel(private val dataRepository: DataRepository) : ViewModel() {

    fun getAnggaranDokumen(
        key: String,
        kodeDok: String,
        tahun: String
    ): LiveData<Resource<PagedList<AnggaranDokumenEntity>>> =
        dataRepository.getAnggaranDokumen(key, kodeDok, tahun)

    fun getAnggaranRekening(
        key: String,
        kodeRek: String,
        kodeDok: String,
        tahun: String
    ): LiveData<Resource<AnggaranRekeningEntity>> =
        dataRepository.getAnggaranRekening(key, kodeRek, kodeDok, tahun)
}