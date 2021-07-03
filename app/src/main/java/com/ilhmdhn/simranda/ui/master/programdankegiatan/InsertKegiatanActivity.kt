package com.ilhmdhn.simranda.ui.master.programdankegiatan

import android.annotation.SuppressLint
import android.content.Context
import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider
import com.ilhmdhn.simranda.R
import com.ilhmdhn.simranda.data.source.local.entity.OrganisasiEntity
import com.ilhmdhn.simranda.databinding.ActivityInsertKegiatanBinding
import com.ilhmdhn.simranda.viewmodel.ViewModelFactory

class InsertKegiatanActivity : AppCompatActivity() {

    private var _activityInsertKegiatanBinding: ActivityInsertKegiatanBinding? = null
    private val binding get() = _activityInsertKegiatanBinding

    private var selectedValue: OrganisasiEntity? = null

    companion object {
        private const val REQUEST_CODE = 100
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        _activityInsertKegiatanBinding = ActivityInsertKegiatanBinding.inflate(layoutInflater)
        setContentView(binding?.root)

        supportActionBar?.title = "Insert Program dan kegiatan"

        binding?.btnSelectOrganisasi?.setOnClickListener {
            val moveForResultOrg = Intent(this, SelectOrganisasiActivity::class.java)
            startActivityForResult(moveForResultOrg, REQUEST_CODE)
        }
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)

        if (requestCode == REQUEST_CODE) {
            if (resultCode == SelectOrganisasiAdapter.RESULT_ORG) {
                selectedValue =
                    data?.getParcelableExtra<OrganisasiEntity>(SelectOrganisasiAdapter.EXTRA_SELECTED_ORG) as OrganisasiEntity
            }
        }
    }

    @SuppressLint("SetTextI18n")
    override fun onResume() {
        super.onResume()
        binding?.edtOrganisasi?.setText("${selectedValue?.kodeOrg} / ${selectedValue?.namaOrg}")
        binding?.edtBagian?.setText("${selectedValue?.kodeBag} / ${selectedValue?.namaBag}")

        val factory = ViewModelFactory.getInstance(this)
        val viewModel = ViewModelProvider(this, factory)[ProgramDanKegiatanViewModel::class.java]

        val context = applicationContext
        val sharedPref =
            context?.getSharedPreferences(R.string.setting_data.toString(), Context.MODE_PRIVATE)
        val tahun = sharedPref?.getString("year", getString(R.string.default_year))
        val userId = sharedPref?.getString("user", getString(R.string.default_user)).toString()
        val key = sharedPref?.getString("key", "getString(R.string.default_user)").toString()

        binding?.btnSubmit?.setOnClickListener {
            if (binding?.edtOrganisasi?.text.isNullOrEmpty()) {
                binding?.edtOrganisasi?.error = "Kosong"
                binding?.edtBagian?.error = "Kosong"
            } else if (binding?.edtKodeKegiatan?.text.isNullOrEmpty()) {
                binding?.edtKodeKegiatan?.error = "Kosong"
            } else if (binding?.edtNamaKegiatan?.text.isNullOrEmpty()) {
                binding?.edtNamaKegiatan?.error = "Kosong"
            } else if (binding?.edtNamaProgram?.text.isNullOrEmpty()) {
                binding?.edtNamaProgram?.error = "Kosong"
            } else {
                val kodeOrganisasi = selectedValue?.kodeOrg
                val kodeKegiatan = binding?.edtKodeKegiatan?.text.toString()
                val namaKegiatan = binding?.edtNamaKegiatan?.text.toString()
                val namaProgram = binding?.edtNamaProgram?.text.toString()

                viewModel.postKegiatan(
                    key,
                    tahun!!,
                    kodeOrganisasi!!,
                    kodeKegiatan,
                    namaProgram,
                    namaKegiatan,
                    userId,
                    context
                )
            }
        }
    }
}