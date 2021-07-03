package com.ilhmdhn.simranda.ui.anggaran

import android.annotation.SuppressLint
import android.content.Context
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import androidx.lifecycle.ViewModelProvider
import com.ilhmdhn.simranda.R
import com.ilhmdhn.simranda.data.source.local.entity.OrganisasiEntity
import com.ilhmdhn.simranda.data.source.local.entity.ProgramDanKegiatanEntity
import com.ilhmdhn.simranda.data.source.local.entity.RekeningEntity
import com.ilhmdhn.simranda.databinding.ActivityInputAnggaranBinding
import com.ilhmdhn.simranda.ui.master.programdankegiatan.SelectOrganisasiActivity
import com.ilhmdhn.simranda.ui.master.programdankegiatan.SelectOrganisasiAdapter
import com.ilhmdhn.simranda.viewmodel.ViewModelFactory

class InputAnggaranActivity : AppCompatActivity() {

    private var _activityInputAnggaranBinding: ActivityInputAnggaranBinding? = null
    private val binding get() = _activityInputAnggaranBinding

    private var selectedKegiatan: ProgramDanKegiatanEntity? = null
    private var selectedOrganisasi: OrganisasiEntity? = null
    private var selectedRekening: RekeningEntity? = null

    var kodeDok: String? = null
    var title: String? = null

    companion object {
        val EXTRA_KODE_DOK = "extra_kode_dok"
        private const val REQUEST_ORG = 100
        private const val REQUEST_KEG = 120
        private const val REQUEST_REK = 130
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        _activityInputAnggaranBinding = ActivityInputAnggaranBinding.inflate(layoutInflater)
        setContentView(binding?.root)

        kodeDok = intent.getStringExtra(EXTRA_KODE_DOK)

        when (kodeDok) {
            "1" -> title = "RKA SKPD"
            "2" -> title = "APBD MURNI"
            "3" -> title = "DPA SKPD"
            "4" -> title = "RKA SKPD PERUBAHAN"
            "5" -> title = "APBD PERUBAHAN"
            "6" -> title = "DPPA SKPD"
            "7" -> title = "APBD REALISASI"
        }

        supportActionBar?.title = title

        binding?.btnSelectOrganisasi?.setOnClickListener {
            val moveForResultOrg = Intent(this, SelectOrganisasiActivity::class.java)
            startActivityForResult(moveForResultOrg, REQUEST_ORG)
        }

        binding?.btnSelectRekening?.setOnClickListener {
            val moveForResultRek = Intent(this, SelectRekeningActivity::class.java)
            startActivityForResult(moveForResultRek, REQUEST_REK)
        }

        binding?.btnSelectKegiatan?.setOnClickListener {

            if (binding?.edtOrganisasi?.text.isNullOrEmpty()) {
                binding?.btnSelectOrganisasi?.error = "Diisi dulu"
                Toast.makeText(this, "Pilih Organisasi Terlebih Dahulu", Toast.LENGTH_SHORT).show()
            } else {
                val moveForResultKeg = Intent(this, SelectKegiatanActivity::class.java)
                startActivityForResult(moveForResultKeg, REQUEST_KEG)
            }
        }
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)

        if (requestCode == REQUEST_ORG) {
            if (resultCode == SelectOrganisasiAdapter.RESULT_ORG) {
                selectedOrganisasi =
                    data?.getParcelableExtra<OrganisasiEntity>(SelectOrganisasiAdapter.EXTRA_SELECTED_ORG) as OrganisasiEntity
            }
        }

        if (requestCode == REQUEST_REK) {
            if (resultCode == SelectRekeningAdapter.RESULT_REK) {
                selectedRekening =
                    data?.getParcelableExtra<RekeningEntity>(SelectRekeningAdapter.EXTRA_SELECT_REK)
            }
        }

        if (requestCode == REQUEST_KEG) {
            if (resultCode == SelectKegiatanAdapter.RESULT_KEG) {
                selectedKegiatan =
                    data?.getParcelableExtra<ProgramDanKegiatanEntity>(SelectKegiatanAdapter.EXTRA_SELECT_KEG)
            }
        }
    }

    @SuppressLint("SetTextI18n")
    override fun onResume() {
        super.onResume()

        if (selectedOrganisasi?.kodeOrg != null) {
            binding?.edtOrganisasi?.setText("${selectedOrganisasi?.kodeOrg} / ${selectedOrganisasi?.namaOrg}")
            binding?.edtBag?.setText("${selectedOrganisasi?.kodeBag} / ${selectedOrganisasi?.namaBag}")
            SelectKegiatanActivity.kodeOrg = selectedOrganisasi?.kodeOrg
        }

        if (selectedKegiatan?.kodeKegiatan != null) {
            binding?.edtProgram?.setText("${selectedKegiatan?.kodeKegiatan} / ${selectedKegiatan?.namaProgram}")
            binding?.edtKegiatan?.setText("${selectedKegiatan?.kodeKegiatan} / ${selectedKegiatan?.namaKegiatan}")
        }

        if (selectedRekening?.kodeRekening != null) {
            binding?.edtRekening?.setText("${selectedRekening?.kodeRekening} / ${selectedRekening?.namaRekening}")
        }

        val factory = ViewModelFactory.getInstance(this)
        val viewModel = ViewModelProvider(this, factory)[AnggaranViewModel::class.java]

        val context = applicationContext
        val sharedPref =
            context?.getSharedPreferences(R.string.setting_data.toString(), Context.MODE_PRIVATE)
        val tahun = sharedPref?.getString("year", getString(R.string.default_year))
        val userId = sharedPref?.getString("user", getString(R.string.default_user)).toString()
        val key = sharedPref?.getString("key", "").toString()

        binding?.btnSubmit?.setOnClickListener {
            if (selectedOrganisasi?.kodeOrg == null) {
                binding?.btnSelectOrganisasi?.error = "Pilih organisasi dahulu"
                Toast.makeText(this, "Pilih Organisasi Terlebih Dahulu", Toast.LENGTH_SHORT).show()
            } else if (selectedKegiatan?.kodeKegiatan == null) {
                binding?.btnSelectKegiatan?.error = "Pilih Program dan Kegiatan dahulu"
                Toast.makeText(
                    this,
                    "Pilih Program dan Kegiatan Terlebih Dahulu",
                    Toast.LENGTH_SHORT
                ).show()
            } else if (selectedRekening?.kodeRekening == null) {
                binding?.btnSelectRekening?.error = "Pilih Rekening dahulu"
                Toast.makeText(this, "Pilih Rekening Terlebih Dahulu", Toast.LENGTH_SHORT).show()
            } else if (binding?.edtHalDokumen?.text.isNullOrEmpty()) {
                binding?.edtHalDokumen?.error = "Kosong"
            } else if (binding?.edtAnggaran?.text.isNullOrEmpty()) {
                binding?.edtAnggaran?.error = "kosong"
            } else {
                viewModel.postAnggaran(
                    key,
                    tahun!!,
                    userId,
                    kodeDok!!,
                    binding!!.edtHalDokumen.text.toString(),
                    selectedOrganisasi!!.kodeOrg,
                    selectedOrganisasi!!.kodeBag!!,
                    selectedKegiatan!!.kodeKegiatan,
                    selectedRekening!!.kodeRekening,
                    binding?.edtAnggaran?.text.toString(),
                    applicationContext
                )
            }
        }
    }
}