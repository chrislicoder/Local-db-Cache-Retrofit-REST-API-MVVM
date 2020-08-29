package com.codingwithmitch.foodrecipes_dbcache.util

import android.util.Log
import com.codingwithmitch.foodrecipes_dbcache.models.Recipe

object Testing {
    fun printRecipes(tag: String?, list: List<Recipe>) {
        for (recipe in list) {
            Log.d(
                    tag,
                    "printRecipes: ${recipe.recipe_id}, ${recipe.title}"
            )
        }
    }
}