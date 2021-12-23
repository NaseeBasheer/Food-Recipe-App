package com.example.receipeapplication.data

import com.example.receipeapplication.data.database.RecipesDao
import com.example.receipeapplication.data.database.entities.FavoritesEntity
import com.example.receipeapplication.data.database.entities.FoodJokeEntity
import com.example.receipeapplication.data.database.entities.RecipesEntity
import com.example.receipeapplication.models.FoodJoke
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class LocalDataSource @Inject constructor(
    private val recipesDao: RecipesDao
) {
    fun readRecipes(): Flow<List<RecipesEntity>> {
        return recipesDao.readRecipes()

    }

    fun readFavoriteRecipes():Flow<List<FavoritesEntity>>{
        return recipesDao.readFavoriteRecipes()
    }

    fun readFoodJoke():Flow<List<FoodJoke>>{
        return recipesDao.readFoodJoke()
    }

    suspend fun insertRecipes(recipesEntity: RecipesEntity){
        recipesDao.insertRecipes(recipesEntity)

    }

    suspend fun insertFavoriteRecipes(favoritesEntity: FavoritesEntity) {
        recipesDao.insertFavoriteRecipe(favoritesEntity)
    }

    suspend fun deleteFavoriteRecipe(favoritesEntity: FavoritesEntity){
        recipesDao.deleteFavoriteRecipe(favoritesEntity)
    }
    suspend fun insertFoodJoke(foodJokeEntity: FoodJokeEntity){
        recipesDao.insertFoodJoke(foodJokeEntity)
    }

    suspend fun deleteAllFavoriteRecipes(){
        recipesDao.deleteAllFavoriteRecipes()
    }

}