package com.ilhmdhn.simranda.ui.master.rekening

import android.content.Context
import android.os.Bundle
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider
import com.ilhmdhn.simranda.R
import com.ilhmdhn.simranda.data.source.local.entity.RekeningEntity
import com.ilhmdhn.simranda.databinding.ActivityUpdateRekeningBinding
import com.ilhmdhn.simranda.viewmodel.ViewModelFactory

class UpdateRekeningActivity : AppCompatActivity() {

    companion object {
        const val EXTRA_DATA = "extra_data"
        const val ALERT_DIALOG_UPDATE = 10
        const val ALERT_DIALOG_DELETE = 20
    }

    var data: RekeningEntity? = null

    private var _activityUpdateRekeningBinding: ActivityUpdateRekeningBinding? = null
    private val binding get() = _activityUpdateRekeningBinding

    val factory = ViewModelFactory.getInstance(this)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        _activityUpdateRekeningBinding = ActivityUpdateRekeningBinding.inflate(layoutInflater)
        setContentView(binding?.root)

        data = intent.getParcelableExtra<RekeningEntity>(EXTRA_DATA) as RekeningEntity

        supportActionBar?.title = data?.namaRekening

        val sharedPref = applicationContext.getSharedPreferences(
            R.string.setting_data.toString(),
            Context.MODE_PRIVATE
        )

        val key = sharedPref?.getString("key", getString(R.string.default_year)).toString()

        binding?.tvDetailRekening?.text = data?.kodeRekening
        binding?.tvDetailUser?.text = data?.username
        binding?.tvDetailDate?.text = data?.tanggalInput

        binding?.edtKeterangan?.setText(data?.namaRekening)

        binding?.btnDelete?.setOnClickListener {
            showAlertDialog(key, ALERT_DIALOG_DELETE, "", applicationContext)
        }

        binding?.btnUpdate?.setOnClickListener {
            if (binding?.edtKeterangan?.text.isNullOrEmpty()) {
                binding?.edtKeterangan?.error = "Kosong"
            } else {
                showAlertDialog(
                    key,
                    ALERT_DIALOG_UPDATE,
                    binding?.edtKeterangan?.text.toString(),
                    applicationContext
                )
            }
        }
    }

    private fun showAlertDialog(key: String, type: Int, isiUpdate: String, context: Context) {
        val viewModel = ViewModelProvider(this, factory)[RekeningViewModel::class.java]
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
                    viewModel.updateRekening(key, data!!.urutan, isiUpdate, context)
                } else {
                    viewModel.deleteRekening(key, data!!.urutan, context)
                }
            }
            setNegativeButton("Tidak") { dialog, _ -> dialog.cancel() }
        }
        val alertDialog = alertDialogBuilder.create()
        alertDialog.show()
    }
}