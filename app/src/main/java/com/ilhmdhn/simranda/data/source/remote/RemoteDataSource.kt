package com.ilhmdhn.simranda.data.source.remote

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.ilhmdhn.simranda.data.source.remote.response.*
import com.ilhmdhn.simranda.utils.ApiConfig
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class RemoteDataSource {

    private var apiConfig = ApiConfig

    companion object {
        private const val TAG = "DataSource"

        @Volatile
        private var instance: RemoteDataSource? = null

        fun getInstance(): RemoteDataSource =
            instance ?: synchronized(this) {
                instance ?: RemoteDataSource()
            }
    }

    fun getRekening(key: String, year: String): LiveData<ApiResponse<List<DataItemRekening>>> {
        val resultRekening = MutableLiveData<ApiResponse<List<DataItemRekening>>>()
        apiConfig.getApiService().getRekening(key, year)
            .enqueue(object : Callback<RekeningResponse> {
                override fun onResponse(
                    call: Call<RekeningResponse>,
                    response: Response<RekeningResponse>
                ) {
                    if (response.isSuccessful) {
                        resultRekening.postValue(ApiResponse.succes(response.body()?.data as List<DataItemRekening>))
                    } else {
                        ApiResponse.empty(response.message(), response.body())
                        Log.e("datane", response.message())
                    }
                }

                override fun onFailure(call: Call<RekeningResponse>, t: Throwable) {
                    ApiResponse.error("${t.message}", null)
                    Log.e(TAG, "${t.message}")
                }
            })
        return resultRekening
    }

    fun getOrganisasi(key: String, year: String): LiveData<ApiResponse<List<DataItemOrganisasi>>> {
        val resultOrganisasi = MutableLiveData<ApiResponse<List<DataItemOrganisasi>>>()
        val client = ApiConfig.getApiService().getOrganisasi(key, year)
        client.enqueue(object : Callback<OrganisasiResponse> {
            override fun onResponse(
                call: Call<OrganisasiResponse>,
                response: Response<OrganisasiResponse>
            ) {
                if (response.isSuccessful) {
                    resultOrganisasi.postValue(
                        ApiResponse.succes(
                            response.body()
                                ?.data as List<DataItemOrganisasi>
                        )
                    )
                } else {
                    ApiResponse.empty(response.message(), response.body())
                    Log.e(TAG, response.message())
                }
            }

            override fun onFailure(call: Call<OrganisasiResponse>, t: Throwable) {
                ApiResponse.error("${t.message}", null)
                Log.e(TAG, "${t.message}")
            }
        })
        return resultOrganisasi
    }

    fun getKegiatan(
        key: String,
        year: String
    ): LiveData<ApiResponse<List<DataItemProgramDanKegiatan>>> {
        val resultKegiatan = MutableLiveData<ApiResponse<List<DataItemProgramDanKegiatan>>>()
        apiConfig.getApiService().getKegiatan(key, year)
            .enqueue(object : Callback<ProgramDanKegiatanResponse> {
                override fun onResponse(
                    call: Call<ProgramDanKegiatanResponse>,
                    response: Response<ProgramDanKegiatanResponse>
                ) {
                    if (response.isSuccessful) {
                        resultKegiatan.postValue(
                            ApiResponse.succes(
                                response.body()
                                    ?.data as List<DataItemProgramDanKegiatan>
                            )
                        )
                    } else {
                        ApiResponse.empty(response.message(), response.body())
                        Log.e(TAG, response.message())
                    }
                }

                override fun onFailure(call: Call<ProgramDanKegiatanResponse>, t: Throwable) {
                    ApiResponse.error("${t.message}", null)
                    Log.e(TAG, "${t.message}")
                }
            })
        return resultKegiatan
    }

    fun getAnggaran(
        key: String,
        year: String,
        kodeDokumen: String
    ): LiveData<ApiResponse<List<DataItemAnggaran>>> {
        val resultAnggaran = MutableLiveData<ApiResponse<List<DataItemAnggaran>>>()
        apiConfig.getApiService().getAnggaran(key, year, kodeDokumen)
            .enqueue(object : Callback<AnggaranResponse> {
                override fun onResponse(
                    call: Call<AnggaranResponse>,
                    response: Response<AnggaranResponse>
                ) {
                    if (response.isSuccessful) {
                        resultAnggaran.postValue(
                            ApiResponse.succes(
                                response.body()
                                    ?.data as List<DataItemAnggaran>
                            )
                        )
                    } else {
                        ApiResponse.empty(response.message(), response.body())
                        Log.e(TAG, response.message())
                    }
                }

                override fun onFailure(call: Call<AnggaranResponse>, t: Throwable) {
                    ApiResponse.error("${t.message}", null)
                    Log.e(TAG, "${t.message}")
                }
            })
        return resultAnggaran
    }

    fun getAnggaranDokumen(
        key: String,
        kodeDok: String,
        tahun: String
    ): LiveData<ApiResponse<List<DataItemAnggaranDokumen>>> {
        val resultAnggaran = MutableLiveData<ApiResponse<List<DataItemAnggaranDokumen>>>()
        val client = ApiConfig.getApiService().getAnggaranDokumen(key, kodeDok, tahun)
        client.enqueue(object : Callback<AnggaranDokumenResponse> {
            override fun onResponse(
                call: Call<AnggaranDokumenResponse>,
                response: Response<AnggaranDokumenResponse>
            ) {
                if (response.isSuccessful) {
                    resultAnggaran.postValue(
                        ApiResponse.succes(
                            response.body()
                                ?.data as List<DataItemAnggaranDokumen>
                        )
                    )
                } else {
                    ApiResponse.empty(response.message(), response.body())
                    Log.e(TAG, response.message())
                }
            }

            override fun onFailure(call: Call<AnggaranDokumenResponse>, t: Throwable) {
                ApiResponse.error("${t.message}", null)
                Log.e(TAG, "${t.message}")
            }
        })
        return resultAnggaran
    }

    fun getAnggaranOrganisasi(
        key: String,
        kodeDok: String,
        tahun: String
    ): LiveData<ApiResponse<List<DataItemAnggaranSkpd>>> {
        val resultAnggaran = MutableLiveData<ApiResponse<List<DataItemAnggaranSkpd>>>()
        val client = ApiConfig.getApiService().getAnggaranSkpd(key, kodeDok, tahun)
        client.enqueue(object : Callback<AnggaranSkpdResponse> {
            override fun onResponse(
                call: Call<AnggaranSkpdResponse>,
                response: Response<AnggaranSkpdResponse>
            ) {
                if (response.isSuccessful) {
                    resultAnggaran.postValue(
                        ApiResponse.succes(
                            response.body()
                                ?.data as List<DataItemAnggaranSkpd>
                        )
                    )
                } else {
                    ApiResponse.empty(response.message(), response.body())
                    Log.e(TAG, response.message())
                }
            }

            override fun onFailure(call: Call<AnggaranSkpdResponse>, t: Throwable) {
                ApiResponse.error("${t.message}", null)
                Log.e(TAG, "${t.message}")
            }
        })
        return resultAnggaran
    }

    fun getAnggaranRekening(
        key: String,
        kodeRek: String,
        kodeDok: String,
        tahun: String
    ): LiveData<ApiResponse<DataAnggaranRekening>> {
        val resultFilterAnggaran = MutableLiveData<ApiResponse<DataAnggaranRekening>>()
        val client = ApiConfig.getApiService().getAnggaranRekening(key, kodeRek, kodeDok, tahun)
        client.enqueue(object : Callback<DataAnggaranRekeningResponse> {
            override fun onResponse(
                call: Call<DataAnggaranRekeningResponse>,
                rekeningResponse: Response<DataAnggaranRekeningResponse>
            ) {
                if (rekeningResponse.isSuccessful) {
                    resultFilterAnggaran.value = (ApiResponse.succes(
                        rekeningResponse.body()
                            ?.dataRekening!!
                    ))
                } else {
                    Log.e(TAG, "onFailure: ${rekeningResponse.message()}")
                }
            }

            override fun onFailure(call: Call<DataAnggaranRekeningResponse>, t: Throwable) {
                ApiResponse.error("${t.message}", null)
                Log.e(TAG, "${t.message}")
            }
        })
        return resultFilterAnggaran
    }
}