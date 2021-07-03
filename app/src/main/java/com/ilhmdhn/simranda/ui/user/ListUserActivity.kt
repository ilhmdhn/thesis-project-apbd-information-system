package com.ilhmdhn.simranda.ui.user

import android.content.Context
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.ilhmdhn.simranda.R
import com.ilhmdhn.simranda.databinding.ActivityListUserBinding
import com.ilhmdhn.simranda.viewmodel.ViewModelFactory
import com.ilhmdhn.simranda.vo.Status

class ListUserActivity : AppCompatActivity() {

    private var _activityListUserBinding: ActivityListUserBinding? = null
    private val binding get() = _activityListUserBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        _activityListUserBinding = ActivityListUserBinding.inflate(layoutInflater)
        setContentView(binding?.root)

        supportActionBar?.title = "List User"

        val factory = ViewModelFactory.getInstance(this)
        val viewModel = ViewModelProvider(this, factory)[UserViewModel::class.java]

        val sharedPref =
            this.getSharedPreferences(R.string.setting_data.toString(), Context.MODE_PRIVATE)
        val key: String = sharedPref?.getString("key", getString(R.string.default_key)).toString()

        val userAdapter = UserAdapter()
        viewModel.getUser(key, applicationContext)

        binding?.fabInsert?.setOnClickListener {
            startActivity(Intent(this, InsertUserActivity::class.java))
        }

        binding?.swipe?.setOnRefreshListener {
            viewModel.getUser(key, applicationContext)
            binding?.swipe?.isRefreshing = false
        }

        viewModel.resultUser.observe(this, { user ->
            if (user != null) {
                userAdapter.setUser(user)
                userAdapter.notifyDataSetChanged()
            }
        })

        with(binding?.rvUser) {
            this?.layoutManager = LinearLayoutManager(this?.context)
            this?.setHasFixedSize(true)
            this?.adapter = userAdapter
        }
    }
}