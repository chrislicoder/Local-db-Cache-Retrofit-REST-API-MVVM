package com.codingwithmitch.foodrecipes_dbcache.ui

import android.os.Bundle
import android.view.View
import android.widget.LinearLayout
import android.widget.ScrollView
import android.widget.TextView
import androidx.appcompat.widget.AppCompatImageView
import androidx.lifecycle.ViewModelProvider
import com.codingwithmitch.foodrecipes_dbcache.R
import com.codingwithmitch.foodrecipes_dbcache.models.Recipe
import com.codingwithmitch.foodrecipes_dbcache.viewmodels.RecipeViewModel

class RecipeActivity : BaseActivity() {
    // UI components
    private lateinit var mRecipeImage: AppCompatImageView
    private lateinit var mRecipeTitle: TextView
    private lateinit var mRecipeRank: TextView
    private lateinit var mRecipeIngrdeintsContainer: LinearLayout
    private lateinit var mScrollView: ScrollView
    private lateinit var mRecipeViewModel: RecipeViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_recipe)
        mRecipeImage = findViewById(R.id.recipe_image)
        mRecipeTitle = findViewById(R.id.recipe_title)
        mRecipeRank = findViewById(R.id.recipe_social_score)
        mRecipeIngrdeintsContainer = findViewById(R.id.ingredients_container)
        mScrollView = findViewById(R.id.parent)
        mRecipeViewModel = ViewModelProvider(this).get(RecipeViewModel::class.java)
        getIncomingIntent()
    }

    private fun getIncomingIntent() {
        if (intent.hasExtra(RECIPE_INTENT)) {
            val recipe = intent.getParcelableExtra<Recipe>(
                    RECIPE_INTENT
            )
        }
    }

    private fun showParent() {
        mScrollView.visibility = View.VISIBLE
    }

    companion object {
        const val RECIPE_INTENT = "recipe"
    }
}