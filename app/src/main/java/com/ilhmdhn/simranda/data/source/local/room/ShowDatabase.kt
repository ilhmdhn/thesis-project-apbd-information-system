package com.ilhmdhn.simranda.data.source.local.room

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.ilhmdhn.simranda.data.source.local.entity.*

@Database(
    entities = [RekeningEntity::class,
        OrganisasiEntity::class,
        ProgramDanKegiatanEntity::class,
        AnggaranEntity::class,
        AnggaranDokumenEntity::class,
        AnggaranRekeningEntity::class],
    version = 1,
    exportSchema = false
)

abstract class ShowDatabase : RoomDatabase() {
    abstract fun simrandaDao(): SimrandaDao

    companion object {

        @Volatile
        private var INSTANCE: ShowDatabase? = null

        fun getInstance(context: Context): ShowDatabase =
            INSTANCE ?: synchronized(this) {
                Room.databaseBuilder(
                    context.applicationContext,
                    ShowDatabase::class.java,
                    "Simranda.db"
                ).build().apply {
                    INSTANCE = this
                }
            }
    }
}