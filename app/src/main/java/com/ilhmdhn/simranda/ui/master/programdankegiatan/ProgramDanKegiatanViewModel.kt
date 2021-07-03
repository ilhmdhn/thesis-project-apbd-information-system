package com.ilhmdhn.simranda.ui.master.programdankegiatan

import android.content.Context
import android.widget.Toast
import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.paging.PagedList
import com.ilhmdhn.simranda.data.DataRepository
import com.ilhmdhn.simranda.data.source.local.entity.OrganisasiEntity
import com.ilhmdhn.simranda.data.source.local.entity.ProgramDanKegiatanEntity
import com.ilhmdhn.simranda.data.source.local.entity.getNameOrgModel
import com.ilhmdhn.simranda.data.source.remote.response.KegiatanDeleteResponse
import com.ilhmdhn.simranda.data.source.remote.response.KegiatanPostResponse
import com.ilhmdhn.simranda.data.source.remote.response.KegiatanUpdateResponse
import com.ilhmdhn.simranda.utils.ApiConfig
import com.ilhmdhn.simranda.vo.Resource
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class ProgramDanKegiatanViewModel(private val dataRepository: DataRepository) : ViewModel() {

    fun getKegiatan(
        key: String,
        year: String
    ): LiveData<Resource<PagedList<ProgramDanKegiatanEntity>>> =
        dataRepository.getKegiatan(key, year)

    fun getSearchKegiatan(search: String?): LiveData<PagedList<ProgramDanKegiatanEntity>> =
        dataRepository.getSearchKegiatan(search)

    fun deleteKegiatan(key: String, urut: String, context: Context) {
        val client = ApiConfig.getApiService().deleteKegiatan(urut, key)
        client.enqueue(object : Callback<KegiatanDeleteResponse> {
            override fun onResponse(
                call: Call<KegiatanDeleteResponse>,
                response: Response<KegiatanDeleteResponse>
            ) {
                if (response.isSuccessful) {
                    Toast.makeText(context, "Succes", Toast.LENGTH_SHORT).show()
                } else {
                    Toast.makeText(context, "Server Return Error", Toast.LENGTH_SHORT).show()
                }
            }

            override fun onFailure(call: Call<KegiatanDeleteResponse>, t: Throwable) {
                Toast.makeText(context, "onFailure ${t.message.toString()}", Toast.LENGTH_SHORT)
                    .show()
            }
        })
    }

    fun updateKegiatan(
        key: String,
        urut: String,
        namaProg: String,
        namaKeg: String,
        context: Context
    ) {
        val client = ApiConfig.getApiService().updateKegiatan(urut, key, namaProg, namaKeg)
        client.enqueue(object : Callback<KegiatanUpdateResponse> {
            override fun onResponse(
                call: Call<KegiatanUpdateResponse>,
                response: Response<KegiatanUpdateResponse>
            ) {
                if (response.isSuccessful) {
                    Toast.makeText(context, "Succes", Toast.LENGTH_SHORT).show()
                } else {
                    Toast.makeText(context, "Server return error", Toast.LENGTH_SHORT).show()
                }
            }

            override fun onFailure(call: Call<KegiatanUpdateResponse>, t: Throwable) {
                Toast.makeText(context, "onFailure: ${t.message.toString()}", Toast.LENGTH_SHORT)
                    .show()
            }
        })
    }

    fun postKegiatan(
        key: String,
        tahun: String,
        kodeOrganisasi: String,
        kodeKegiatan: String,
        namaProgram: String,
        namaKegiatan: String,
        userId: String,
        context: Context
    ) {
        val client = ApiConfig.getApiService().postKegiatan(
            key,
            tahun,
            kodeOrganisasi,
            kodeKegiatan,
            namaProgram,
            namaKegiatan,
            (kodeOrganisasi + kodeKegiatan),
            userId
        )
        client.enqueue(object : Callback<KegiatanPostResponse> {
            override fun onResponse(
                call: Call<KegiatanPostResponse>,
                response: Response<KegiatanPostResponse>
            ) {
                if (response.isSuccessful) {
                    Toast.makeText(context, "Succes", Toast.LENGTH_SHORT).show()
                } else {
                    Toast.makeText(context, "Server return error", Toast.LENGTH_SHORT).show()
                }
            }

            override fun onFailure(call: Call<KegiatanPostResponse>, t: Throwable) {
                Toast.makeText(
                    context,
                    "onFailure: ${t.message.toString()} ${call}",
                    Toast.LENGTH_SHORT
                ).show()
            }

        })
    }

    fun getNameOrg(search: String): LiveData<getNameOrgModel> = dataRepository.getNameOrg(search)

    fun getSelectOrganisasi(
        key: String,
        year: String
    ): LiveData<Resource<PagedList<OrganisasiEntity>>> = dataRepository.getSelectOrg(key, year)

    fun getSelectSearchOrganisasi(search: String?): LiveData<PagedList<OrganisasiEntity>> =
        dataRepository.getSelectSearchOrg(search)
}