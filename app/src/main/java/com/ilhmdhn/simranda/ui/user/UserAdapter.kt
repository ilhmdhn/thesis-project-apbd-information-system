package com.ilhmdhn.simranda.ui.user

import android.annotation.SuppressLint
import android.content.Intent
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.ilhmdhn.simranda.data.source.remote.response.DataUserItem
import com.ilhmdhn.simranda.databinding.ListUserBinding

class UserAdapter : RecyclerView.Adapter<UserAdapter.UserViewHolder>() {

    private var listUser = ArrayList<DataUserItem>()

    fun setUser(user: List<DataUserItem>) {
        this.listUser.clear()
        this.listUser.addAll(user)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): UserViewHolder {
        val listUserBinding =
            ListUserBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return UserViewHolder(listUserBinding)
    }

    override fun onBindViewHolder(holder: UserViewHolder, position: Int) {
        val user = listUser[position]
        holder.bind(user)
    }

    override fun getItemCount(): Int = listUser.size

    class UserViewHolder(private val binding: ListUserBinding) :
        RecyclerView.ViewHolder(binding.root) {
        @SuppressLint("SetTextI18n")
        fun bind(user: DataUserItem) {
            with(binding) {
                if (user.level == "1") {
                    tvLevel.text = "Admin"
                } else if (user.level == "2") {
                    tvLevel.text = "Operator"
                } else {
                    tvLevel.text = "Tamu"
                }
                tvUserId.text = user.username
                tvFullname.text = user.fullname

                itemView.setOnClickListener {
                    val move = Intent(itemView.context, UpdateUserActivity::class.java)
                    move.putExtra(UpdateUserActivity.EXTRA_DATA, user)
                    itemView.context.startActivity(move)
                }
            }
        }
    }
}