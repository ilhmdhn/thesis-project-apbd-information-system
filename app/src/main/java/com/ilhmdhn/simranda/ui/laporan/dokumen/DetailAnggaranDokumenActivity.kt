package com.ilhmdhn.simranda.ui.laporan.dokumen

import android.annotation.SuppressLint
import android.content.Context
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Toast
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.ilhmdhn.simranda.R
import com.ilhmdhn.simranda.data.DataRepository
import com.ilhmdhn.simranda.databinding.ActivityDetailAnggaranDokumenBinding
import com.ilhmdhn.simranda.ui.laporan.LaporanViewModel
import com.ilhmdhn.simranda.utils.Utils
import com.ilhmdhn.simranda.viewmodel.ViewModelFactory
import com.ilhmdhn.simranda.vo.Status

class DetailAnggaranDokumenActivity : AppCompatActivity() {

    companion object {
        var EXTRA_KODE_DOK = "extra_kode_dok"
    }

    private var _activityDetailAnggaranDokumenBinding: ActivityDetailAnggaranDokumenBinding? = null
    private val binding get() = _activityDetailAnggaranDokumenBinding

    val ringkasanAnggaranAdapter = AnggaranDokumenAdapter()

    @SuppressLint("SetTextI18n")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        _activityDetailAnggaranDokumenBinding =
            ActivityDetailAnggaranDokumenBinding.inflate(layoutInflater)
        setContentView(binding?.root)

        val kodeDok: String = intent.getStringExtra(EXTRA_KODE_DOK).toString()

        Log.d("kodedok", kodeDok)

        when (kodeDok) {
            "1" -> binding?.tvJudul?.text = "RINGKASAN RKA-SKPD"
            "2" -> binding?.tvJudul?.text = "RINGKASAN APBD MURNI"
            "3" -> binding?.tvJudul?.text = "RINGKASAN DPA-SKPD"
            "4" -> binding?.tvJudul?.text = "RINGKASAN RKA-SKPD-PERUBAHAN"
            "5" -> binding?.tvJudul?.text = "RINGKASAN APBD PERUBAHAN"
            "6" -> binding?.tvJudul?.text = "RINGKASAN DPPA-SKPD"
            "7" -> binding?.tvJudul?.text = "RINGKASAN APBD-REALISASI"
        }

        val sharedPref = applicationContext?.getSharedPreferences(
            R.string.setting_data.toString(),
            Context.MODE_PRIVATE
        )
        val year = sharedPref?.getString("year", getString(R.string.default_year)).toString()
        val key = sharedPref?.getString("key", "").toString()

        binding?.tvTahun?.text = "TAHUN ANGGARAN ${year}"

        supportActionBar?.hide()

        DataRepository.isConnected = Utils.networkCheck(applicationContext)
        getData(key, kodeDok, year)

        binding?.swipe?.setOnRefreshListener {
            DataRepository.isConnected = Utils.networkCheck(applicationContext)
            getData(key, kodeDok, year)
            binding?.swipe?.isRefreshing = false
        }
    }

    fun getData(key: String, kodeDok: String, tahun: String) {
        val factory = ViewModelFactory.getInstance(this)
        val viewModel = ViewModelProvider(this, factory)[LaporanViewModel::class.java]

        viewModel.getAnggaranDokumen(key, kodeDok, tahun).observe(this, { ringkasanAnggaran ->
            if (ringkasanAnggaran != null) {
                when (ringkasanAnggaran.status) {
                    Status.LOADING -> binding?.progressBar?.visibility = View.VISIBLE
                    Status.SUCCES -> {
                        binding?.progressBar?.visibility = View.GONE
                        ringkasanAnggaranAdapter.submitList(ringkasanAnggaran.data)
                    }
                    Status.ERROR -> {
                        binding?.progressBar?.visibility = View.GONE
                        Toast.makeText(this, "Terjadi Kesalahan", Toast.LENGTH_SHORT).show()
                    }
                }
            }
        })
        with(binding?.rvRingkasan) {
            this?.layoutManager = LinearLayoutManager(this?.context)
            this?.setHasFixedSize(true)
            this?.adapter = ringkasanAnggaranAdapter
        }
    }
}