package com.ilhmdhn.simranda.ui.home

import android.annotation.SuppressLint
import android.app.Activity
import android.content.Context
import android.content.Intent
import android.content.SharedPreferences
import android.os.Bundle
import android.text.method.TextKeyListener.clear
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.fragment.app.Fragment
import com.ilhmdhn.simranda.R
import com.ilhmdhn.simranda.databinding.FragmentHomeBinding
import com.ilhmdhn.simranda.ui.anggaran.ListAnggaranFragment
import com.ilhmdhn.simranda.ui.laporan.LaporanMenuFragment
import com.ilhmdhn.simranda.ui.login.LoginActivity
import com.ilhmdhn.simranda.ui.master.MasterDataFragment
import com.ilhmdhn.simranda.ui.setting.SettingFragment
import com.ilhmdhn.simranda.ui.user.ListUserActivity

class HomeFragment : Fragment() {

    private var _fragmentHomeBinding: FragmentHomeBinding? = null
    private val binding get() = _fragmentHomeBinding

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _fragmentHomeBinding = FragmentHomeBinding.inflate(layoutInflater, container, false)
        return binding?.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val mFragmentManager = fragmentManager

        val context = activity
        val sharedPref =
            context?.getSharedPreferences(R.string.setting_data.toString(), Context.MODE_PRIVATE)
        binding?.tvUserName?.setText(
            sharedPref?.getString(
                "fullname",
                getString(R.string.default_user)
            )
        )

        binding?.tvLogout?.setOnClickListener {
            showAlertDialog(context!!)
        }


        val userLevel = sharedPref?.getString("level", getString(R.string.default_user))
        if (userLevel == "3") {
            binding?.rlMasterData?.visibility = View.GONE
            binding?.rlInputData?.visibility = View.GONE
        }

        if (userLevel != "1") {
            binding?.rlUserData?.visibility = View.GONE
        }

        binding?.rlMasterData?.setOnClickListener {
            val mMasterDataFragment = MasterDataFragment()
            mFragmentManager?.beginTransaction()?.apply {
                replace(
                    R.id.frame_container,
                    mMasterDataFragment,
                    MasterDataFragment::class.java.simpleName
                )
                addToBackStack(null)
                commit()
            }
        }
        binding?.rlPengaturan?.setOnClickListener {
            val mSettingFragment = SettingFragment()
            mFragmentManager?.beginTransaction()?.apply {
                replace(
                    R.id.frame_container,
                    mSettingFragment,
                    SettingFragment::class.java.simpleName
                )
                addToBackStack(null)
                commit()
            }
        }

        binding?.rlInputData?.setOnClickListener {
            val mListAnggaranFragment = ListAnggaranFragment()
            mFragmentManager?.beginTransaction()?.apply {
                replace(
                    R.id.frame_container,
                    mListAnggaranFragment,
                    ListAnggaranFragment::class.java.simpleName
                )
                addToBackStack(null)
                commit()
            }
        }

        binding?.rlLaporan?.setOnClickListener {
            val mMenuLaporanFragment = LaporanMenuFragment()
            mFragmentManager?.beginTransaction()?.apply {
                replace(
                    R.id.frame_container,
                    mMenuLaporanFragment,
                    LaporanMenuFragment::class.java.simpleName
                )
                addToBackStack(null)
                commit()
            }
        }

        binding?.rlUserData?.setOnClickListener {
            startActivity(Intent(activity, ListUserActivity::class.java))
        }
    }

    @SuppressLint("CommitPrefEdits")
    private fun showAlertDialog(context: Context) {
        val sharedPref =
            context.getSharedPreferences(R.string.setting_data.toString(), Context.MODE_PRIVATE)
        val alertDialogBuilder = AlertDialog.Builder(context)
        with(alertDialogBuilder) {
            setTitle("Logout?")
            setMessage("Apakah Anda Ingin Logout")
            setCancelable(true)
            setPositiveButton(("Ya")) { _, _ ->
                startActivity(Intent(context, LoginActivity::class.java))
                sharedPref.edit().clear().apply()
                (context as Activity).finish()
            }
            setNegativeButton("Batal") { dialog, _ -> dialog.cancel() }
        }
        val alertDialog = alertDialogBuilder.create()
        alertDialog.show()
    }
}