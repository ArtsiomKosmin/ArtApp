package com.example.data.source.local.entity

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import java.io.Serializable

@Entity(tableName = "Arts")
data class ArtEntityDB(
    @PrimaryKey(autoGenerate = true)
    val id: Int?,

    @ColumnInfo(name = "art_id")
    val artId: String,

    @ColumnInfo(name = "title")
    val title: String,

    @ColumnInfo(name = "long_title")
    val longTitle: String,

    @ColumnInfo(name = "web_image_url")
    val webImageUrl: String,

    @ColumnInfo(name = "is_favorite")
    val isFavorite: Boolean
) : Serializable
