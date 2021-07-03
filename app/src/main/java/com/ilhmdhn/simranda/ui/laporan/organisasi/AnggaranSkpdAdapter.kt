package com.ilhmdhn.simranda.ui.laporan.organisasi

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.paging.PagedListAdapter
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.ilhmdhn.simranda.data.source.local.entity.LaporanAnggaranSkpdEntity
import com.ilhmdhn.simranda.databinding.ListAnggaranOrganisasiBinding

class AnggaranSkpdAdapter :
    PagedListAdapter<LaporanAnggaranSkpdEntity, AnggaranSkpdAdapter.AnggaranOrganisasiViewHolder>(
        DIFF_CALLBACK
    ) {

    companion object {
        private val DIFF_CALLBACK = object : DiffUtil.ItemCallback<LaporanAnggaranSkpdEntity>() {
            override fun areItemsTheSame(
                oldItem: LaporanAnggaranSkpdEntity,
                newItem: LaporanAnggaranSkpdEntity
            ): Boolean {
                return oldItem.tahun == newItem.tahun
            }

            override fun areContentsTheSame(
                oldItem: LaporanAnggaranSkpdEntity,
                newItem: LaporanAnggaranSkpdEntity
            ): Boolean {
                return oldItem == newItem
            }

        }
    }

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): AnggaranOrganisasiViewHolder {
        val listAnggaranOrganisasiBinding = ListAnggaranOrganisasiBinding.inflate(
            LayoutInflater.from(parent.context),
            parent,
            false
        )
        return AnggaranOrganisasiViewHolder(listAnggaranOrganisasiBinding)
    }

    override fun onBindViewHolder(holder: AnggaranOrganisasiViewHolder, position: Int) {
        val anggaranSkpd = getItem(position)
        if (anggaranSkpd != null) {
            holder.bind(anggaranSkpd)
        }
    }

    class AnggaranOrganisasiViewHolder(private val binding: ListAnggaranOrganisasiBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(laporanAnggaranSkpdEntity: LaporanAnggaranSkpdEntity) {
            with(binding) {

            }
        }
    }
}