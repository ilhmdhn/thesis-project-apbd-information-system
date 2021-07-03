package com.ilhmdhn.simranda.ui.laporan.dokumen

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.ilhmdhn.simranda.databinding.ActivityAnggaranDokumenBinding

class AnggaranDokumenActivity : AppCompatActivity() {

    private var _activityAnggaranDokumen: ActivityAnggaranDokumenBinding? = null
    private val binding get() = _activityAnggaranDokumen

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        _activityAnggaranDokumen = ActivityAnggaranDokumenBinding.inflate(layoutInflater)
        setContentView(binding?.root)

        supportActionBar?.hide()

        binding?.btnRkaSkpd?.setOnClickListener {
            val intent = Intent(this, DetailAnggaranDokumenActivity::class.java)
            intent.putExtra(DetailAnggaranDokumenActivity.EXTRA_KODE_DOK, "1")
            startActivity(intent)
        }

        binding?.btnApbdMurni?.setOnClickListener {
            val intent = Intent(this, DetailAnggaranDokumenActivity::class.java)
            intent.putExtra(DetailAnggaranDokumenActivity.EXTRA_KODE_DOK, "2")
            startActivity(intent)
        }

        binding?.btnDpaSkpd?.setOnClickListener {
            val intent = Intent(this, DetailAnggaranDokumenActivity::class.java)
            intent.putExtra(DetailAnggaranDokumenActivity.EXTRA_KODE_DOK, "3")
            startActivity(intent)
        }

        binding?.btnRkaSkpdPerubahan?.setOnClickListener {
            val intent = Intent(this, DetailAnggaranDokumenActivity::class.java)
            intent.putExtra(DetailAnggaranDokumenActivity.EXTRA_KODE_DOK, "4")
            startActivity(intent)
        }

        binding?.btnApbdPerubahan?.setOnClickListener {
            val intent = Intent(this, DetailAnggaranDokumenActivity::class.java)
            intent.putExtra(DetailAnggaranDokumenActivity.EXTRA_KODE_DOK, "5")
            startActivity(intent)
        }

        binding?.btnDppaSkpd?.setOnClickListener {
            val intent = Intent(this, DetailAnggaranDokumenActivity::class.java)
            intent.putExtra(DetailAnggaranDokumenActivity.EXTRA_KODE_DOK, "6")
            startActivity(intent)
        }

        binding?.btnApbdRealisasi?.setOnClickListener {
            val intent = Intent(this, DetailAnggaranDokumenActivity::class.java)
            intent.putExtra(DetailAnggaranDokumenActivity.EXTRA_KODE_DOK, "7")
            startActivity(intent)
        }
    }
}