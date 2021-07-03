package com.ilhmdhn.simranda.ui.laporan.organisasi

import android.annotation.SuppressLint
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.AdapterView
import android.widget.ArrayAdapter
import android.widget.Toast
import com.ilhmdhn.simranda.R
import com.ilhmdhn.simranda.data.source.local.entity.OrganisasiEntity
import com.ilhmdhn.simranda.databinding.ActivityAnggaranOrganisasiBinding
import com.ilhmdhn.simranda.ui.master.programdankegiatan.SelectOrganisasiActivity
import com.ilhmdhn.simranda.ui.master.programdankegiatan.SelectOrganisasiAdapter
import com.ilhmdhn.simranda.ui.master.programdankegiatan.SelectOrganisasiAdapter.Companion.RESULT_ORG

class AnggaranOrganisasiActivity : AppCompatActivity(), AdapterView.OnItemSelectedListener {

    private var _activityAnggaranOrganisasiBinding: ActivityAnggaranOrganisasiBinding? = null
    private val binding get() = _activityAnggaranOrganisasiBinding

    private var kodeDok: String? = null
    private var selectedOrganisasi: OrganisasiEntity? = null

    companion object {
        private const val REQUEST_ORG = 100
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        _activityAnggaranOrganisasiBinding =
            ActivityAnggaranOrganisasiBinding.inflate(layoutInflater)
        setContentView(binding?.root)

        supportActionBar?.title = "Laporan Anggaran Organisasi"

        binding?.dokSpinner?.onItemSelectedListener = this

        ArrayAdapter.createFromResource(
            this,
            R.array.jenis_dokumen,
            android.R.layout.simple_spinner_item
        ).also { adapter ->
            adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
            binding?.dokSpinner?.adapter = adapter
        }


        binding?.btnSelectOrganisasi?.setOnClickListener {
            val moveForResult = Intent(this, SelectOrganisasiActivity::class.java)
            startActivityForResult(moveForResult, RESULT_ORG)
        }

    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (requestCode == RESULT_ORG) {
            if (resultCode == RESULT_ORG) {
                selectedOrganisasi =
                    data?.getParcelableExtra<OrganisasiEntity>(SelectOrganisasiAdapter.EXTRA_SELECTED_ORG) as OrganisasiEntity
            }
        }
    }

    @SuppressLint("SetTextI18n")
    override fun onResume() {
        super.onResume()

        if (selectedOrganisasi?.kodeOrg != null) {
            binding?.edtOrganisasi?.setText("${selectedOrganisasi?.kodeOrg} / ${selectedOrganisasi?.namaOrg}")
            binding?.edtBag?.setText("${selectedOrganisasi?.kodeBag} / ${selectedOrganisasi?.namaBag}")
        }


        binding?.btnSubmit?.setOnClickListener {
            if (selectedOrganisasi?.kodeOrg == null) {
                binding?.btnSelectOrganisasi?.error = "pilih organisasi"
                Toast.makeText(this, "Silahkan pilih organisasi terlebih dulu", Toast.LENGTH_SHORT)
                    .show()
            } else if (kodeDok == null) {
                Toast.makeText(this, "Silahkan pilih jenis Dokumen dulu", Toast.LENGTH_SHORT).show()
            } else {
                Toast.makeText(
                    this,
                    "Kode Organisasi ${selectedOrganisasi?.kodeOrg} Kode Dok $kodeDok",
                    Toast.LENGTH_SHORT
                ).show()
            }
        }
    }

    override fun onItemSelected(parent: AdapterView<*>?, view: View?, position: Int, id: Long) {
        when (position) {
            0 -> kodeDok = null
            1 -> kodeDok = "1"
            2 -> kodeDok = "2"
            3 -> kodeDok = "3"
            4 -> kodeDok = "4"
            5 -> kodeDok = "5"
            6 -> kodeDok = "6"
            7 -> kodeDok = "7"
        }
    }

    override fun onNothingSelected(parent: AdapterView<*>?) {
        kodeDok = null
    }
}