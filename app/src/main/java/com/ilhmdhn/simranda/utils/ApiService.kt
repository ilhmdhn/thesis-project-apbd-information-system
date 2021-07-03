package com.ilhmdhn.simranda.utils

import com.ilhmdhn.simranda.data.source.remote.response.*
import retrofit2.Call
import retrofit2.http.*


interface ApiService {

    //LOGIN
    @GET("login")
    fun login(
        @Query("key") key: String,
        @Query("username") id: String,
        @Query("password") password: String
    ): Call<LoginResponse>

    //Api User

    @GET("user")
    fun getUser(
        @Query("key") key: String,
    ): Call<UserResponse>

    @FormUrlEncoded
    @POST("user")
    fun createUser(
        @Field("username") username: String,
        @Field("fullname") fullname: String,
        @Field("level") level: String,
        @Field("password") password: String,
        @Field("key") key: String
    ): Call<createUserResponse>

    @FormUrlEncoded
    @PUT("user/{username}")
    fun updateUser(
        @Path("username") username: String,
        @Field("key") key: String,
        @Field("fullname") fullname: String,
        @Field("level") level: String,
        @Field("password") password: String
    ): Call<updateUserResponse>

    @DELETE("user/{username}")
    fun deleteUser(
        @Path("username") username: String,
        @Query("key") key: String
    ): Call<deleteUserResponse>

    //Api Rekening
    @GET("rekening")
    fun getRekening(
        @Query("key") key: String,
        @Query("tahun") tahun: String
    ): Call<RekeningResponse>

    @FormUrlEncoded
    @POST("rekening")
    fun postRekening(
        @Field("key") key: String,
        @Field("tahun") tahun: String,
        @Field("kode_rekening") kodeRekening: String,
        @Field("nama_rekening") namaRekening: String,
        @Field("urut") urut: String,
        @Field("username") userId: String
    ): Call<RekeningPostResponse>

    @DELETE("rekening/{urut}")
    fun deleteRekening(
        @Path("urut") urut: String,
        @Query("key") key: String
    ): Call<RekeningChangeResponse>

    @FormUrlEncoded
    @PUT("rekening/{urut}")
    fun updateRekening(
        @Path("urut") urut: String,
        @Field("nama_rekening") namaRekening: String,
        @Field("key") key: String
    ): Call<RekeningChangeResponse>


    //Api Organisasi
    @GET("organisasi")
    fun getOrganisasi(
        @Query("key") key: String,
        @Query("year") tahun: String
    ): Call<OrganisasiResponse>

    @FormUrlEncoded
    @POST("organisasi")
    fun postOrganisasi(
        @Field("key") key: String,
        @Field("tahun") tahun: String,
        @Field("kode_org") kodeOrg: String,
        @Field("nama_org") namaOrg: String,
        @Field("kode_bag") kodeBag: String,
        @Field("nama_bag") namaBag: String,
        @Field("nama_fungsi") namaFungsi: String,
        @Field("urut") urut: String,
        @Field("user_id") userId: String
    ): Call<organisasiPostResponse>

    @FormUrlEncoded
    @PUT("organisasi/{urut}")
    fun updateOrganisasi(
        @Path("urut") urut: String,
        @Field("key") key: String,
        @Field("nama_org") namaOrg: String,
        @Field("kode_bag") kodeOrg: String,
        @Field("nama_bag") namaBag: String,
        @Field("nama_fungsi") namaFungsi: String
    ): Call<organisasiPostResponse>

    @DELETE("organisasi/{urut}")
    fun deleteOrganisasi(
        @Path("urut") urut: String,
        @Query("key") key: String,
    ): Call<organisasiDeleteResponse>

    //Api Kegiatan
    @GET("kegiatan")
    fun getKegiatan(
        @Query("key") key: String,
        @Query("year") tahun: String
    ): Call<ProgramDanKegiatanResponse>

    @FormUrlEncoded
    @PUT("kegiatan/{urut}")
    fun updateKegiatan(
        @Path("urut") urut: String,
        @Query("key") key: String,
        @Field("nama_prog") namaProg: String,
        @Field("nama_keg") namaKeg: String
    ): Call<KegiatanUpdateResponse>

    @DELETE("kegiatan/{urut}")
    fun deleteKegiatan(
        @Path("urut") urut: String,
        @Query("key") key: String
    ): Call<KegiatanDeleteResponse>

    @FormUrlEncoded
    @POST("kegiatan")
    fun postKegiatan(
        @Field("key") key: String,
        @Field("tahun") tahun: String,
        @Field("kode_organisasi") kodeOrganisasi: String,
        @Field("kode_kegiatan") kodeKegiatan: String,
        @Field("nama_program") namaProgram: String,
        @Field("nama_kegiatan") namaKegiatan: String,
        @Field("urut") urut: String,
        @Field("user_id") userId: String,
    ): Call<KegiatanPostResponse>

    //Anggaran

    @GET("anggaran")
    fun getAnggaran(
        @Query("key") key: String,
        @Query("year") tahun: String,
        @Query("kode_dokumen") kodeDokumen: String
    ): Call<AnggaranResponse>

    @FormUrlEncoded
    @PUT("anggaran/{id}")
    fun updateAnggaran(
        @Path("id") id: String,
        @Query("key") key: String,
        @Field("nominal") nominal: String
    ): Call<AnggaranUpdateResponse>

    @DELETE("anggaran/{id}")
    fun deleteAnggaran(
        @Path("id") id: String,
        @Query("key") key: String
    ): Call<AnggaranDeleteResponse>

    @FormUrlEncoded
    @POST("anggaran")
    fun postAnggaran(
        @Field("key") key: String,
        @Field("tahun") tahun: String,
        @Field("user_id") userId: String,
        @Field("kode_dokumen") kodeDokumen: String,
        @Field("halaman_dokumen") halDokumen: String,
        @Field("kode_organisasi") kodeOrg: String,
        @Field("kode_bagian") kodeBag: String,
        @Field("kode_kegiatan") kodeKeg: String,
        @Field("kode_rekening") kodeRek: String,
        @Field("nominal_anggaran") nominalAnggaran: String
    ): Call<AnggaranPostResponse>

//    Laporan

    @GET("anggarandokumen")
    fun getAnggaranDokumen(
        @Query("key") key: String,
        @Query("kodeDok") kodeDok: String,
        @Query("tahun") tahun: String,
    ): Call<AnggaranDokumenResponse>

    @GET("anggaranorganisasi")
    fun getAnggaranSkpd(
        @Query("key") key: String,
        @Query("kodeDok") kodeDok: String,
        @Query("tahun") tahun: String
    ): Call<AnggaranSkpdResponse>

    @GET("anggaranrekening")
    fun getAnggaranRekening(
        @Query("key") key: String,
        @Query("kodeRek") kodeRek: String,
        @Query("kodeDok") kodeDok: String,
        @Query("tahun") tahun: String
    ): Call<DataAnggaranRekeningResponse>
}