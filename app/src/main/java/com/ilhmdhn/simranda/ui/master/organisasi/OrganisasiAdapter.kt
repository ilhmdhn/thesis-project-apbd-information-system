package com.ilhmdhn.simranda.ui.master.organisasi

import android.annotation.SuppressLint
import android.content.Intent
import android.util.Log
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.paging.PagedListAdapter
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.ilhmdhn.simranda.data.source.local.entity.OrganisasiEntity
import com.ilhmdhn.simranda.databinding.ActivityInsertOrganisasiBinding
import com.ilhmdhn.simranda.databinding.ListOrganisasiBinding

class OrganisasiAdapter :
    PagedListAdapter<OrganisasiEntity, OrganisasiAdapter.OrganisasiMasterViewHolder>(DIFF_CALLBACK) {

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
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): OrganisasiMasterViewHolder {
        val listOrganisasiBinding =
            ListOrganisasiBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return OrganisasiMasterViewHolder(listOrganisasiBinding)
    }

    override fun onBindViewHolder(holder: OrganisasiMasterViewHolder, position: Int) {
        val organisasi = getItem(position)
        if (organisasi != null) {
            holder.bind(organisasi)
        }
    }

    class OrganisasiMasterViewHolder(private val binding: ListOrganisasiBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(organisasiEntity: OrganisasiEntity) {
            with(binding) {
                tvKodeOrganisasi.text = organisasiEntity.kodeOrg
                tvNamaOrg.text = organisasiEntity.namaOrg
                tvNamaBagian.text = organisasiEntity.namaBag
                tvNamaFungsi.text = organisasiEntity.namaFungsi
                itemView.setOnClickListener {
                    val intent = Intent(itemView.context, UpdateOrganisasiActivity::class.java)
                    intent.putExtra(UpdateOrganisasiActivity.EXTRA_DATA, organisasiEntity)
                    itemView.context.startActivity(intent)
                }
            }
        }
    }
}