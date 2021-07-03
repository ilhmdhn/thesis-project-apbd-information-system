package com.ilhmdhn.simranda.ui.master.organisasi

import android.app.SearchManager
import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.view.Menu
import android.view.View
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.SearchView
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.ilhmdhn.simranda.R
import com.ilhmdhn.simranda.data.DataRepository
import com.ilhmdhn.simranda.databinding.ActivityListOrganisasiBinding
import com.ilhmdhn.simranda.utils.Utils.networkCheck
import com.ilhmdhn.simranda.viewmodel.ViewModelFactory
import com.ilhmdhn.simranda.vo.Status

class ListOrganisasiActivity : AppCompatActivity() {

    private var _activityListOrganisasiBinding: ActivityListOrganisasiBinding? = null
    private val binding get() = _activityListOrganisasiBinding
    val organisasiMasterAdapter = OrganisasiAdapter()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        _activityListOrganisasiBinding = ActivityListOrganisasiBinding.inflate(layoutInflater)
        setContentView(binding?.root)

        binding?.fab?.setOnClickListener {
            startActivity(Intent(this, InsertOrganisasiActivity::class.java))
        }

        supportActionBar?.title = "Master Data Organisasi"

        binding?.swipe?.setOnRefreshListener {
            DataRepository.isConnected = networkCheck(applicationContext)
            getAllData()
            binding?.swipe?.isRefreshing = false
        }
    }

    override fun onResume() {
        DataRepository.isConnected = networkCheck(applicationContext)
        getAllData()
        super.onResume()
    }

    private fun getAllData() {
        val sharedPref = applicationContext?.getSharedPreferences(
            R.string.setting_data.toString(),
            Context.MODE_PRIVATE
        )
        val factory = ViewModelFactory.getInstance(this)
        val viewModel = ViewModelProvider(this, factory)[OrganisasiViewModel::class.java]

        val year = sharedPref?.getString("year", getString(R.string.default_year)).toString()
        val key = sharedPref?.getString("key", "").toString()

        viewModel.getOrganisasi(key, year).observe(this, { organisasi ->
            if (organisasi != null) {
                when (organisasi.status) {
                    Status.LOADING -> binding?.progressBar?.visibility = View.VISIBLE
                    Status.SUCCES -> {
                        binding?.progressBar?.visibility = View.GONE
                        organisasiMasterAdapter.submitList(organisasi.data)
                    }
                    Status.ERROR -> {
                        binding?.progressBar?.visibility = View.GONE
                        Toast.makeText(this, "Terjadi Kesalahan", Toast.LENGTH_SHORT).show()
                    }
                }
            }
        })
        showRecycler()
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.menu_master, menu)

        val factory = ViewModelFactory.getInstance(this)
        val viewModel = ViewModelProvider(this, factory)[OrganisasiViewModel::class.java]

        val searchManager = getSystemService(Context.SEARCH_SERVICE) as SearchManager
        val searchView = menu?.findItem(R.id.search)?.actionView as SearchView

        searchView.setSearchableInfo(searchManager.getSearchableInfo(componentName))
        searchView.queryHint = resources.getString(R.string.search_hint)
        searchView.setOnQueryTextListener(object : SearchView.OnQueryTextListener {
            override fun onQueryTextSubmit(query: String?): Boolean {
                return false
            }

            override fun onQueryTextChange(newText: String?): Boolean {
                viewModel.getSearchOrganisasi(newText)
                    .observe(this@ListOrganisasiActivity, { organisasi ->
                        if (organisasi != null) {
                            DataRepository.isConnected = false
                            organisasiMasterAdapter.submitList(organisasi)
                        }

                        if (newText.isNullOrEmpty()) {
                            getAllData()
                        }
                        showRecycler()
                    })
                return true
            }
        })
        return true
    }

    private fun showRecycler() {
        with(binding?.rvOrganisasi) {
            this?.layoutManager = LinearLayoutManager(this?.context)
            this?.setHasFixedSize(true)
            this?.adapter = organisasiMasterAdapter
        }
    }
}