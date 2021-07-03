package com.ilhmdhn.simranda.ui.laporan.dokumen

import android.graphics.Typeface
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.paging.PagedListAdapter
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.ilhmdhn.simranda.R
import com.ilhmdhn.simranda.data.source.local.entity.AnggaranDokumenEntity
import com.ilhmdhn.simranda.databinding.AnggaranDokumenBinding
import com.ilhmdhn.simranda.utils.Utils

class AnggaranDokumenAdapter :
    PagedListAdapter<AnggaranDokumenEntity, AnggaranDokumenAdapter.AnggaranDokumenViewHolder>(
        DIFF_CALLBACK
    ) {

    companion object {
        private val DIFF_CALLBACK = object : DiffUtil.ItemCallback<AnggaranDokumenEntity>() {
            override fun areItemsTheSame(
                oldItem: AnggaranDokumenEntity,
                newItem: AnggaranDokumenEntity
            ): Boolean {
                return oldItem.tahun == newItem.tahun
            }

            override fun areContentsTheSame(
                oldItem: AnggaranDokumenEntity,
                newItem: AnggaranDokumenEntity
            ): Boolean {
                return oldItem == newItem
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): AnggaranDokumenViewHolder {
        val ringkasanAnggaranBinding =
            AnggaranDokumenBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return AnggaranDokumenViewHolder(ringkasanAnggaranBinding)
    }

    override fun onBindViewHolder(holderDokumen: AnggaranDokumenViewHolder, position: Int) {
        val ringkasanAnggaan = getItem(position)
        if (ringkasanAnggaan != null) {
            holderDokumen.bind(ringkasanAnggaan)
        }
    }

    class AnggaranDokumenViewHolder(private val binding: AnggaranDokumenBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(laporanRingkasanEntity: AnggaranDokumenEntity) {
            with(binding) {

                tvIdRek.text = laporanRingkasanEntity.kodeRekening
                tvNamaRek.text = laporanRingkasanEntity.namaRekening
                tvNominal.text = Utils.getCurrency(laporanRingkasanEntity.jumlah?.toDouble())

                if (laporanRingkasanEntity.jumlahDua != null) {
                    tvIdRek.setTypeface(null, Typeface.BOLD)
                    tvNamaRek.setTypeface(null, Typeface.BOLD)
                    tvNominal.setTypeface(null, Typeface.BOLD)
                    tvNominal.text = Utils.getCurrency(laporanRingkasanEntity.jumlah?.toDouble())
                    if (laporanRingkasanEntity.kodeRekening == "" && laporanRingkasanEntity.jumlah != null) {
                        layoutParent.setBackgroundResource(R.drawable.vertical_border)
                    } else {
                        layoutParent.setBackgroundResource(0)
                    }
                } else {
                    tvIdRek.setTypeface(null, Typeface.NORMAL)
                    tvNamaRek.setTypeface(null, Typeface.NORMAL)
                    tvNominal.setTypeface(null, Typeface.NORMAL)
                    layoutParent.setBackgroundResource(0)
                }
            }
        }
    }
}