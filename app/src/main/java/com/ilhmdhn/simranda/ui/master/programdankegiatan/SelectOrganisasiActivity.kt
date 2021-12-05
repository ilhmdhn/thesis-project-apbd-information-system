package com.ilhmdhn.simranda.ui.master.programdankegiatan

import android.app.SearchManager
import android.content.Context
import android.os.Bundle
import android.view.KeyEvent
import android.view.Menu
import android.view.View
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.ilhmdhn.simranda.R
import com.ilhmdhn.simranda.data.DataRepository
import com.ilhmdhn.simranda.databinding.ActivitySelectOrganisasiBinding
import com.ilhmdhn.simranda.ui.master.organisasi.OrganisasiViewModel
import com.ilhmdhn.simranda.ui.master.rekening.RekeningViewModel
import com.ilhmdhn.simranda.utils.Utils.networkCheck
import com.ilhmdhn.simranda.viewmodel.ViewModelFactory
import com.ilhmdhn.simranda.vo.Status

class SelectOrganisasiActivity : AppCompatActivity() {

    private var _activitySelectOrganisasiBinding: ActivitySelectOrganisasiBinding? = null
    private val binding get() = _activitySelectOrganisasiBinding
    val selectOrganisasiAdapter = SelectOrganisasiAdapter()
    private lateinit var viewModel: ProgramDanKegiatanViewModel
    var year: String = ""
    var key: String = ""

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        _activitySelectOrganisasiBinding = ActivitySelectOrganisasiBinding.inflate(layoutInflater)
        setContentView(binding?.root)
        supportActionBar?.title = "Pilih Organisasi"

        val factory = ViewModelFactory.getInstance(this)
        viewModel = ViewModelProvider(this, factory)[ProgramDanKegiatanViewModel::class.java]

        val sharedPref = applicationContext?.getSharedPreferences(R.string.setting_data.toString(), Context.MODE_PRIVATE)
        year = sharedPref?.getString("year", getString(R.string.default_year)).toString()
        key = sharedPref?.getString("key", "").toString()

        DataRepository.isConnected = networkCheck(applicationContext)
        getAllData()

        binding?.swipe?.setOnRefreshListener {
            getAllData()
            binding?.swipe?.isRefreshing = false
        }
    }

    override fun onKeyDown(keyCode: Int, event: KeyEvent?): Boolean {
        if (keyCode == KeyEvent.KEYCODE_BACK) {
            return super.onKeyDown(keyCode, event)
        }
        return false
    }

    private fun getAllData() {
        viewModel.getSelectOrganisasi(key, year).observe(this, { organisasi ->
            if (organisasi != null) {
                when (organisasi.status) {
                    Status.LOADING -> binding?.progressBar?.visibility = View.VISIBLE
                    Status.SUCCES -> {
                        binding?.progressBar?.visibility = View.GONE
                        selectOrganisasiAdapter.submitList(organisasi.data)
                    }
                    Status.ERROR -> {
                        binding?.progressBar?.visibility = View.GONE
                        Toast.makeText(this, "Terjadi Kesalahan", Toast.LENGTH_SHORT).show()
                    }
                }
            }
            with(binding?.rvSelectOrganisasi) {
                this?.layoutManager = LinearLayoutManager(this?.context)
                this?.setHasFixedSize(true)
                this?.adapter = selectOrganisasiAdapter
            }
        })
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.menu_master, menu)

        val searchManager = getSystemService(Context.SEARCH_SERVICE) as SearchManager
        val searchView =
            menu?.findItem(R.id.search)?.actionView as androidx.appcompat.widget.SearchView

        searchView.setSearchableInfo(searchManager.getSearchableInfo(componentName))
        searchView.queryHint = resources.getString(R.string.search_hint)
        searchView.setOnQueryTextListener(object :
            androidx.appcompat.widget.SearchView.OnQueryTextListener {
            override fun onQueryTextSubmit(query: String?): Boolean {
                return false
            }

            override fun onQueryTextChange(newText: String?): Boolean {
                viewModel.getSelectSearchOrganisasi(newText)
                    .observe(this@SelectOrganisasiActivity, { organisasi ->
                        if (organisasi != null) {
                            selectOrganisasiAdapter.submitList(organisasi)
                        }

                        if (newText.isNullOrEmpty()) {
                            getAllData()
                        }
                    })
                return true
            }
        })
        return true
    }
}