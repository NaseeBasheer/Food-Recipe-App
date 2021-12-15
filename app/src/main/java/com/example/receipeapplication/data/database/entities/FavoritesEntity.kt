package com.example.receipeapplication.data.database.entities

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.example.receipeapplication.models.Result
import com.example.receipeapplication.util.Constants.Companion.FAVORITE_RECIPES_TABLE


@Entity(tableName = FAVORITE_RECIPES_TABLE)
class FavoritesEntity(
    @PrimaryKey(autoGenerate = true)
    val id: Int,
    val result: Result
)