package com.ilhmdhn.simranda.ui.anggaran

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
import com.ilhmdhn.simranda.databinding.ActivityListAnggaranBinding
import com.ilhmdhn.simranda.utils.Utils
import com.ilhmdhn.simranda.viewmodel.ViewModelFactory
import com.ilhmdhn.simranda.vo.Status

class ListAnggaranActivity : AppCompatActivity() {

    companion object {
        var EXTRA_KODE_DOK = "extra_kode_dok"
    }

    private var _activityListAnggaranBinding: ActivityListAnggaranBinding? = null
    private val binding get() = _activityListAnggaranBinding
    val anggaranAdapter = AnggaranAdapter()
    private lateinit var viewModel: AnggaranViewModel
    var kodeDok: String = ""
    var title: String = ""
    var key: String = ""
    var year: String = ""

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        _activityListAnggaranBinding = ActivityListAnggaranBinding.inflate(layoutInflater)
        setContentView(binding?.root)

        val sharedPref = applicationContext?.getSharedPreferences(R.string.setting_data.toString(), Context.MODE_PRIVATE)
        val factory = ViewModelFactory.getInstance(this)
        viewModel = ViewModelProvider(this, factory)[AnggaranViewModel::class.java]

        year = sharedPref?.getString("year", getString(R.string.default_year)).toString()
        key = sharedPref?.getString("key", "").toString()

        kodeDok = intent.getStringExtra(EXTRA_KODE_DOK).toString()

        when (kodeDok) {
            "1" -> title = "RKA SKPD"
            "2" -> title = "APBD MURNI"
            "3" -> title = "DPA SKPD"
            "4" -> title = "RKA SKPD PERUBAHAN"
            "5" -> title = "APBD PERUBAHAN"
            "6" -> title = "DPPA SKPD"
            "7" -> title = "APBD REALISASI"
        }

        supportActionBar?.title = title
        binding?.swiped?.setOnRefreshListener {
            DataRepository.isConnected = Utils.networkCheck(applicationContext)
            getAllData()
            binding?.swiped?.isRefreshing = false
        }

        binding?.fabInsert?.setOnClickListener {
            val intent = Intent(this, InputAnggaranActivity::class.java)
            intent.putExtra(InputAnggaranActivity.EXTRA_KODE_DOK, kodeDok)
            startActivity(intent)
        }
    }

    private fun getAllData() {

        viewModel.getAnggaran(key, year, kodeDok).observe(this, { anggaran ->
            if (anggaran != null) {
                when (anggaran.status) {
                    Status.LOADING -> binding?.progressBar?.visibility = View.VISIBLE
                    Status.SUCCES -> {
                        binding?.progressBar?.visibility = View.GONE
                        anggaranAdapter.submitList(anggaran.data)
                    }
                    Status.ERROR -> {
                        binding?.progressBar?.visibility = View.GONE
                        Toast.makeText(this, "Terjadi Kesalahan", Toast.LENGTH_SHORT).show()
                    }
                }
            }
            with(binding?.rvAnggaran) {
                this?.layoutManager = LinearLayoutManager(this?.context)
                this?.setHasFixedSize(true)
                this?.adapter = anggaranAdapter
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
                viewModel.getSearchAnggaran(kodeDok, newText)
                    .observe(this@ListAnggaranActivity, { data ->
                        if (data != null) {
                            DataRepository.isConnected = false
                            anggaranAdapter.submitList(data)
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