package com.ilhmdhn.simranda.ui.master.organisasi

import android.content.Context
import android.os.Bundle
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider
import com.ilhmdhn.simranda.R
import com.ilhmdhn.simranda.data.source.local.entity.OrganisasiEntity
import com.ilhmdhn.simranda.databinding.ActivityUpdateOrganisasiBinding
import com.ilhmdhn.simranda.viewmodel.ViewModelFactory

class UpdateOrganisasiActivity : AppCompatActivity() {
    companion object {
        const val EXTRA_DATA = "extra_data"
        const val ALERT_DIALOG_UPDATE = 10
        const val ALERT_DIALOG_DELETE = 20
    }

    private var _activityUpdateOrganisasiBinding: ActivityUpdateOrganisasiBinding? = null
    private val binding get() = _activityUpdateOrganisasiBinding

    var data: OrganisasiEntity? = null

    val factory = ViewModelFactory.getInstance(this)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        _activityUpdateOrganisasiBinding = ActivityUpdateOrganisasiBinding.inflate(layoutInflater)
        setContentView(binding?.root)

        data = intent.getParcelableExtra<OrganisasiEntity>(EXTRA_DATA) as OrganisasiEntity

        supportActionBar?.title = data?.namaOrg

        binding?.tvTglEntry?.text = data?.tanggalEntry
        binding?.tvUserId?.text = data?.username
        binding?.tvKodeOrganisasi?.text = data?.kodeOrg

        binding?.edtKeteranganSkpd?.setText(data?.namaOrg)
        binding?.edtKodeBagian?.setText(data?.kodeBag)
        binding?.edtNamaBagian?.setText(data?.namaBag)
        binding?.edtNamaFungsi?.setText(data?.namaFungsi)

        binding?.btnUpdate?.setOnClickListener {
            if (binding?.edtKeteranganSkpd?.text.isNullOrEmpty()) {
                binding?.edtKeteranganSkpd?.error = "Kosong"
            } else if (binding?.edtKodeBagian?.text.isNullOrEmpty()) {
                binding?.edtKodeBagian?.error = "Kosong"
            } else if (binding?.edtNamaBagian?.text.isNullOrEmpty()) {
                binding?.edtNamaBagian?.error = "Kosong"
            } else if (binding?.edtNamaFungsi?.text.isNullOrEmpty()) {
                binding?.edtNamaFungsi?.error = "Kosong"
            } else {
                val ketSkpd = binding?.edtKeteranganSkpd?.text.toString()
                val kodeBag = binding?.edtKodeBagian?.text.toString()
                val namaBag = binding?.edtNamaBagian?.text.toString()
                val namaFungsi = binding?.edtNamaFungsi?.text.toString()
                showAlertDialog(
                    ALERT_DIALOG_UPDATE,
                    ketSkpd,
                    kodeBag,
                    namaBag,
                    namaFungsi,
                    applicationContext
                )
            }
        }

        binding?.btnDelete?.setOnClickListener {
            showAlertDialog(ALERT_DIALOG_DELETE, "", "", "", "", applicationContext)
        }
    }

    private fun showAlertDialog(
        type: Int,
        ketSKPD: String,
        kodeBag: String,
        namaBag: String,
        namaFungsi: String,
        context: Context
    ) {

        val sharedPref = applicationContext.getSharedPreferences(
            R.string.setting_data.toString(),
            Context.MODE_PRIVATE
        )
        val key = sharedPref?.getString("key", getString(R.string.default_year)).toString()

        val viewModel = ViewModelProvider(this, factory)[OrganisasiViewModel::class.java]
        val isDialogUpdate = type == ALERT_DIALOG_UPDATE
        val dialogTitle: String
        val dialogMessage: String
        if (isDialogUpdate) {
            dialogTitle = "Ubah Data?"
            dialogMessage = "Apakah anda ingin mengubah data?"
        } else {
            dialogTitle = "Hapus?"
            dialogMessage = "Apakah anda yakin ingin menghapus item ini?"
        }

        val alertDialogBuilder = AlertDialog.Builder(this)
        with(alertDialogBuilder) {
            setTitle(dialogTitle)
            setMessage(dialogMessage)
            setCancelable(true)
            setPositiveButton(("Ya")) { _, _ ->
                if (isDialogUpdate) {
                    viewModel.updateOrganisasi(
                        key,
                        data?.urut.toString(),
                        ketSKPD,
                        kodeBag,
                        namaBag,
                        namaFungsi,
                        context
                    )
                } else {
                    viewModel.deleteOrganisasi(key, data?.urut.toString(), context)
                }
            }
            setNegativeButton("Tidak") { dialog, _ -> dialog.cancel() }
        }
        val alertDialog = alertDialogBuilder.create()
        alertDialog.show()
    }
}