package com.example.receipeapplication.models

import com.google.gson.annotations.SerializedName

data class FoodRecipe(
    @SerializedName("results")
    val results: List<Result>
)