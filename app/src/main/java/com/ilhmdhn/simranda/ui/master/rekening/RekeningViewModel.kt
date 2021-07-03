package com.ilhmdhn.simranda.ui.master.rekening

import android.content.Context
import android.widget.Toast
import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.paging.PagedList
import com.ilhmdhn.simranda.data.DataRepository
import com.ilhmdhn.simranda.data.source.local.entity.RekeningEntity
import com.ilhmdhn.simranda.data.source.remote.response.RekeningChangeResponse
import com.ilhmdhn.simranda.data.source.remote.response.RekeningPostResponse
import com.ilhmdhn.simranda.utils.ApiConfig
import com.ilhmdhn.simranda.vo.Resource
import retrofit2.Call
import retrofit2.Response
import retrofit2.Callback as Callback1

class RekeningViewModel(private val dataRepository: DataRepository) : ViewModel() {

    fun getRekening(key: String, year: String): LiveData<Resource<PagedList<RekeningEntity>>> =
        dataRepository.getRekening(key, year)

    fun getSearchRekening(search: String?): LiveData<PagedList<RekeningEntity>> =
        dataRepository.getSearchRekening(search)

    fun postRekening(
        key: String,
        tahun: String,
        kodeRekening: String,
        namaRekening: String,
        userId: String,
        context: Context
    ) {
        val client = ApiConfig.getApiService()
            .postRekening(key, tahun, kodeRekening, namaRekening, (kodeRekening + tahun), userId)
        client.enqueue(object : Callback1<RekeningPostResponse> {
            override fun onResponse(
                call: Call<RekeningPostResponse>,
                response: Response<RekeningPostResponse>
            ) {
                if (response.isSuccessful) {
                    Toast.makeText(context, "Succes", Toast.LENGTH_SHORT).show()
                } else {
                    Toast.makeText(context, "Gagal", Toast.LENGTH_SHORT).show()
                }
            }

            override fun onFailure(call: Call<RekeningPostResponse>, t: Throwable) {
                Toast.makeText(context, "onFailure: ${t.message.toString()}", Toast.LENGTH_LONG)
                    .show()
            }
        })
    }

    fun deleteRekening(key: String, urut: String, context: Context) {
        val client = ApiConfig.getApiService().deleteRekening(urut, key)
        client.enqueue(object : Callback1<RekeningChangeResponse> {
            override fun onResponse(
                call: Call<RekeningChangeResponse>,
                response: Response<RekeningChangeResponse>
            ) {
                if (response.isSuccessful) {
                    Toast.makeText(context, "Success", Toast.LENGTH_SHORT).show()
                } else {
                    Toast.makeText(context, response.body()?.message.toString(), Toast.LENGTH_SHORT)
                        .show()
                }
            }

            override fun onFailure(call: Call<RekeningChangeResponse>, t: Throwable) {
                Toast.makeText(
                    context,
                    "onFailure: ${t.message.toString()} ${call}",
                    Toast.LENGTH_LONG
                ).show()
            }
        })
    }

    fun updateRekening(key: String, urut: String, keterangan: String, context: Context) {
        val client = ApiConfig.getApiService().updateRekening(urut, keterangan, key)
        client.enqueue(object : retrofit2.Callback<RekeningChangeResponse> {
            override fun onResponse(
                call: Call<RekeningChangeResponse>,
                response: Response<RekeningChangeResponse>
            ) {
                if (response.isSuccessful) {
                    Toast.makeText(context, "Succes", Toast.LENGTH_SHORT).show()
                } else {
                    Toast.makeText(context, "Gagal", Toast.LENGTH_SHORT).show()
                }
            }

            override fun onFailure(call: Call<RekeningChangeResponse>, t: Throwable) {
                Toast.makeText(
                    context,
                    "onFailure: ${t.message.toString()} ${call}",
                    Toast.LENGTH_LONG
                ).show()
            }
        })
    }
}