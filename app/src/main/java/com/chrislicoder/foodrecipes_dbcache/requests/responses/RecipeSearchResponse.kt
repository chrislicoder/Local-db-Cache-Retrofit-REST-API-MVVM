package com.chrislicoder.foodrecipes_dbcache.requests.responses

import com.chrislicoder.foodrecipes_dbcache.models.Recipe
import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName

data class RecipeSearchResponse(
    @SerializedName("count") @Expose val count: Int,
    @SerializedName("recipes") @Expose val recipes: List<Recipe>
) {
    override fun toString(): String {
        return "RecipeSearchResponse{count=$count, recipes=$recipes}"
    }
}
