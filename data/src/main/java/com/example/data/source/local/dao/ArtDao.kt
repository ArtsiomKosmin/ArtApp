package com.example.data.source.local.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import com.example.data.source.local.entity.ArtEntityDB

@Dao
interface ArtDao {
    @Insert
    suspend fun insertArt(art: ArtEntityDB)
    @Query ("SELECT * FROM arts")
    fun getAllNotes(): List<ArtEntityDB>
}