package com.ilhmdhn.simranda.ui.anggaran

import android.content.Context
import android.graphics.pdf.PdfDocument
import android.widget.Toast
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.paging.PagedList
import com.ilhmdhn.simranda.data.DataRepository
import com.ilhmdhn.simranda.data.source.local.entity.*
import com.ilhmdhn.simranda.data.source.local.room.SimrandaDao
import com.ilhmdhn.simranda.data.source.remote.ApiResponse
import com.ilhmdhn.simranda.data.source.remote.response.AnggaranDeleteResponse
import com.ilhmdhn.simranda.data.source.remote.response.AnggaranPostResponse
import com.ilhmdhn.simranda.data.source.remote.response.AnggaranUpdateResponse
import com.ilhmdhn.simranda.data.source.remote.response.organisasiPostResponse
import com.ilhmdhn.simranda.utils.ApiConfig
import com.ilhmdhn.simranda.vo.Resource
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class AnggaranViewModel(private val dataRepository: DataRepository) : ViewModel() {

    fun getAnggaran(
        key: String,
        year: String,
        kodeDok: String
    ): LiveData<Resource<PagedList<AnggaranEntity>>> {
        return dataRepository.getAnggaran(key, year, kodeDok)
    }

    fun getName(id: String): LiveData<getNameModel> = dataRepository.getName(id)

    fun getSearchAnggaran(kodeDok: String, search: String?): LiveData<PagedList<AnggaranEntity>> =
        dataRepository.getSearchAnggaran(kodeDok, search)

    fun updateAnggaran(key: String, id: String, nominal: String, context: Context) {
        val client = ApiConfig.getApiService().updateAnggaran(id, key, nominal)
        client.enqueue(object : Callback<AnggaranUpdateResponse> {
            override fun onResponse(
                call: Call<AnggaranUpdateResponse>,
                response: Response<AnggaranUpdateResponse>
            ) {
                if (response.isSuccessful) {
                    Toast.makeText(context, "Succes", Toast.LENGTH_SHORT).show()
                } else {
                    Toast.makeText(context, "Server return error", Toast.LENGTH_SHORT).show()
                }
            }

            override fun onFailure(call: Call<AnggaranUpdateResponse>, t: Throwable) {
                Toast.makeText(
                    context,
                    "onFailure: ${t.message.toString()} ${call}",
                    Toast.LENGTH_SHORT
                ).show()
            }
        })
    }

    fun deleteAnggaran(key: String, id: String, context: Context) {
        val client = ApiConfig.getApiService().deleteAnggaran(id, key)
        client.enqueue(object : Callback<AnggaranDeleteResponse> {
            override fun onResponse(
                call: Call<AnggaranDeleteResponse>,
                response: Response<AnggaranDeleteResponse>
            ) {
                if (response.isSuccessful) {
                    Toast.makeText(context, "Succes", Toast.LENGTH_SHORT).show()
                } else {
                    Toast.makeText(context, "Server return error", Toast.LENGTH_SHORT).show()
                }
            }

            override fun onFailure(call: Call<AnggaranDeleteResponse>, t: Throwable) {
                Toast.makeText(
                    context,
                    "onFailure: ${t.message.toString()} ${call}",
                    Toast.LENGTH_SHORT
                ).show()
            }
        })
    }

    fun postAnggaran(
        key: String,
        tahun: String,
        userId: String,
        kodeDokumen: String,
        halDokumen: String,
        kodeOrg: String,
        kodeBag: String,
        kodeKeg: String,
        kodeRek: String,
        nominalAnggaran: String,
        context: Context
    ) {
        val client = ApiConfig.getApiService().postAnggaran(
            key,
            tahun,
            userId,
            kodeDokumen,
            halDokumen,
            kodeOrg,
            kodeBag,
            kodeKeg,
            kodeRek,
            nominalAnggaran
        )
        client.enqueue(object : Callback<AnggaranPostResponse> {
            override fun onResponse(
                call: Call<AnggaranPostResponse>,
                response: Response<AnggaranPostResponse>
            ) {
                if (response.isSuccessful) {
                    Toast.makeText(context, "Succes", Toast.LENGTH_SHORT).show()
                } else {
                    Toast.makeText(context, "Server return error", Toast.LENGTH_SHORT).show()
                }
            }

            override fun onFailure(call: Call<AnggaranPostResponse>, t: Throwable) {
                Toast.makeText(
                    context,
                    "onFailure: ${t.message.toString()} ${call}",
                    Toast.LENGTH_SHORT
                ).show()
            }

        })
    }

    fun getSelectRekening(
        key: String,
        year: String
    ): LiveData<Resource<PagedList<RekeningEntity>>> = dataRepository.getSelectRekening(key, year)

    fun getSelectSearchRekening(search: String?): LiveData<PagedList<RekeningEntity>> =
        dataRepository.getSelectSearchRekening(search)

    fun getSelectKegiatan(
        key: String,
        year: String,
        kodeOrg: String?
    ): LiveData<Resource<PagedList<ProgramDanKegiatanEntity>>> =
        dataRepository.getSelectKeg(key, year, kodeOrg)

    fun getSelectSearchKegiatan(
        kodeOrg: String,
        search: String?
    ): LiveData<PagedList<ProgramDanKegiatanEntity>> =
        dataRepository.getSelectSearchKeg(kodeOrg, search)
}