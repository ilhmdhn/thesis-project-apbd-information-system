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
import com.ilhmdhn.simranda.databinding.ActivitySelectRekeningBinding
import com.ilhmdhn.simranda.ui.master.organisasi.OrganisasiViewModel
import com.ilhmdhn.simranda.ui.master.rekening.RekeningViewModel
import com.ilhmdhn.simranda.utils.Utils
import com.ilhmdhn.simranda.viewmodel.ViewModelFactory
import com.ilhmdhn.simranda.vo.Status

class SelectRekeningActivity : AppCompatActivity() {

    private var _activitySelectRekeningBinding: ActivitySelectRekeningBinding? = null
    private val binding get() = _activitySelectRekeningBinding
    val selectRekeningAdapter = SelectRekeningAdapter()
    private lateinit var viewModel: AnggaranViewModel
    var year: String = ""
    var key: String = ""

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        _activitySelectRekeningBinding = ActivitySelectRekeningBinding.inflate(layoutInflater)
        setContentView(binding?.root)
        supportActionBar?.title = "Pilih rekening"

        val factory = ViewModelFactory.getInstance(this)
        viewModel = ViewModelProvider(this, factory)[AnggaranViewModel::class.java]

        val sharedPref = applicationContext.getSharedPreferences(R.string.setting_data.toString(),Context.MODE_PRIVATE)
        year = sharedPref?.getString("year", getString(R.string.default_year)).toString()
        key = sharedPref?.getString("key", "").toString()

        getAllData()

        binding?.swiped?.setOnRefreshListener {
            getAllData()
            binding?.swiped?.isRefreshing = false
        }
    }

    private fun getAllData() {
        viewModel.getSelectRekening(key, year).observe(this, { rekening ->
            if (rekening != null) {
                when (rekening.status) {
                    Status.LOADING -> binding?.progressBar?.visibility = View.VISIBLE
                    Status.SUCCES -> {
                        binding?.progressBar?.visibility = View.GONE
                        selectRekeningAdapter.submitList(rekening.data)
                    }
                    Status.ERROR -> {
                        binding?.progressBar?.visibility = View.GONE
                        Toast.makeText(this, "Terjadi Kesalahan", Toast.LENGTH_SHORT).show()
                    }
                }
            }
            with(binding?.rvRekening) {
                this?.layoutManager = LinearLayoutManager(this?.context)
                this?.setHasFixedSize(true)
                this?.adapter = selectRekeningAdapter
            }
        })
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.menu_master, menu)

        val searchManager = getSystemService(Context.SEARCH_SERVICE) as SearchManager
        val searchView = menu?.findItem(R.id.search)?.actionView as SearchView

        searchView.setSearchableInfo(searchManager.getSearchableInfo(componentName))
        searchView.queryHint = resources.getString(R.string.search_hint)
        searchView.setOnQueryTextListener(object : SearchView.OnQueryTextListener {
            override fun onQueryTextSubmit(query: String?): Boolean {
                return false
            }

            override fun onQueryTextChange(newText: String?): Boolean {
                viewModel.getSelectSearchRekening(newText)
                    .observe(this@SelectRekeningActivity, { rekening ->
                        if (rekening != null) {
                            DataRepository.isConnected = false
                            selectRekeningAdapter.submitList(rekening)
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