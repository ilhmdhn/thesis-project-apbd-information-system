package com.ilhmdhn.simranda.data

import androidx.lifecycle.LiveData
import androidx.paging.LivePagedListBuilder
import androidx.paging.PagedList
import com.ilhmdhn.simranda.data.source.local.LocalDataSource
import com.ilhmdhn.simranda.data.source.local.entity.*
import com.ilhmdhn.simranda.data.source.remote.ApiResponse
import com.ilhmdhn.simranda.data.source.remote.RemoteDataSource
import com.ilhmdhn.simranda.data.source.remote.response.*
import com.ilhmdhn.simranda.utils.AppExecutors
import com.ilhmdhn.simranda.vo.Resource

class DataRepository private constructor(
    private val remoteDataSource: RemoteDataSource,
    private val localDataSource: LocalDataSource,
    private val appExecutors: AppExecutors
) : DataSource {

    companion object {
        @Volatile
        private var instance: DataRepository? = null

        fun getInstance(
            remoteData: RemoteDataSource,
            localData: LocalDataSource,
            appExecutors: AppExecutors
        ): DataRepository =
            instance ?: synchronized(this) {
                instance ?: DataRepository(remoteData, localData, appExecutors).apply {
                    instance = this
                }
            }
    }

//    Rekening
    override fun getRekening(
    key: String,
    year: String,
    reload: Boolean
): LiveData<Resource<PagedList<RekeningEntity>>> {
        return object :
            NetworkBoundResource<PagedList<RekeningEntity>, List<DataItemRekening>>(appExecutors) {
            override fun loadFromDB(): LiveData<PagedList<RekeningEntity>> {
                val config = PagedList.Config.Builder()
                    .setEnablePlaceholders(false)
                    .setInitialLoadSizeHint(20)
                    .setPageSize(20)
                    .build()
                return LivePagedListBuilder(localDataSource.getRekening(year), config).build()
            }

            override fun shouldFetch(data: PagedList<RekeningEntity>?): Boolean =
                data.isNullOrEmpty() || reload


            override fun createCall(): LiveData<ApiResponse<List<DataItemRekening>>> {
                return remoteDataSource.getRekening(key, year)
            }

            override fun saveCallResult(data: List<DataItemRekening>) {
                val rekeningList = ArrayList<RekeningEntity>()
                for (response in data) {
                    val rekening = with(response) {
                        RekeningEntity(
                            urutan, kodeRekening, namaRekening, username, tahun, tanggalInput
                        )
                    }
                    rekeningList.add(rekening)
                }
                localDataSource.deleteRekening()
                localDataSource.insertRekening(rekeningList)
            }
        }.asLiveData()
    }

    override fun getSelectRekening(
        key: String,
        year: String,
        reload: Boolean
    ): LiveData<Resource<PagedList<RekeningEntity>>> {
        return object :
            NetworkBoundResource<PagedList<RekeningEntity>, List<DataItemRekening>>(appExecutors) {
            override fun loadFromDB(): LiveData<PagedList<RekeningEntity>> {
                val config = PagedList.Config.Builder()
                    .setEnablePlaceholders(false)
                    .setInitialLoadSizeHint(20)
                    .setPageSize(20)
                    .build()
                return LivePagedListBuilder(localDataSource.getSelectRekening(), config).build()
            }

            override fun shouldFetch(data: PagedList<RekeningEntity>?): Boolean {
                return data.isNullOrEmpty() || reload
            }

            override fun createCall(): LiveData<ApiResponse<List<DataItemRekening>>> {
                return remoteDataSource.getRekening(key, year)
            }

            override fun saveCallResult(data: List<DataItemRekening>) {
                val rekeningList = ArrayList<RekeningEntity>()
                for (response in data) {
                    val rekening = with(response) {
                        RekeningEntity(
                            urutan, kodeRekening, namaRekening, username, tahun, tanggalInput
                        )
                    }
                    rekeningList.add(rekening)
                }
                localDataSource.deleteRekening()
                localDataSource.insertRekening(rekeningList)
            }
        }.asLiveData()
    }

    override fun getSelectSearchRekening(search: String?): LiveData<PagedList<RekeningEntity>> {
        val config = PagedList.Config.Builder()
            .setEnablePlaceholders(false)
            .setInitialLoadSizeHint(20)
            .setPageSize(20)
            .build()
        return LivePagedListBuilder(localDataSource.getSelectSearchRekening(search), config).build()
    }

    override fun getSearchRekening(search: String?): LiveData<PagedList<RekeningEntity>> {
        val config = PagedList.Config.Builder()
            .setEnablePlaceholders(false)
            .setInitialLoadSizeHint(20)
            .setPageSize(20)
            .build()
        return LivePagedListBuilder(localDataSource.getSearchRekening(search), config).build()
    }

    override fun getOrganisasi(
        key: String,
        year: String,
        reload: Boolean
    ): LiveData<Resource<PagedList<OrganisasiEntity>>> {
        return object :
            NetworkBoundResource<PagedList<OrganisasiEntity>, List<DataItemOrganisasi>>(appExecutors) {
            override fun loadFromDB(): LiveData<PagedList<OrganisasiEntity>> {
                val config = PagedList.Config.Builder()
                    .setEnablePlaceholders(false)
                    .setInitialLoadSizeHint(20)
                    .setPageSize(20)
                    .build()
                return LivePagedListBuilder(localDataSource.getOrganisasi(), config).build()
            }

            override fun shouldFetch(data: PagedList<OrganisasiEntity>?): Boolean {
                return data.isNullOrEmpty() || reload
            }

            override fun createCall(): LiveData<ApiResponse<List<DataItemOrganisasi>>> {
                return remoteDataSource.getOrganisasi(key, year)
            }

            override fun saveCallResult(data: List<DataItemOrganisasi>) {
                val organisasiList = ArrayList<OrganisasiEntity>()
                for (response in data) {
                    val organisasi = with(response) {
                        OrganisasiEntity(
                            urut,
                            kodeOrg,
                            kodeBag,
                            namaOrg,
                            namaBag,
                            namaFungsi,
                            tahun,
                            username,
                            tanggalEntry
                        )
                    }
                    organisasiList.add(organisasi)
                }
                localDataSource.deleteOrganisasi()
                localDataSource.insertOrganisasi(organisasiList)
            }
        }.asLiveData()
    }

    override fun getSearchOrganisasi(search: String?): LiveData<PagedList<OrganisasiEntity>> {
        val config = PagedList.Config.Builder()
            .setEnablePlaceholders(false)
            .setInitialLoadSizeHint(20)
            .setPageSize(20)
            .build()
        return LivePagedListBuilder(localDataSource.getSearchOrganisasi(search), config).build()
    }

    override fun getNameOrg(search: String): LiveData<getNameOrgModel> {
        return localDataSource.getNameOrg(search)
    }

    override fun getSelectOrg(
        key: String,
        year: String,
        reload: Boolean
    ): LiveData<Resource<PagedList<OrganisasiEntity>>> {
        return object :
            NetworkBoundResource<PagedList<OrganisasiEntity>, List<DataItemOrganisasi>>(appExecutors) {
            override fun loadFromDB(): LiveData<PagedList<OrganisasiEntity>> {
                val config = PagedList.Config.Builder()
                    .setEnablePlaceholders(false)
                    .setInitialLoadSizeHint(20)
                    .setPageSize(20)
                    .build()
                return LivePagedListBuilder(localDataSource.getSelectOrg(), config).build()
            }

            override fun shouldFetch(data: PagedList<OrganisasiEntity>?): Boolean {
                return data.isNullOrEmpty() || reload
            }

            override fun createCall(): LiveData<ApiResponse<List<DataItemOrganisasi>>> {
                return remoteDataSource.getOrganisasi(key, year)
            }

            override fun saveCallResult(data: List<DataItemOrganisasi>) {
                val organisasiList = ArrayList<OrganisasiEntity>()
                for (response in data) {
                    val organisasi = with(response) {
                        OrganisasiEntity(
                            urut,
                            kodeOrg,
                            kodeBag,
                            namaOrg,
                            namaBag,
                            namaFungsi,
                            tahun,
                            username,
                            tanggalEntry
                        )
                    }
                    organisasiList.add(organisasi)
                }
                localDataSource.deleteOrganisasi()
                localDataSource.insertOrganisasi(organisasiList)
            }

        }.asLiveData()
    }

    override fun getSelectSearchOrg(search: String?): LiveData<PagedList<OrganisasiEntity>> {
        val config = PagedList.Config.Builder()
            .setEnablePlaceholders(false)
            .setInitialLoadSizeHint(20)
            .setPageSize(20)
            .build()
        return LivePagedListBuilder(localDataSource.getSelectSearchOrg(search), config).build()
    }

//    kegiatan

    override fun getKegiatan(
        key: String,
        year: String,
        reload: Boolean
    ): LiveData<Resource<PagedList<ProgramDanKegiatanEntity>>> {
        return object :
            NetworkBoundResource<PagedList<ProgramDanKegiatanEntity>, List<DataItemProgramDanKegiatan>>(
                appExecutors
            ) {
            override fun loadFromDB(): LiveData<PagedList<ProgramDanKegiatanEntity>> {
                val config = PagedList.Config.Builder()
                    .setEnablePlaceholders(false)
                    .setInitialLoadSizeHint(20)
                    .setPageSize(20)
                    .build()
                return LivePagedListBuilder(localDataSource.getKegiatan(), config).build()
            }

            override fun shouldFetch(data: PagedList<ProgramDanKegiatanEntity>?): Boolean {
                return data.isNullOrEmpty() || reload
            }

            override fun createCall(): LiveData<ApiResponse<List<DataItemProgramDanKegiatan>>> {
                return remoteDataSource.getKegiatan(key, year)
            }

            override fun saveCallResult(data: List<DataItemProgramDanKegiatan>) {
                val kegiatanList = ArrayList<ProgramDanKegiatanEntity>()
                for (response in data) {
                    val kegiatan = with(response) {
                        ProgramDanKegiatanEntity(
                            urut,
                            kodeKegiatan,
                            kodeOrganisasi,
                            namaProgram,
                            namaKegiatan,
                            username,
                            tanggalEntry,
                            tahun
                        )
                    }
                    kegiatanList.add(kegiatan)
                }
                localDataSource.deleteKegiatan()
                localDataSource.insertKegiatan(kegiatanList)
            }
        }.asLiveData()
    }

    override fun getSelectKeg(
        key: String,
        year: String,
        kodeOrg: String?,
        reload: Boolean
    ): LiveData<Resource<PagedList<ProgramDanKegiatanEntity>>> {
        return object :
            NetworkBoundResource<PagedList<ProgramDanKegiatanEntity>, List<DataItemProgramDanKegiatan>>(
                appExecutors
            ) {
            override fun loadFromDB(): LiveData<PagedList<ProgramDanKegiatanEntity>> {
                val config = PagedList.Config.Builder()
                    .setEnablePlaceholders(false)
                    .setInitialLoadSizeHint(20)
                    .setPageSize(20)
                    .build()
                return LivePagedListBuilder(localDataSource.getSelectKeg(kodeOrg), config).build()
            }

            override fun shouldFetch(data: PagedList<ProgramDanKegiatanEntity>?): Boolean {
                return data.isNullOrEmpty() || reload
            }

            override fun createCall(): LiveData<ApiResponse<List<DataItemProgramDanKegiatan>>> {
                return remoteDataSource.getKegiatan(key, year)
            }

            override fun saveCallResult(data: List<DataItemProgramDanKegiatan>) {
                val kegiatanList = ArrayList<ProgramDanKegiatanEntity>()
                for (response in data) {
                    val kegiatan = with(response) {
                        ProgramDanKegiatanEntity(
                            urut,
                            kodeKegiatan,
                            kodeOrganisasi,
                            namaProgram,
                            namaKegiatan,
                            username,
                            tanggalEntry,
                            tahun
                        )
                    }
                    kegiatanList.add(kegiatan)
                }
                localDataSource.deleteKegiatan()
                localDataSource.insertKegiatan(kegiatanList)
            }
        }.asLiveData()
    }

    override fun getSelectSearchKeg(
        kodeOrg: String,
        search: String?
    ): LiveData<PagedList<ProgramDanKegiatanEntity>> {
        val config = PagedList.Config.Builder()
            .setEnablePlaceholders(false)
            .setInitialLoadSizeHint(20)
            .setPageSize(20)
            .build()
        return LivePagedListBuilder(
            localDataSource.getSelectSearchKeg(kodeOrg, search),
            config
        ).build()
    }

    override fun getSearchKegiatan(search: String?): LiveData<PagedList<ProgramDanKegiatanEntity>> {
        val config = PagedList.Config.Builder()
            .setEnablePlaceholders(false)
            .setInitialLoadSizeHint(20)
            .setPageSize(20)
            .build()
        return LivePagedListBuilder(localDataSource.getSearchKegiatan(search), config).build()
    }

    override fun getSearchAnggaran(
        kodeDok: String,
        search: String?
    ): LiveData<PagedList<AnggaranEntity>> {
        val config = PagedList.Config.Builder()
            .setEnablePlaceholders(false)
            .setInitialLoadSizeHint(20)
            .setPageSize(20)
            .build()
        return LivePagedListBuilder(
            localDataSource.getSearchAnggaran(kodeDok, search),
            config
        ).build()
    }

    override fun getAnggaran(
        key: String,
        year: String,
        kodeDokumen: String,
        reload: Boolean
    ): LiveData<Resource<PagedList<AnggaranEntity>>> {
        return object :
            NetworkBoundResource<PagedList<AnggaranEntity>, List<DataItemAnggaran>>(appExecutors) {
            override fun loadFromDB(): LiveData<PagedList<AnggaranEntity>> {
                val config = PagedList.Config.Builder()
                    .setEnablePlaceholders(false)
                    .setInitialLoadSizeHint(20)
                    .setPageSize(20)
                    .build()
                return LivePagedListBuilder(
                    localDataSource.getAnggaran(kodeDokumen),
                    config
                ).build()
            }

            override fun shouldFetch(data: PagedList<AnggaranEntity>?): Boolean {
                return data.isNullOrEmpty() || reload
            }

            override fun createCall(): LiveData<ApiResponse<List<DataItemAnggaran>>> {
                return remoteDataSource.getAnggaran(key, year, kodeDokumen)
            }

            override fun saveCallResult(data: List<DataItemAnggaran>) {
                val anggaranList = ArrayList<AnggaranEntity>()
                for (response in data) {
                    val anggaran = with(response) {
                        AnggaranEntity(
                            id,
                            kodeKegiatan,
                            halDokumen,
                            kodeRekening,
                            kodeBagian,
                            kodeOrganisasi,
                            nominalAnggaran,
                            tahun,
                            tanggalInput,
                            username,
                            this.kodeDokumen
                        )
                    }
                    anggaranList.add(anggaran)
                }
                localDataSource.deleteAnggaran(kodeDokumen)
                localDataSource.insertAnggaran(anggaranList)
            }

        }.asLiveData()
    }

    override fun getName(id: String): LiveData<getNameModel> = localDataSource.getName(id)

    override fun getAnggaranDokumen(
        key: String,
        kodeDok: String,
        tahun: String,
        reload: Boolean
    ): LiveData<Resource<PagedList<AnggaranDokumenEntity>>> {
        return object :
            NetworkBoundResource<PagedList<AnggaranDokumenEntity>, List<DataItemAnggaranDokumen>>(
                appExecutors
            ) {

            override fun loadFromDB(): LiveData<PagedList<AnggaranDokumenEntity>> {
                val config = PagedList.Config.Builder()
                    .setEnablePlaceholders(false)
                    .setInitialLoadSizeHint(100)
                    .setPageSize(100)
                    .build()
                return LivePagedListBuilder(
                    localDataSource.getAnggaranDokumen(kodeDok, tahun),
                    config
                ).build()
            }

            override fun shouldFetch(data: PagedList<AnggaranDokumenEntity>?): Boolean = data.isNullOrEmpty() || reload

            override fun createCall(): LiveData<ApiResponse<List<DataItemAnggaranDokumen>>> =
                remoteDataSource.getAnggaranDokumen(key, kodeDok, tahun)

            override fun saveCallResult(data: List<DataItemAnggaranDokumen>) {
                val anggaranList = ArrayList<AnggaranDokumenEntity>()
                for (response in data) {
                    val anggaran = with(response) {
                        AnggaranDokumenEntity(
                            0, namaRekening, tahun, jumlah, kodeRekening, kodeDokumen, jumlahDua
                        )
                    }
                    anggaranList.add(anggaran)
                }
                localDataSource.deleteAnggaranDokumen(kodeDok, tahun)
                localDataSource.insertAnggaranDokumen(anggaranList)
            }
        }.asLiveData()
    }


    override fun getAnggaranRekening(
        key: String,
        kodeRek: String,
        kodeDok: String,
        tahun: String,
        reload: Boolean
    ): LiveData<Resource<AnggaranRekeningEntity>> {
        return object :
            NetworkBoundResource<AnggaranRekeningEntity, DataAnggaranRekening>(appExecutors) {
            override fun loadFromDB(): LiveData<AnggaranRekeningEntity> =
                localDataSource.getAnggaranRekening(kodeRek, kodeDok, tahun)

            override fun shouldFetch(data: AnggaranRekeningEntity?): Boolean =
                data?.id == null || reload

            override fun createCall(): LiveData<ApiResponse<DataAnggaranRekening>> =
                remoteDataSource.getAnggaranRekening(key, kodeRek, kodeDok, tahun)

            override fun saveCallResult(data: DataAnggaranRekening) {
                val filterDataResult = with(data) {
                    AnggaranRekeningEntity(
                        0, kodeRekening, this.kodeDok, this.tahun, total
                    )
                }
                localDataSource.deleteAnggaranRekening(kodeRek, kodeDok, tahun)
                localDataSource.insertFilterAnggaran(filterDataResult)
            }

        }.asLiveData()
    }
}