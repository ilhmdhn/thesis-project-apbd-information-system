package com.ilhmdhn.simranda.ui.master.programdankegiatan

import android.content.Context
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.lifecycle.ViewModelProvider
import com.ilhmdhn.simranda.R
import com.ilhmdhn.simranda.data.source.local.entity.ProgramDanKegiatanEntity
import com.ilhmdhn.simranda.databinding.ActivityUpdateKegiatanBinding
import com.ilhmdhn.simranda.viewmodel.ViewModelFactory

class UpdateKegiatanActivity : AppCompatActivity() {

    companion object {
        const val EXTRA_DATA = "extra_data"
        const val ALERT_DIALOG_UPDATE = 10
        const val ALERT_DIALOG_DELETE = 20
    }

    var data: ProgramDanKegiatanEntity? = null

    private var _activityUpdateKegiatanBinding: ActivityUpdateKegiatanBinding? = null
    private val binding get() = _activityUpdateKegiatanBinding

    val factory = ViewModelFactory.getInstance(this)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        val viewModel = ViewModelProvider(this, factory)[ProgramDanKegiatanViewModel::class.java]

        _activityUpdateKegiatanBinding = ActivityUpdateKegiatanBinding.inflate(layoutInflater)
        setContentView(binding?.root)

        data =
            intent.getParcelableExtra<ProgramDanKegiatanEntity>(EXTRA_DATA) as ProgramDanKegiatanEntity

        supportActionBar?.title = "Update Kegiatan"

        viewModel.getNameOrg(data!!.kodeOrganisasi!!).observe(this, { data ->
            if (data != null) {
                binding?.tvNamaOrganisasi?.text = data.nama_organisasi
            } else {
                Toast.makeText(this, "nama organisasi null", Toast.LENGTH_SHORT).show()
            }
        })

        binding?.tvTahun?.text = data?.tahun
        binding?.tvTglEntry?.text = data?.tanggalEntry
        binding?.tvUserId?.text = data?.username
        binding?.tvKodeOrganisasi?.text = data?.kodeOrganisasi
        binding?.tvKodeKegiatan?.text = data?.kodeKegiatan
        binding?.edtNamaKegiatan?.setText(data?.namaKegiatan)
        binding?.edtNamaProgram?.setText(data?.namaProgram)

        binding?.btnDelete?.setOnClickListener {
            showAlertDialog(ALERT_DIALOG_DELETE, "", "", applicationContext)
        }

        binding?.btnUpdate?.setOnClickListener {
            if (binding?.edtNamaKegiatan?.text.isNullOrEmpty()) {
                binding?.edtNamaKegiatan?.error = "kosong"
            } else if (binding?.edtNamaProgram?.text.isNullOrEmpty()) {
                binding?.edtNamaProgram?.error = "kosong"
            } else {
                showAlertDialog(
                    ALERT_DIALOG_UPDATE,
                    binding?.edtNamaProgram?.text.toString(),
                    binding?.edtNamaKegiatan?.text.toString(),
                    applicationContext
                )
            }
        }
    }

    private fun showAlertDialog(
        type: Int,
        isiProgram: String,
        isiKegiatan: String,
        context: Context
    ) {

        val sharedPref = applicationContext.getSharedPreferences(
            R.string.setting_data.toString(),
            Context.MODE_PRIVATE
        )
        val key = sharedPref?.getString("key", "").toString()

        val viewModel = ViewModelProvider(this, factory)[ProgramDanKegiatanViewModel::class.java]
        val isiDialogUpdate = type == ALERT_DIALOG_UPDATE
        val dialogTitle: String
        val dialogMessage: String
        if (isiDialogUpdate) {
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
                if (isiDialogUpdate) {
                    viewModel.updateKegiatan(
                        key,
                        data?.urut.toString(),
                        isiProgram,
                        isiKegiatan,
                        context
                    )
                } else {
                    viewModel.deleteKegiatan(key, data?.urut.toString(), context)
                }
            }
            setNegativeButton("Tidak") { dialog, _ -> dialog.cancel() }
        }
        val alertDialog = alertDialogBuilder.create()
        alertDialog.show()
    }
}