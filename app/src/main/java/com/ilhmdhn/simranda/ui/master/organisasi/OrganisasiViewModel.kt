package com.ilhmdhn.simranda.ui.master.organisasi

import android.content.Context
import android.widget.Toast
import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.paging.PagedList
import com.ilhmdhn.simranda.data.DataRepository
import com.ilhmdhn.simranda.data.source.local.entity.OrganisasiEntity
import com.ilhmdhn.simranda.data.source.remote.response.OrganisasiResponse
import com.ilhmdhn.simranda.data.source.remote.response.organisasiDeleteResponse
import com.ilhmdhn.simranda.data.source.remote.response.organisasiPostResponse
import com.ilhmdhn.simranda.utils.ApiConfig
import com.ilhmdhn.simranda.vo.Resource
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class OrganisasiViewModel(private val dataRepository: DataRepository) : ViewModel() {

    fun getOrganisasi(key: String, year: String): LiveData<Resource<PagedList<OrganisasiEntity>>> =
        dataRepository.getOrganisasi(key, year)

    fun getSearchOrganisasi(search: String?): LiveData<PagedList<OrganisasiEntity>> =
        dataRepository.getSearchOrganisasi(search)

    fun postOrganisasi(
        key: String,
        tahun: String,
        kodeOrg: String,
        namaOrg: String,
        kodeBag: String,
        namaBag: String,
        namaFungsi: String,
        userId: String,
        context: Context
    ) {
        val client = ApiConfig.getApiService().postOrganisasi(
            key,
            tahun,
            kodeOrg,
            namaOrg,
            kodeBag,
            namaBag,
            namaFungsi,
            (kodeOrg + kodeBag),
            userId
        )
        client.enqueue(object : Callback<organisasiPostResponse> {
            override fun onResponse(
                call: Call<organisasiPostResponse>,
                response: Response<organisasiPostResponse>
            ) {
                if (response.isSuccessful) {
                    Toast.makeText(context, "succes", Toast.LENGTH_SHORT).show()
                } else {
                    Toast.makeText(context, "server return error", Toast.LENGTH_SHORT).show()
                }
            }

            override fun onFailure(call: Call<organisasiPostResponse>, t: Throwable) {
                Toast.makeText(context, "onFailure: ${t.message.toString()}", Toast.LENGTH_SHORT)
                    .show()
            }

        })
    }

    fun updateOrganisasi(
        key: String,
        urut: String,
        namaOrg: String,
        kodeBag: String,
        namaBag: String,
        fungsi: String,
        context: Context
    ) {
        val client =
            ApiConfig.getApiService().updateOrganisasi(urut, key, namaOrg, kodeBag, namaBag, fungsi)
        client.enqueue(object : Callback<organisasiPostResponse> {
            override fun onResponse(
                call: Call<organisasiPostResponse>, response: Response<organisasiPostResponse>
            ) {
                if (response.isSuccessful) {
                    Toast.makeText(context, "Succes", Toast.LENGTH_SHORT).show()
                } else {
                    Toast.makeText(context, "Server return error", Toast.LENGTH_SHORT).show()
                }
            }

            override fun onFailure(call: Call<organisasiPostResponse>, t: Throwable) {
                Toast.makeText(context, "onFailure ${t.message.toString()}", Toast.LENGTH_SHORT)
                    .show()
            }
        })
    }

    fun deleteOrganisasi(key: String, urut: String, context: Context) {
        val client = ApiConfig.getApiService().deleteOrganisasi(urut, key)
        client.enqueue(object : Callback<organisasiDeleteResponse> {
            override fun onResponse(
                call: Call<organisasiDeleteResponse>, response: Response<organisasiDeleteResponse>
            ) {
                if (response.isSuccessful) {
                    Toast.makeText(context, "Succes", Toast.LENGTH_SHORT).show()
                } else {
                    Toast.makeText(context, "Server return error", Toast.LENGTH_SHORT).show()
                }
            }

            override fun onFailure(call: Call<organisasiDeleteResponse>, t: Throwable) {
                Toast.makeText(context, "onFailure ${t.message.toString()}", Toast.LENGTH_SHORT)
                    .show()
            }
        })
    }
}