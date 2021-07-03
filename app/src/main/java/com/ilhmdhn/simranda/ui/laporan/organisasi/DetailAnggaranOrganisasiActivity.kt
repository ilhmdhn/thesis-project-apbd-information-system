package com.ilhmdhn.simranda.ui.laporan.organisasi

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.ilhmdhn.simranda.databinding.ActivityDetailAnggaranOrganisasiBinding

class DetailAnggaranOrganisasiActivity : AppCompatActivity() {

    private var _activityDetailAnggaranOrganisasiBinding: ActivityDetailAnggaranOrganisasiBinding? =
        null
    private val binding get() = _activityDetailAnggaranOrganisasiBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        _activityDetailAnggaranOrganisasiBinding =
            ActivityDetailAnggaranOrganisasiBinding.inflate(layoutInflater)
        setContentView(binding?.root)
    }
}