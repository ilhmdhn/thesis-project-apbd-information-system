package com.ilhmdhn.simranda.ui.master.organisasi

import android.content.Context
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.lifecycle.ViewModelProvider
import com.ilhmdhn.simranda.R
import com.ilhmdhn.simranda.databinding.ActivityInsertOrganisasiBinding
import com.ilhmdhn.simranda.viewmodel.ViewModelFactory

class InsertOrganisasiActivity : AppCompatActivity() {

    private var _activityInsertOrganisasiBinding: ActivityInsertOrganisasiBinding? = null
    private val binding get() = _activityInsertOrganisasiBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        _activityInsertOrganisasiBinding = ActivityInsertOrganisasiBinding.inflate(layoutInflater)
        setContentView(binding?.root)

        supportActionBar?.title = "Insert Organisasi"

        val factory = ViewModelFactory.getInstance(this)
        val viewModel = ViewModelProvider(this, factory)[OrganisasiViewModel::class.java]

        val context = applicationContext
        val sharedPref =
            context?.getSharedPreferences(R.string.setting_data.toString(), Context.MODE_PRIVATE)
        val tahun = sharedPref?.getString("year", getString(R.string.default_year))
        val userId = sharedPref?.getString("user", getString(R.string.default_user)).toString()
        val key = sharedPref?.getString("key", "").toString()

        binding?.submit?.setOnClickListener {
            if (binding?.edtSkpd?.text.isNullOrEmpty()) {
                binding?.edtSkpd?.error = "Kosong"
            } else if (binding?.edtKodeBagian?.text.isNullOrEmpty()) {
                binding?.edtKodeBagian?.error = "Kosong"
            } else if (binding?.edtKeteranganSkpd?.text.isNullOrEmpty()) {
                binding?.edtKeteranganSkpd?.error = "Kosong"
            } else if (binding?.edtNamaBagian?.text.isNullOrEmpty()) {
                binding?.edtNamaBagian?.error = "Kosong"
            } else if (binding?.edtNamaFungsi?.text.isNullOrEmpty()) {
                binding?.edtNamaFungsi?.error = "Kosong"
            } else {

                val skpd = binding?.edtSkpd?.text.toString()
                val kodeBag = binding?.edtKodeBagian?.text.toString()
                val ketSkpd = binding?.edtKeteranganSkpd?.text.toString()
                val namaBag = binding?.edtNamaBagian?.text.toString()
                val namaFungsi = binding?.edtNamaFungsi?.text.toString()

                viewModel.postOrganisasi(
                    key,
                    tahun!!,
                    skpd,
                    ketSkpd,
                    kodeBag,
                    namaBag,
                    namaFungsi,
                    userId,
                    context
                )
            }
        }
    }
}