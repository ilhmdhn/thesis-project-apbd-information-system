package com.ilhmdhn.simranda.ui.user

import android.content.Context
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.RadioButton
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.lifecycle.ViewModelProvider
import com.ilhmdhn.simranda.R
import com.ilhmdhn.simranda.data.source.remote.response.DataUserItem
import com.ilhmdhn.simranda.databinding.ActivityUpdateUserBinding
import com.ilhmdhn.simranda.ui.master.organisasi.UpdateOrganisasiActivity
import com.ilhmdhn.simranda.viewmodel.ViewModelFactory

class UpdateUserActivity : AppCompatActivity() {

    companion object {
        const val EXTRA_DATA = "extra_data"
        const val ALERT_DIALOG_UPDATE = 10
        const val ALERT_DIALOG_DELETE = 20
    }

    var data: DataUserItem? = null
    val factory = ViewModelFactory.getInstance(this)
    private var userLevel: String? = null

    private var _activityUpdateUser: ActivityUpdateUserBinding? = null
    private val binding get() = _activityUpdateUser

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        _activityUpdateUser = ActivityUpdateUserBinding.inflate(layoutInflater)
        setContentView(binding?.root)

        data = intent.getParcelableExtra<DataUserItem>(EXTRA_DATA) as DataUserItem

        binding?.edtFullname?.setText(data?.fullname)
        binding?.edtPassword?.setText(data?.password)
        binding?.edtVerifPassowrd?.setText(data?.password)

        if (data?.level == "3") {
            binding?.radioGuest?.isChecked = true
            userLevel = "3"
        } else if (data?.level == "2") {
            binding?.radioOperator?.isChecked = true
            userLevel = "2"
        } else if (data?.level == "1") {
            userLevel = "1"
            binding?.radioGuest?.visibility = View.GONE
            binding?.radioOperator?.visibility = View.GONE
        }

        binding?.btnUpdate?.setOnClickListener {
            if (binding?.edtFullname?.text.isNullOrEmpty()) {
                binding?.edtFullname?.error = "Kosong"
            } else if (binding?.edtPassword?.text.isNullOrEmpty()) {
                binding?.edtPassword?.error = "Kosong"
            } else if (binding?.edtVerifPassowrd?.text.isNullOrEmpty()) {
                binding?.edtVerifPassowrd?.error = "Kosong"
            } else if (userLevel == null) {
                binding?.radioGuest?.error = "pilih salah satu"
                binding?.radioOperator?.error = "pilih salah satu"
            } else if (binding?.edtPassword?.text.toString() != binding?.edtVerifPassowrd?.text.toString()) {
                binding?.edtPassword?.error = "Tidak Sama"
                binding?.edtVerifPassowrd?.error = "Tidak Sama"
                Toast.makeText(this, "Password berbeda", Toast.LENGTH_SHORT).show()
            } else {
                val fullname = binding?.edtFullname?.text.toString()
                val password = binding?.edtVerifPassowrd?.text.toString()

                showAlertDialog(
                    ALERT_DIALOG_UPDATE,
                    fullname,
                    password,
                    userLevel,
                    applicationContext
                )
            }
        }

        binding?.btnDelete?.setOnClickListener {
            showAlertDialog(ALERT_DIALOG_DELETE, "", "", "", applicationContext)
        }
    }

    private fun showAlertDialog(
        type: Int,
        fullname: String,
        password: String,
        userType: String?,
        context: Context
    ) {

        val sharedPref = applicationContext.getSharedPreferences(
            R.string.setting_data.toString(),
            Context.MODE_PRIVATE
        )
        val key = sharedPref?.getString("key", "").toString()

        val viewModel = ViewModelProvider(this, factory)[UserViewModel::class.java]
        val isDialogUpdate = type == UpdateOrganisasiActivity.ALERT_DIALOG_UPDATE
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
                    viewModel.updateUser(
                        key,
                        data?.username.toString(),
                        fullname,
                        userType!!,
                        password,
                        context
                    )
                    Toast.makeText(
                        context,
                        data?.username.toString() + fullname + userType + password,
                        Toast.LENGTH_LONG
                    ).show()
                } else {
                    viewModel.deleteUser(key, data?.username.toString(), context)
                }
            }
            setNegativeButton("Tidak") { dialog, _ -> dialog.cancel() }
        }
        val alertDialog = alertDialogBuilder.create()
        alertDialog.show()
    }

    fun onRadioButtonClicked(view: View) {
        if (view is RadioButton) {
            val checked = view.isChecked

            when (view.getId()) {
                R.id.radio_guest ->
                    if (checked) {
                        userLevel = "3"
                    }
                R.id.radio_operator ->
                    if (checked) {
                        userLevel = "2"
                    }
            }
        }
    }
}