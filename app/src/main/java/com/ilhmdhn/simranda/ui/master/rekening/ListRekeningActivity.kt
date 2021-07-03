package com.ilhmdhn.simranda.ui.master.rekening

import android.app.SearchManager
import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.os.Handler
import android.view.Menu
import android.view.View
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.SearchView
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.ilhmdhn.simranda.R
import com.ilhmdhn.simranda.data.DataRepository
import com.ilhmdhn.simranda.data.source.remote.StatusResponse
import com.ilhmdhn.simranda.databinding.ActivityListRekeningBinding
import com.ilhmdhn.simranda.utils.Utils.networkCheck
import com.ilhmdhn.simranda.viewmodel.ViewModelFactory
import com.ilhmdhn.simranda.vo.Status


class ListRekeningActivity : AppCompatActivity() {

    private var _activityListRekeningBinding: ActivityListRekeningBinding? = null
    private val binding get() = _activityListRekeningBinding
    val rekeningMasterAdapter = RekeningAdapter()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        _activityListRekeningBinding = ActivityListRekeningBinding.inflate(layoutInflater)
        setContentView(binding?.root)

        binding?.fabInsert?.setOnClickListener {
            startActivity(Intent(this, InputRekeningActivity::class.java))
        }

        supportActionBar?.title = "Master Kode Rekening"

        binding?.swipe?.setOnRefreshListener {
            DataRepository.isConnected = networkCheck(applicationContext)
            getAllData()
            binding?.swipe?.isRefreshing = false
        }
    }

    override fun onResume() {
        super.onResume()
        DataRepository.isConnected = networkCheck(applicationContext)
        getAllData()

//        getAllData()
//        Handler(mainLooper).postDelayed({
//            DataRepository.isConnected = networkCheck(applicationContext)
//            getAllData()
//        }, 3000)
    }

    private fun getAllData() {
        val sharedPref = applicationContext.getSharedPreferences(
            R.string.setting_data.toString(),
            Context.MODE_PRIVATE
        )
        val factory = ViewModelFactory.getInstance(this)
        val viewModel = ViewModelProvider(this, factory)[RekeningViewModel::class.java]

        val year = sharedPref?.getString("year", getString(R.string.default_year)).toString()
        val key = sharedPref?.getString("key", "").toString()

        viewModel.getRekening(key, year).observe(this, { rekening ->
            if (rekening != null) {
                when (rekening.status) {
                    Status.LOADING -> binding?.progressBar?.visibility = View.VISIBLE
                    Status.SUCCES -> {
                        binding?.progressBar?.visibility = View.GONE
                        rekeningMasterAdapter.submitList(rekening.data)
                    }
                    Status.ERROR -> {
                        binding?.progressBar?.visibility = View.GONE
                        Toast.makeText(this, "Terjadi Kesalahan", Toast.LENGTH_SHORT).show()
                    }
                }
            }
        })

        with(binding?.rvRekening) {
            this?.layoutManager = LinearLayoutManager(this?.context)
            this?.setHasFixedSize(true)
            this?.adapter = rekeningMasterAdapter
        }
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.menu_master, menu)

        val factory = ViewModelFactory.getInstance(this)
        val viewModel = ViewModelProvider(this, factory)[RekeningViewModel::class.java]

        val searchManager = getSystemService(Context.SEARCH_SERVICE) as SearchManager
        val searchView = menu?.findItem(R.id.search)?.actionView as SearchView

        searchView.setSearchableInfo(searchManager.getSearchableInfo(componentName))
        searchView.queryHint = resources.getString(R.string.search_hint)
        searchView.setOnQueryTextListener(object : SearchView.OnQueryTextListener {
            override fun onQueryTextSubmit(query: String?): Boolean {
                return false
            }

            override fun onQueryTextChange(newText: String?): Boolean {
                viewModel.getSearchRekening(newText)
                    .observe(this@ListRekeningActivity, { rekening ->
                        if (rekening != null) {
                            DataRepository.isConnected = false
                            rekeningMasterAdapter.submitList(rekening)
                        }

                        if (newText.isNullOrEmpty()) {
                            getAllData()
                        }

                        with(binding?.rvRekening) {
                            this?.layoutManager = LinearLayoutManager(this?.context)
                            this?.setHasFixedSize(true)
                            this?.adapter = rekeningMasterAdapter
                        }
                    })
                return true
            }
        })
        return true
    }

}