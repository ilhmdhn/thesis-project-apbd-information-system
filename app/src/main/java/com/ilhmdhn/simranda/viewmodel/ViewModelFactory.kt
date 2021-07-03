package com.ilhmdhn.simranda.viewmodel

import android.content.Context
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.ilhmdhn.simranda.data.DataRepository
import com.ilhmdhn.simranda.di.Injection
import com.ilhmdhn.simranda.ui.anggaran.AnggaranViewModel
import com.ilhmdhn.simranda.ui.laporan.LaporanViewModel
import com.ilhmdhn.simranda.ui.master.organisasi.OrganisasiViewModel
import com.ilhmdhn.simranda.ui.master.programdankegiatan.ProgramDanKegiatanViewModel
import com.ilhmdhn.simranda.ui.master.rekening.RekeningViewModel
import com.ilhmdhn.simranda.ui.user.UserViewModel

class ViewModelFactory private constructor(private val mDataRepository: DataRepository) :
    ViewModelProvider.NewInstanceFactory() {

    companion object {
        @Volatile
        private var instance: ViewModelFactory? = null

        fun getInstance(context: Context): ViewModelFactory =
            instance ?: synchronized(this) {
                instance ?: ViewModelFactory(Injection.provideRepository(context))
            }
    }

    @Suppress("UNCHECKED_CAST")
    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        return when {
            modelClass.isAssignableFrom(RekeningViewModel::class.java) -> RekeningViewModel(
                mDataRepository
            ) as T
            modelClass.isAssignableFrom(OrganisasiViewModel::class.java) -> OrganisasiViewModel(
                mDataRepository
            ) as T
            modelClass.isAssignableFrom(ProgramDanKegiatanViewModel::class.java) -> ProgramDanKegiatanViewModel(
                mDataRepository
            ) as T

            modelClass.isAssignableFrom(AnggaranViewModel::class.java) -> AnggaranViewModel(
                mDataRepository
            ) as T

            modelClass.isAssignableFrom(LaporanViewModel::class.java) -> LaporanViewModel(
                mDataRepository
            ) as T

            modelClass.isAssignableFrom(UserViewModel::class.java) -> UserViewModel() as T

            else -> throw IllegalArgumentException("Unknown ViewModel class: " + modelClass.name)
        }
    }
}