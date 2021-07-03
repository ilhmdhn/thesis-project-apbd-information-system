package com.ilhmdhn.simranda.ui.anggaran

import android.content.Intent
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.ilhmdhn.simranda.R
import com.ilhmdhn.simranda.databinding.FragmentListAnggaranBinding

class ListAnggaranFragment : Fragment() {

    private var _fragmentListAnggaran: FragmentListAnggaranBinding? = null
    private val binding get() = _fragmentListAnggaran

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _fragmentListAnggaran =
            FragmentListAnggaranBinding.inflate(layoutInflater, container, false)
        return binding?.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding?.btnRkaSkpd?.setOnClickListener {
            val intent = Intent(activity, ListAnggaranActivity::class.java)
            intent.putExtra(ListAnggaranActivity.EXTRA_KODE_DOK, "1")
            startActivity(intent)
        }

        binding?.btnApbdMurni?.setOnClickListener {
            val intent = Intent(activity, ListAnggaranActivity::class.java)
            intent.putExtra(ListAnggaranActivity.EXTRA_KODE_DOK, "2")
            startActivity(intent)
        }

        binding?.btnDpaSkpd?.setOnClickListener {
            val intent = Intent(activity, ListAnggaranActivity::class.java)
            intent.putExtra(ListAnggaranActivity.EXTRA_KODE_DOK, "3")
            startActivity(intent)
        }

        binding?.btnRkaSkpdPerubahan?.setOnClickListener {
            val intent = Intent(activity, ListAnggaranActivity::class.java)
            intent.putExtra(ListAnggaranActivity.EXTRA_KODE_DOK, "4")
            startActivity(intent)
        }

        binding?.btnApbdPerubahan?.setOnClickListener {
            val intent = Intent(activity, ListAnggaranActivity::class.java)
            intent.putExtra(ListAnggaranActivity.EXTRA_KODE_DOK, "5")
            startActivity(intent)
        }

        binding?.btnDppaSkpd?.setOnClickListener {
            val intent = Intent(activity, ListAnggaranActivity::class.java)
            intent.putExtra(ListAnggaranActivity.EXTRA_KODE_DOK, "6")
            startActivity(intent)
        }

        binding?.btnApbdRealisasi?.setOnClickListener {
            val intent = Intent(activity, ListAnggaranActivity::class.java)
            intent.putExtra(ListAnggaranActivity.EXTRA_KODE_DOK, "7")
            startActivity(intent)
        }
    }
}