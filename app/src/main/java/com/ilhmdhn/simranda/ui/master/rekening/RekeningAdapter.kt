package com.ilhmdhn.simranda.ui.master.rekening

import android.content.Intent
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.paging.PagedListAdapter
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.ilhmdhn.simranda.data.source.local.entity.RekeningEntity
import com.ilhmdhn.simranda.databinding.ListRekeningBinding

class RekeningAdapter :
    PagedListAdapter<RekeningEntity, RekeningAdapter.RekeningMasterViewHolder>(DIFF_CALLBACK) {

    companion object {
        private val DIFF_CALLBACK = object : DiffUtil.ItemCallback<RekeningEntity>() {
            override fun areItemsTheSame(
                oldItem: RekeningEntity,
                newItem: RekeningEntity
            ): Boolean {
                return oldItem.urutan == newItem.urutan
            }

            override fun areContentsTheSame(
                oldItem: RekeningEntity,
                newItem: RekeningEntity
            ): Boolean {
                return oldItem == newItem
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RekeningMasterViewHolder {
        val listRekeningBinding =
            ListRekeningBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return RekeningMasterViewHolder(listRekeningBinding)
    }

    override fun onBindViewHolder(holder: RekeningMasterViewHolder, position: Int) {
        val rekening = getItem(position)
        if (rekening != null) {
            holder.bind(rekening)
        }
    }

    class RekeningMasterViewHolder(private val binding: ListRekeningBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(rekeningEntity: RekeningEntity?) {
            with(binding) {
                tvRekening.text = rekeningEntity?.kodeRekening
                tvKeterangan.text = rekeningEntity?.namaRekening

                itemView.setOnClickListener {
                    val intent = Intent(itemView.context, UpdateRekeningActivity::class.java)
                    intent.putExtra(UpdateRekeningActivity.EXTRA_DATA, rekeningEntity)
                    itemView.context.startActivity(intent)
                }
            }
        }
    }
}