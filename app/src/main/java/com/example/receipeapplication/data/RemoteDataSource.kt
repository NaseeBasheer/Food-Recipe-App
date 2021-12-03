package com.example.receipeapplication.data

import com.example.receipeapplication.data.network.FoodRecipesApi
import com.example.receipeapplication.models.FoodRecipe
import retrofit2.Response
import javax.inject.Inject


//RemoteDataSource fetch data from remote food recipe API(spoonacular API)

class RemoteDataSource @Inject constructor(
    private val foodRecipesApi: FoodRecipesApi //injecting API
) {
    suspend fun getRecipes(queries: Map<String, String>): Response<FoodRecipe>{
        return foodRecipesApi.getRecipes(queries)

    }

}