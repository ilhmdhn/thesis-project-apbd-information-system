package com.ilhmdhn.simranda.ui.setting

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import com.ilhmdhn.simranda.R
import com.ilhmdhn.simranda.databinding.FragmentSettingBinding

class SettingFragment : Fragment() {

    private var _fragmentSettingBinding: FragmentSettingBinding? = null
    private val binding get() = _fragmentSettingBinding

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _fragmentSettingBinding = FragmentSettingBinding.inflate(layoutInflater, container, false)
        return binding?.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val context = activity
        val sharedPref =
            context?.getSharedPreferences(R.string.setting_data.toString(), Context.MODE_PRIVATE)

        val editor = sharedPref?.edit()

        binding?.edtYear?.setText(sharedPref?.getString("year", getString(R.string.default_year)))

        binding?.btnSubmit?.setOnClickListener {
            editor?.putString("year", binding?.edtYear?.text.toString())
            editor?.apply()
            Toast.makeText(activity, "OK", Toast.LENGTH_SHORT).show()
        }
    }
}