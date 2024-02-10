package com.example.data.source.local.entity

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import java.io.Serializable

@Entity(tableName = "Arts")
data class ArtEntityDB(
    @PrimaryKey(autoGenerate = true)
    val id: Int?,

    @ColumnInfo(name = "artId")
    val artId: String,

    @ColumnInfo(name = "title")
    val title: String,

    @ColumnInfo(name = "longTitle")
    val longTitle: String,

    @ColumnInfo(name = "webImageUrl")
    val webImageUrl: String,

    @ColumnInfo(name = "headerImageUrl")
    val headerImageUrl: String,

    @ColumnInfo(name = "isFavorite")
    val isFavorite: Boolean
) : Serializable
