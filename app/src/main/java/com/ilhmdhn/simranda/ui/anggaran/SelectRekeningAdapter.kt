package com.ilhmdhn.simranda.ui.anggaran

import android.app.Activity
import android.content.Intent
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.paging.PagedListAdapter
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.ilhmdhn.simranda.data.source.local.entity.RekeningEntity
import com.ilhmdhn.simranda.databinding.ListRekeningBinding

class SelectRekeningAdapter :
    PagedListAdapter<RekeningEntity, SelectRekeningAdapter.SelectRekeningViewHolder>(DIFF_CALLBACK) {

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

        const val EXTRA_SELECT_REK = "extra_select_rek"
        const val RESULT_REK = 130
    }

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): SelectRekeningAdapter.SelectRekeningViewHolder {
        val listRekeningBinding =
            ListRekeningBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return SelectRekeningViewHolder(listRekeningBinding)
    }

    override fun onBindViewHolder(
        holder: SelectRekeningAdapter.SelectRekeningViewHolder,
        position: Int
    ) {
        val rekening = getItem(position)
        if (rekening != null) {
            holder.bind(rekening)
        }
    }

    class SelectRekeningViewHolder(private val binding: ListRekeningBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(rekeningEntity: RekeningEntity) {
            with(binding) {
                tvRekening.text = rekeningEntity.kodeRekening
                tvKeterangan.text = rekeningEntity.namaRekening
                itemView.setOnClickListener {
                    val resultIntent = Intent()
                    resultIntent.putExtra(EXTRA_SELECT_REK, rekeningEntity)
                    (itemView.context as Activity).setResult(RESULT_REK, resultIntent)
                    (itemView.context as Activity).finish()

                }
            }
        }
    }
}