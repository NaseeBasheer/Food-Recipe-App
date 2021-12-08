package com.example.receipeapplication.viewmodels

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import com.example.receipeapplication.util.Constants.Companion.API_KEY
import com.example.receipeapplication.util.Constants.Companion.DEFAULT_DIET_TYPE
import com.example.receipeapplication.util.Constants.Companion.DEFAULT_MEAL_TYPE
import com.example.receipeapplication.util.Constants.Companion.DEFAULT_RECIPES_NUMBER
import com.example.receipeapplication.util.Constants.Companion.QUERY_ADD_RECIPE_INFO
import com.example.receipeapplication.util.Constants.Companion.QUERY_API_KEY
import com.example.receipeapplication.util.Constants.Companion.QUERY_DIET
import com.example.receipeapplication.util.Constants.Companion.QUERY_FILL_INGREDIENTS
import com.example.receipeapplication.util.Constants.Companion.QUERY_NUMBER
import com.example.receipeapplication.util.Constants.Companion.QUERY_TYPE

class RecipesViewModel(application: Application): AndroidViewModel(application) {

     fun applyQueries(): HashMap<String, String>{
        val queries:HashMap<String, String> =HashMap()

        queries[QUERY_NUMBER]= DEFAULT_RECIPES_NUMBER
        queries[QUERY_API_KEY]= API_KEY
        queries[QUERY_TYPE] = DEFAULT_MEAL_TYPE
        queries[QUERY_DIET]= DEFAULT_DIET_TYPE
        queries[QUERY_ADD_RECIPE_INFO]= "true"
        queries[QUERY_FILL_INGREDIENTS] = "true"

        return queries
    }

}