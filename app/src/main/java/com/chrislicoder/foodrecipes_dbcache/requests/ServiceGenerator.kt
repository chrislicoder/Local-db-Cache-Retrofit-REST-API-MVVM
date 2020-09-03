package com.chrislicoder.foodrecipes_dbcache.requests

import com.chrislicoder.foodrecipes_dbcache.util.Constants.BASE_URL
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

object ServiceGenerator {
    val recipeApi: RecipeApi by lazy {
        Retrofit.Builder()
            .baseUrl(BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
            .create(RecipeApi::class.java)
    }
}
