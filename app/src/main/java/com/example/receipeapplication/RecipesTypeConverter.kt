package com.example.receipeapplication

import androidx.room.TypeConverter
import com.example.receipeapplication.models.FoodRecipe
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken

//using gson library to convert food recipe to string
class RecipesTypeConverter {
    val gson = Gson()

    @TypeConverter
    fun foodRecipeToString(foodRecipe: FoodRecipe):String{

        return gson.toJson(foodRecipe)
    }

    @TypeConverter
    fun stringToFoodRecipe(data:String): FoodRecipe{

        val listType = object: TypeToken<FoodRecipe>(){}.type
        return gson.fromJson(data, listType)
    }
}