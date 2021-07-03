package com.ilhmdhn.simranda.ui.master.rekening

import android.content.Context
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider
import com.ilhmdhn.simranda.R
import com.ilhmdhn.simranda.databinding.ActivityInputRekeningBinding
import com.ilhmdhn.simranda.viewmodel.ViewModelFactory

class InputRekeningActivity : AppCompatActivity() {

    private var _activityInputRekeningBinding: ActivityInputRekeningBinding? = null
    private val binding get() = _activityInputRekeningBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        _activityInputRekeningBinding = ActivityInputRekeningBinding.inflate(layoutInflater)
        setContentView(binding?.root)

        supportActionBar?.title = getString(R.string.insertrekening)

        val sharedPref = applicationContext.getSharedPreferences(
            R.string.setting_data.toString(),
            Context.MODE_PRIVATE
        )

        val year = sharedPref?.getString("year", getString(R.string.default_year)).toString()
        val userId = sharedPref?.getString("user", getString(R.string.default_user)).toString()
        val key = sharedPref?.getString("key", "").toString()

        val factory = ViewModelFactory.getInstance(this)
        val viewModel = ViewModelProvider(this, factory)[RekeningViewModel::class.java]

        binding?.submit?.setOnClickListener {

            if (binding?.edtRekening?.text.isNullOrEmpty()) {
                binding?.edtRekening?.error = "Kosong"
            } else if (binding?.edtDescription?.text.isNullOrEmpty()) {
                binding?.edtDescription?.error = "Kosong"
            } else {
                val rekening = binding?.edtRekening?.text.toString()
                val keterangan = binding?.edtDescription?.text.toString()
                viewModel.postRekening(key, year, rekening, keterangan, userId, applicationContext)
            }
        }
    }
}
