package com.ilhmdhn.simranda.ui.anggaran

import android.app.SearchManager
import android.content.Context
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.Menu
import android.view.View
import android.widget.Toast
import androidx.appcompat.widget.SearchView
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.ilhmdhn.simranda.R
import com.ilhmdhn.simranda.data.DataRepository
import com.ilhmdhn.simranda.databinding.ActivitySelectKegiatanBinding
import com.ilhmdhn.simranda.utils.Utils
import com.ilhmdhn.simranda.viewmodel.ViewModelFactory
import com.ilhmdhn.simranda.vo.Status

class SelectKegiatanActivity : AppCompatActivity() {

    private var _activitySelectRekeningBinding: ActivitySelectKegiatanBinding? = null
    private val binding get() = _activitySelectRekeningBinding
    val selectKegiatanAdapter = SelectKegiatanAdapter()

    companion object {
        var kodeOrg: String? = null
    }


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        _activitySelectRekeningBinding = ActivitySelectKegiatanBinding.inflate(layoutInflater)
        setContentView(binding?.root)

        supportActionBar?.title = "Pilih Program dan Kegiatan"

        DataRepository.isConnected = Utils.networkCheck(applicationContext)
        getAllData()

        binding?.swiped?.setOnRefreshListener {
            getAllData()
            binding?.swiped?.isRefreshing = false
        }
    }

    private fun getAllData() {
        val sharedPref = applicationContext.getSharedPreferences(
            R.string.setting_data.toString(),
            Context.MODE_PRIVATE
        )
        val factory = ViewModelFactory.getInstance(this)
        val viewModel = ViewModelProvider(this, factory)[AnggaranViewModel::class.java]

        val year = sharedPref?.getString("year", getString(R.string.default_year)).toString()
        val key = sharedPref?.getString("key", "").toString()

        viewModel.getSelectKegiatan(key, year, kodeOrg).observe(this, { kegiatan ->
            if (kegiatan != null) {
                when (kegiatan.status) {
                    Status.LOADING -> binding?.progressBar?.visibility = View.VISIBLE
                    Status.SUCCES -> {
                        binding?.progressBar?.visibility = View.GONE
                        selectKegiatanAdapter.submitList(kegiatan.data)
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
        val viewModel = ViewModelProvider(this, factory)[AnggaranViewModel::class.java]

        val searchManager = getSystemService(Context.SEARCH_SERVICE) as SearchManager
        val searchView = menu?.findItem(R.id.search)?.actionView as SearchView

        searchView.setSearchableInfo(searchManager.getSearchableInfo(componentName))
        searchView.queryHint = resources.getString(R.string.search_hint)

        searchView.setOnQueryTextListener(object : SearchView.OnQueryTextListener {
            override fun onQueryTextSubmit(query: String?): Boolean {
                return false
            }

            override fun onQueryTextChange(newText: String?): Boolean {
                viewModel.getSelectSearchKegiatan(kodeOrg!!, newText)
                    .observe(this@SelectKegiatanActivity, { kegiatan ->
                        if (kegiatan != null) {
                            DataRepository.isConnected = false
                            selectKegiatanAdapter.submitList(kegiatan)
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
        with(binding?.rvKegiatan) {
            this?.layoutManager = LinearLayoutManager(this?.context)
            this?.setHasFixedSize(true)
            this?.adapter = selectKegiatanAdapter
        }
    }
}