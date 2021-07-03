package com.ilhmdhn.simranda.ui.anggaran

import android.content.Intent
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.paging.PagedListAdapter
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.ilhmdhn.simranda.data.source.local.entity.AnggaranEntity
import com.ilhmdhn.simranda.databinding.ListAnggaranBinding
import com.ilhmdhn.simranda.utils.Utils

class AnggaranAdapter :
    PagedListAdapter<AnggaranEntity, AnggaranAdapter.AnggaranViewHolder>(DIFF_CALLBACK) {

    companion object {
        private val DIFF_CALLBACK = object : DiffUtil.ItemCallback<AnggaranEntity>() {
            override fun areItemsTheSame(
                oldItem: AnggaranEntity,
                newItem: AnggaranEntity
            ): Boolean {
                return oldItem.id == newItem.id
            }

            override fun areContentsTheSame(
                oldItem: AnggaranEntity,
                newItem: AnggaranEntity
            ): Boolean {
                return oldItem == newItem
            }

        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): AnggaranViewHolder {
        val listAnggaranBinding =
            ListAnggaranBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return AnggaranViewHolder(listAnggaranBinding)
    }

    override fun onBindViewHolder(holder: AnggaranViewHolder, position: Int) {
        val anggaran = getItem(position)
        if (anggaran != null) {
            holder.bind(anggaran)
        }
    }

    class AnggaranViewHolder(private val binding: ListAnggaranBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(anggaranEntity: AnggaranEntity) {
            with(binding) {
                tvKodeSkpd.text = anggaranEntity.kodeOrganisasi
                tvId.text = anggaranEntity.id
                tvKodeBagian.text = anggaranEntity.kodeBagian
                tvKodeProgram.text = Utils.generateKodeProgram(anggaranEntity.kodeKegiatan)
                tvKodeKegiatan.text = Utils.generateKodeKegiatan(anggaranEntity.kodeKegiatan)
                tvKodeRekening.text = Utils.generateKodeRekening(anggaranEntity.kodeRekening)
                tvHalamanDokumen.text = anggaranEntity.halDokumen
                tvNominalAnggaran.text =
                    Utils.getCurrency(anggaranEntity.nominalAnggaran?.toDouble())
                itemView.setOnClickListener {
                    val intent = Intent(itemView.context, UpdateAnggaranActivity::class.java)
                    intent.putExtra(UpdateAnggaranActivity.EXTRA_DATA, anggaranEntity)
                    itemView.context.startActivity(intent)
                }
            }
        }
    }
}