package com.ilhmdhn.simranda.utils

import android.content.Context
import android.net.ConnectivityManager
import android.net.NetworkInfo
import androidx.lifecycle.LiveData
import com.ilhmdhn.simranda.data.source.local.LocalDataSource
import com.ilhmdhn.simranda.data.source.local.entity.RekeningEntity
import java.text.DecimalFormat
import java.text.NumberFormat
import java.util.*

object Utils {

    fun networkCheck(context: Context): Boolean {
        val cm = context.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
        val activeNetwork: NetworkInfo? = cm.activeNetworkInfo
        val isConnected: Boolean = activeNetwork?.isConnectedOrConnecting == true

        return isConnected
    }

    fun generateKodeProgram(program: String?): String {
        return program!![0].toString() + program[1].toString() + program[2].toString()
    }

    fun generateKodeKegiatan(kegiatan: String?): String {
        val hasil: String =
            kegiatan!![3].toString() + kegiatan[4].toString() + kegiatan[5].toString()
        return hasil
    }

    fun generateKodeRekening(rekening: String?): String {
        return "${rekening!![0]}.${rekening[1]}.${rekening[2]}.${rekening[3]}${rekening[4]}.${rekening[5]}${rekening[6]}"
    }

    fun getCurrency(price: Double?): String {
//        val localeID = Locale("in", "ID")
//        val formatRupiah = NumberFormat.getCurrencyInstance(localeID)
//        return formatRupiah.format(price)

        val format = DecimalFormat("#,###,###")

        if (price != null) {
            return "Rp. " + format.format(price).replace(",".toRegex(), ".")
        } else {
            return ""
        }
    }

    fun createKodeKelompok(kodeRek: String?): String {
        return kodeRek!![0].toString() + kodeRek[1]
    }

    fun createKodeJenis(kodeRek: String?): String {
        return kodeRek!![0].toString() + kodeRek[1] + kodeRek[2]
    }

    fun createKodeObjek(kodeRek: String?): String {
        return kodeRek!![0].toString() + kodeRek[1] + kodeRek[2] + kodeRek[3] + kodeRek[4]
    }
}