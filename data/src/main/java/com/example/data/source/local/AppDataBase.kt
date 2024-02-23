package com.example.data.source.local

import android.content.Context
import androidx.room.AutoMigration
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.example.data.source.local.dao.ArtDao
import com.example.data.source.local.entity.ArtEntityDB

@Database(
    entities = [ArtEntityDB::class],
    version = 1,
    exportSchema = true,
//    autoMigrations = [AutoMigration(from = 1, to = 2)]
)
abstract class AppDataBase : RoomDatabase() {
    abstract fun getDao(): ArtDao

    companion object {
        @Volatile
        private var INSTANCE: AppDataBase? = null

        fun getDataBase(context: Context): AppDataBase {
            return INSTANCE ?: synchronized(this) {
                val instance = Room.databaseBuilder(
                    context.applicationContext,
                    AppDataBase::class.java,
                    "artsLocal.db"
                ).build()
                instance
            }
        }
    }
}