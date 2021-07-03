package com.ilhmdhn.simranda.ui.laporan.rekening

import android.annotation.SuppressLint
import android.content.Context
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.lifecycle.ViewModelProvider
import com.ilhmdhn.simranda.R
import com.ilhmdhn.simranda.data.DataRepository
import com.ilhmdhn.simranda.databinding.ActivityDetailAnggaranRekeningBinding
import com.ilhmdhn.simranda.ui.laporan.LaporanViewModel
import com.ilhmdhn.simranda.utils.Utils
import com.ilhmdhn.simranda.viewmodel.ViewModelFactory
import com.ilhmdhn.simranda.vo.Status

class DetailAnggaranRekeningActivity : AppCompatActivity() {

    companion object {
        var EXTRA_KODE_DOK = "extra_kode_dok"
        var EXTRA_KODE_REK = "extra_kode_rek"
        var EXTRA_NAME_REK = "extra_name_rek"
    }

    private var _activityDetailAnggaranRekeningBinding: ActivityDetailAnggaranRekeningBinding? =
        null
    private val binding get() = _activityDetailAnggaranRekeningBinding

    @SuppressLint("SetTextI18n")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        _activityDetailAnggaranRekeningBinding =
            ActivityDetailAnggaranRekeningBinding.inflate(layoutInflater)
        setContentView(binding?.root)

        supportActionBar?.hide()
        val sharedPref = applicationContext?.getSharedPreferences(
            R.string.setting_data.toString(),
            Context.MODE_PRIVATE
        )
        val year = sharedPref?.getString("year", getString(R.string.default_year)).toString()
        val key = sharedPref?.getString("key", "").toString()

        val kodeDok: String = intent.getStringExtra(EXTRA_KODE_DOK).toString()
        val kodeRek: String = intent.getStringExtra(EXTRA_KODE_REK).toString()
        val namaRekening: String = intent.getStringExtra(EXTRA_NAME_REK).toString()

        when (kodeDok) {
            "1" -> binding?.tvDokumen?.text = "RKA-SKPD"
            "2" -> binding?.tvDokumen?.text = "APBD MURNI"
            "3" -> binding?.tvDokumen?.text = "DPA-SKPD"
            "4" -> binding?.tvDokumen?.text = "RKA-SKPD-PERUBAHAN"
            "5" -> binding?.tvDokumen?.text = "APBD PERUBAHAN"
            "6" -> binding?.tvDokumen?.text = "DPPA-SKPD"
            "7" -> binding?.tvDokumen?.text = "APBD-REALISASI"
        }

        binding?.tvTahun?.text = "Tahun Anggaran $year"
        binding?.tvKodeRekening?.text = Utils.generateKodeRekening(kodeRek)
        binding?.tvNamaRekening?.text = namaRekening

        getData(key, kodeRek, kodeDok, year)

        binding?.swiped?.setOnRefreshListener {
            getData(key, kodeRek, kodeDok, year)
            binding?.swiped?.isRefreshing = false
        }
    }

    @SuppressLint("SetTextI18n")
    private fun getData(key: String, kodeRek: String, kodeDok: String, tahun: String) {
        val factory = ViewModelFactory.getInstance(this)
        val viewModel = ViewModelProvider(this, factory)[LaporanViewModel::class.java]
        DataRepository.isConnected = Utils.networkCheck(applicationContext)
        viewModel.getAnggaranRekening(key, kodeRek, kodeDok, tahun).observe(this, { anggaran ->
            if (anggaran != null) {
                when (anggaran.status) {
                    Status.LOADING -> binding?.tvTotal?.text = "Memuat"
                    Status.SUCCES -> binding?.tvTotal?.text =
                        Utils.getCurrency(anggaran.data?.total?.toDouble())
                    Status.ERROR -> binding?.tvTotal?.text = "Gagal Dimuat"
                }
            }
        })
    }
}