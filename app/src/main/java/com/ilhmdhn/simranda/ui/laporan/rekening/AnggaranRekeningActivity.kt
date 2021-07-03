package com.ilhmdhn.simranda.ui.laporan.rekening

import android.annotation.SuppressLint
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.AdapterView
import android.widget.ArrayAdapter
import android.widget.Toast
import com.ilhmdhn.simranda.R
import com.ilhmdhn.simranda.data.source.local.entity.RekeningEntity
import com.ilhmdhn.simranda.databinding.ActivityAnggaranRekeningBinding
import com.ilhmdhn.simranda.ui.anggaran.SelectRekeningActivity
import com.ilhmdhn.simranda.ui.anggaran.SelectRekeningAdapter

class AnggaranRekeningActivity : AppCompatActivity(), AdapterView.OnItemSelectedListener {

    private var _activityAnggaranRekeningBinding: ActivityAnggaranRekeningBinding? = null
    private val binding get() = _activityAnggaranRekeningBinding

    private var selectedRekening: RekeningEntity? = null
    private var kodeDok: String? = null

    companion object {
        private const val REQUEST_REK = 130
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        _activityAnggaranRekeningBinding = ActivityAnggaranRekeningBinding.inflate(layoutInflater)
        setContentView(binding?.root)

        supportActionBar?.hide()

        binding?.dokSpinner?.onItemSelectedListener = this


        ArrayAdapter.createFromResource(
            this,
            R.array.jenis_dokumen,
            android.R.layout.simple_spinner_item
        ).also { adapter ->
            adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
            binding?.dokSpinner?.adapter = adapter
        }

        binding?.btnSelectRekening?.setOnClickListener {
            val moveForResultRek = Intent(this, SelectRekeningActivity::class.java)
            startActivityForResult(moveForResultRek, REQUEST_REK)
        }
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)

        if (requestCode == REQUEST_REK) {
            if (resultCode == SelectRekeningAdapter.RESULT_REK) {
                selectedRekening =
                    data?.getParcelableExtra<RekeningEntity>(SelectRekeningAdapter.EXTRA_SELECT_REK)
            }
        }
    }

    @SuppressLint("SetTextI18n")
    override fun onResume() {
        super.onResume()
        if (selectedRekening?.kodeRekening != null) {
            binding?.edtRekening?.setText("${selectedRekening?.kodeRekening} / ${selectedRekening?.namaRekening}")
        }

        binding?.btnSubmit?.setOnClickListener {
            if (binding?.edtRekening?.text.isNullOrEmpty()) {
                binding?.btnSelectRekening?.error = "Pilih rekening"
                Toast.makeText(this, "Pilih kode rekening terlebih dahulu", Toast.LENGTH_SHORT)
                    .show()
            } else if (kodeDok == null) {
                Toast.makeText(this, "Pilih jenig dokumen telebih dahulu", Toast.LENGTH_SHORT)
                    .show()
            } else {
                val intent = Intent(this, DetailAnggaranRekeningActivity::class.java)
                intent.putExtra(DetailAnggaranRekeningActivity.EXTRA_KODE_DOK, kodeDok)
                intent.putExtra(
                    DetailAnggaranRekeningActivity.EXTRA_KODE_REK,
                    selectedRekening?.kodeRekening
                )
                intent.putExtra(
                    DetailAnggaranRekeningActivity.EXTRA_NAME_REK,
                    selectedRekening?.namaRekening
                )
                startActivity(intent)
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