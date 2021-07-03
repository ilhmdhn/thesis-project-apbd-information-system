package com.ilhmdhn.simranda.ui.laporan

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.ilhmdhn.simranda.databinding.FragmentLaporanMenuBinding
import com.ilhmdhn.simranda.ui.laporan.rekening.AnggaranRekeningActivity
import com.ilhmdhn.simranda.ui.laporan.dokumen.AnggaranDokumenActivity
import com.ilhmdhn.simranda.ui.laporan.organisasi.AnggaranOrganisasiActivity

class LaporanMenuFragment : Fragment() {

    private var _fragmentMenuAnggaranBinding: FragmentLaporanMenuBinding? = null
    private val binding get() = _fragmentMenuAnggaranBinding

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _fragmentMenuAnggaranBinding =
            FragmentLaporanMenuBinding.inflate(layoutInflater, container, false)
        return binding?.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding?.btnRingkasanAnggaran?.setOnClickListener {
            startActivity(Intent(activity, AnggaranDokumenActivity::class.java))
        }

        binding?.btnLaporanSkpd?.setOnClickListener {
            startActivity(Intent(activity, AnggaranOrganisasiActivity::class.java))
        }

        binding?.btnFilterAnggaran?.setOnClickListener {
            startActivity(Intent(activity, AnggaranRekeningActivity::class.java))
        }
    }
}