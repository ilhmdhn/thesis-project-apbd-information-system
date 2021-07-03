package com.ilhmdhn.simranda.ui.master.programdankegiatan

import android.app.Activity
import android.content.Intent
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.paging.PagedListAdapter
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.ilhmdhn.simranda.data.source.local.entity.OrganisasiEntity
import com.ilhmdhn.simranda.databinding.ListSelectOrganisasiBinding

class SelectOrganisasiAdapter :
    PagedListAdapter<OrganisasiEntity, SelectOrganisasiAdapter.ListOrganisasiViewHolder>(
        DIFF_CALLBACK
    ) {

    companion object {
        private val DIFF_CALLBACK = object : DiffUtil.ItemCallback<OrganisasiEntity>() {
            override fun areItemsTheSame(
                oldItem: OrganisasiEntity,
                newItem: OrganisasiEntity
            ): Boolean {
                return oldItem.urut == newItem.urut
            }

            override fun areContentsTheSame(
                oldItem: OrganisasiEntity,
                newItem: OrganisasiEntity
            ): Boolean {
                return oldItem == newItem
            }
        }

        const val EXTRA_SELECTED_ORG = "extra_selected_value"
        const val RESULT_ORG = 110
    }

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): SelectOrganisasiAdapter.ListOrganisasiViewHolder {
        val listSelectOrganisasiBinding =
            ListSelectOrganisasiBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ListOrganisasiViewHolder(listSelectOrganisasiBinding)
    }

    override fun onBindViewHolder(
        holder: SelectOrganisasiAdapter.ListOrganisasiViewHolder,
        position: Int
    ) {
        val organisasi = getItem(position)
        if (organisasi != null) {
            holder.bind(organisasi)
        }
    }

    class ListOrganisasiViewHolder(private val binding: ListSelectOrganisasiBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(organisasiEntity: OrganisasiEntity) {
            with(binding) {
                tvKodeOrganisasi.text = organisasiEntity.kodeOrg
                tvNamaOrg.text = organisasiEntity.namaOrg
                tvKodeBagian.text = organisasiEntity.kodeBag
                tvNamaBagian.text = organisasiEntity.namaBag
                itemView.setOnClickListener {
                    val resultIntent = Intent()
                    resultIntent.putExtra(EXTRA_SELECTED_ORG, organisasiEntity)
                    (itemView.context as Activity).setResult(RESULT_ORG, resultIntent)
                    (itemView.context as Activity).finish()
                }
            }
        }
    }
}