package com.ilhmdhn.simranda.ui.anggaran

import android.app.Activity
import android.content.Intent
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.paging.PagedListAdapter
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.ilhmdhn.simranda.data.source.local.entity.ProgramDanKegiatanEntity
import com.ilhmdhn.simranda.databinding.ListKegiatanBinding

class SelectKegiatanAdapter :
    PagedListAdapter<ProgramDanKegiatanEntity, SelectKegiatanAdapter.SelectKegiatanViewHolder>(
        DIFF_CALLBACK
    ) {

    companion object {
        private val DIFF_CALLBACK = object : DiffUtil.ItemCallback<ProgramDanKegiatanEntity>() {
            override fun areItemsTheSame(
                oldItem: ProgramDanKegiatanEntity,
                newItem: ProgramDanKegiatanEntity
            ): Boolean {
                return oldItem.urut == newItem.urut
            }

            override fun areContentsTheSame(
                oldItem: ProgramDanKegiatanEntity,
                newItem: ProgramDanKegiatanEntity
            ): Boolean {
                return oldItem == newItem
            }
        }

        const val EXTRA_SELECT_KEG = "extra_select_keg"
        const val RESULT_KEG = 120
    }

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): SelectKegiatanAdapter.SelectKegiatanViewHolder {
        val listKegiatanBinding =
            ListKegiatanBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return SelectKegiatanViewHolder(listKegiatanBinding)
    }

    override fun onBindViewHolder(
        holder: SelectKegiatanAdapter.SelectKegiatanViewHolder,
        position: Int
    ) {
        val kegiatan = getItem(position)
        if (kegiatan != null) {
            holder.bind(kegiatan)
        }
    }

    class SelectKegiatanViewHolder(private val binding: ListKegiatanBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(programDanKegiatanEntity: ProgramDanKegiatanEntity) {
            with(binding) {
                tvKodeKeg.text = programDanKegiatanEntity.kodeKegiatan
                tvKodeOrganisasi.text = programDanKegiatanEntity.kodeOrganisasi
                tvNamaProgram.text = programDanKegiatanEntity.namaProgram
                tvNamaKegiatan.text = programDanKegiatanEntity.namaKegiatan
                itemView.setOnClickListener {
                    val resultIntent = Intent()
                    resultIntent.putExtra(EXTRA_SELECT_KEG, programDanKegiatanEntity)
                    (itemView.context as Activity).setResult(RESULT_KEG, resultIntent)
                    (itemView.context as Activity).finish()
                }
            }
        }
    }
}