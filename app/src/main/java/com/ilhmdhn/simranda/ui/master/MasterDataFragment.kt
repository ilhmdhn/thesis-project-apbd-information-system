package com.ilhmdhn.simranda.ui.master

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.ilhmdhn.simranda.databinding.FragmentMasterDataBinding
import com.ilhmdhn.simranda.ui.master.organisasi.ListOrganisasiActivity
import com.ilhmdhn.simranda.ui.master.programdankegiatan.ListKegiatanActivity
import com.ilhmdhn.simranda.ui.master.rekening.ListRekeningActivity

class MasterDataFragment : Fragment() {

    private var _fragmentMasterData: FragmentMasterDataBinding? = null
    private val binding get() = _fragmentMasterData

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _fragmentMasterData = FragmentMasterDataBinding.inflate(layoutInflater, container, false)
        return binding?.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding?.rlMasterRekening?.setOnClickListener {
            startActivity(Intent(activity, ListRekeningActivity::class.java))
        }

        binding?.rlOrganisasi?.setOnClickListener {
            startActivity(Intent(activity, ListOrganisasiActivity::class.java))
        }

        binding?.rlProgramDanKegiatan?.setOnClickListener {
            startActivity(Intent(activity, ListKegiatanActivity::class.java))
        }
    }
}