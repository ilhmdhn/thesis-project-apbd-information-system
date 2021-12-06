package com.ilhmdhn.simranda.ui.laporan.organisasi

import android.annotation.SuppressLint
import android.content.Context
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import androidx.lifecycle.ViewModelProvider
import com.ilhmdhn.simranda.R
import com.ilhmdhn.simranda.data.source.local.entity.OrganisasiEntity
import com.ilhmdhn.simranda.databinding.ActivityDetailAnggaranOrganisasiBinding
import com.ilhmdhn.simranda.ui.laporan.LaporanViewModel
import com.ilhmdhn.simranda.viewmodel.ViewModelFactory

class DetailAnggaranOrganisasiActivity : AppCompatActivity() {

    private var _activityDetailAnggaranOrganisasiBinding: ActivityDetailAnggaranOrganisasiBinding? = null
    private val binding get() = _activityDetailAnggaranOrganisasiBinding

    companion object {
        var EXTRA_KODE_DOK = "extra_doke_dok"
        var EXTRA_DATA_ORG = "extra_data_org"
    }

    var dataOrg: OrganisasiEntity? = null
    var kodeDok: String = ""
    var anggaranOrganisasiAdapter = AnggaranOrganisasiAdapter()

    @SuppressLint("SetTextI18n")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        _activityDetailAnggaranOrganisasiBinding =
            ActivityDetailAnggaranOrganisasiBinding.inflate(layoutInflater)
        setContentView(binding?.root)

        dataOrg = intent.getParcelableExtra<OrganisasiEntity>(EXTRA_DATA_ORG) as OrganisasiEntity
        kodeDok = intent.getStringExtra(EXTRA_KODE_DOK).toString()

        val sharedPref = applicationContext?.getSharedPreferences(R.string.setting_data.toString(),
            Context.MODE_PRIVATE)

        val key = sharedPref?.getString("key", "").toString()

        supportActionBar?.hide()

        binding?.tvPemerintahan?.text = dataOrg?.namaFungsi
        binding?.tvOrg?.text = dataOrg?.namaOrg
        binding?.tvTahun?.text = "TAHUN ANGGARAN ${dataOrg?.tahun}"

        when (kodeDok) {
            "1" -> binding?.tvDok?.text = "Ringkasan RKA SKPD"
            "2" -> binding?.tvDok?.text = "Ringkasan APBD Murni"
            "3" -> binding?.tvDok?.text = "Ringkasan DPA SKPD"
            "4" -> binding?.tvDok?.text = "Ringkasan RKA SKPD Perubahan"
            "5" -> binding?.tvDok?.text = "Ringkasan APBD PERUBAHAN"
            "6" -> binding?.tvDok?.text = "Ringkasan DPPA SKPD"
            "7" -> binding?.tvDok?.text = "Ringkasan APBD Realisasi"
        }

        getData(key, kodeDok, dataOrg?.kodeOrg.toString(), dataOrg?.tahun.toString())

        binding?.swipe?.setOnRefreshListener {
            getData(key, kodeDok, dataOrg?.kodeOrg.toString(), dataOrg?.tahun.toString())
            binding?.swipe?.isRefreshing = false
        }
    }

    fun getData(key: String, kodeDok: String, kodeOrg: String, tahun: String) {
        val factory = ViewModelFactory.getInstance(this)
        val viewModel = ViewModelProvider(this, factory)[LaporanViewModel::class.java]

        viewModel.getAnggaranOrganisasi(key, kodeDok, kodeOrg, tahun).observe(this, { data ->
            if (data != null) {
                when (data.status) {
                    Status.LOADING -> binding?.progressBar?.visibility = View.VISIBLE
                    Status.SUCCES -> {
                        binding?.progressBar?.visibility = View.GONE
                        anggaranOrganisasiAdapter.submitList(data.data)
                    }
                    Status.ERROR -> {
                        binding?.progressBar?.visibility = View.GONE
//                        Toast.makeText(this, "Gagal terhubung ke server", Toast.LENGTH_SHORT).show()
                    }
                }
            }
            with(binding?.rvAnggaran) {
                this?.layoutManager = LinearLayoutManager(this?.context)
                this?.setHasFixedSize(true)
                this?.adapter = anggaranOrganisasiAdapter
            }
        })
    }
}