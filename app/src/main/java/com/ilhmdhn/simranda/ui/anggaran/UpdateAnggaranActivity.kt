package com.ilhmdhn.simranda.ui.anggaran

import android.content.Context
import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider
import com.ilhmdhn.simranda.R
import com.ilhmdhn.simranda.data.source.local.entity.AnggaranEntity
import com.ilhmdhn.simranda.databinding.ActivityUpdateAnggaranBinding
import com.ilhmdhn.simranda.utils.Utils
import com.ilhmdhn.simranda.viewmodel.ViewModelFactory

class UpdateAnggaranActivity : AppCompatActivity() {

    companion object {
        const val EXTRA_DATA = "extra_data"
        const val ALERT_DIALOG_UPDATE = 10
        const val ALERT_DIALOG_DELETE = 20
    }

    var data: AnggaranEntity? = null
    val factory = ViewModelFactory.getInstance(this)

    private var _activityupdateAnggaranActivity: ActivityUpdateAnggaranBinding? = null
    private val binding get() = _activityupdateAnggaranActivity

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        _activityupdateAnggaranActivity = ActivityUpdateAnggaranBinding.inflate(layoutInflater)
        setContentView(binding?.root)

        data = intent.getParcelableExtra<AnggaranEntity>(EXTRA_DATA) as AnggaranEntity

        when (data?.kodeDokumen) {
            "1" -> title = "RKA SKPD"
            "2" -> title = "APBD MURNI"
            "3" -> title = "DPA SKPD"
            "4" -> title = "RKA SKPD PERUBAHAN"
            "5" -> title = "APBD PERUBAHAN"
            "6" -> title = "DPPA SKPD"
            "7" -> title = "APBD REALISASI"
        }

        binding?.edtNominalAnggaran?.setText(data?.nominalAnggaran)

        val viewModel = ViewModelProvider(this, factory)[AnggaranViewModel::class.java]

        viewModel.getName(data!!.id).observe(this, { data ->
            if (data != null) {
                binding?.tvNamaOrganisasi?.text = data.nama_organisasi
                binding?.tvNamaBagian?.text = data.nama_bagian
                binding?.tvNamaProgram?.text = data.nama_program
                binding?.tvNamaKegiatan?.text = data.nama_kegiatan
                binding?.tvNamaRekening?.text = data.nama_rekening
            } else {
                Toast.makeText(
                    this,
                    "data Organisasi null silahkan buka master organisasi",
                    Toast.LENGTH_SHORT
                ).show()
            }
        })


        binding?.tvTahun?.text = data?.tahun
        binding?.tvTglEntry?.text = data?.tanggalInput
        binding?.tvUserId?.text = data?.username
        binding?.tvKodeOrganisasi?.text = data?.kodeOrganisasi
        binding?.tvKodeBagian?.text = data?.kodeBagian
        binding?.tvHalamanDokumen?.text = data?.halDokumen
        binding?.tvKodeProgram?.text = Utils.generateKodeProgram(data?.kodeKegiatan)
        binding?.tvKodeKegiatan?.text = Utils.generateKodeKegiatan(data?.kodeKegiatan)
        binding?.tvKodeRekening?.text = data?.kodeRekening
        binding?.tvIdData?.text = data?.id

        binding?.btnUpdate?.setOnClickListener {
            if (binding?.edtNominalAnggaran?.text.isNullOrEmpty()) {
                binding?.edtNominalAnggaran?.error = "Kosong"
            } else {
                showAlertDialog(
                    ALERT_DIALOG_UPDATE,
                    binding?.edtNominalAnggaran?.text.toString(),
                    applicationContext
                )
            }
        }

        binding?.btnDelete?.setOnClickListener {
            showAlertDialog(ALERT_DIALOG_DELETE, "", applicationContext)
        }
    }

    private fun showAlertDialog(type: Int, nominalAnggaran: String, context: Context) {

        val sharedPref = applicationContext.getSharedPreferences(
            R.string.setting_data.toString(),
            Context.MODE_PRIVATE
        )
        val key = sharedPref?.getString("key", "").toString()

        val viewModel = ViewModelProvider(this, factory)[AnggaranViewModel::class.java]
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
                    viewModel.updateAnggaran(key, data?.id!!, nominalAnggaran, context)
                } else {
                    viewModel.deleteAnggaran(key, data?.id!!, context)
                }
            }
            setNegativeButton("Tidak") { dialog, _ -> dialog.cancel() }
        }
        val alertDialog = alertDialogBuilder.create()
        alertDialog.show()
    }
}