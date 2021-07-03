package com.ilhmdhn.simranda.ui.master.programdankegiatan

import android.content.Intent
import android.util.Log
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.paging.PagedListAdapter
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.ilhmdhn.simranda.data.source.local.entity.ProgramDanKegiatanEntity
import com.ilhmdhn.simranda.databinding.ListKegiatanBinding

class ProgramDanKegiatanAdapter :
    PagedListAdapter<ProgramDanKegiatanEntity, ProgramDanKegiatanAdapter.ProgramDanKegiatanViewHolder>(
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
    }

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): ProgramDanKegiatanViewHolder {
        val listKegiatanBinding =
            ListKegiatanBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ProgramDanKegiatanViewHolder(listKegiatanBinding)
    }

    override fun onBindViewHolder(holder: ProgramDanKegiatanViewHolder, position: Int) {
        val kegiatan = getItem(position)
        Log.d("cekdata", kegiatan.toString())
        if (kegiatan != null) {
            holder.bind(kegiatan)
        }
    }

    class ProgramDanKegiatanViewHolder(private val binding: ListKegiatanBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(kegiatanEntity: ProgramDanKegiatanEntity) {
            with(binding) {
                tvKodeKeg.text = kegiatanEntity.kodeKegiatan
                tvKodeOrganisasi.text = kegiatanEntity.kodeOrganisasi
                tvNamaKegiatan.text = kegiatanEntity.namaKegiatan
                tvNamaProgram.text = kegiatanEntity.namaProgram
                itemView.setOnClickListener {
                    val intent = Intent(itemView.context, UpdateKegiatanActivity::class.java)
                    intent.putExtra(UpdateKegiatanActivity.EXTRA_DATA, kegiatanEntity)
                    itemView.context.startActivity(intent)
                }
            }
        }
    }
}