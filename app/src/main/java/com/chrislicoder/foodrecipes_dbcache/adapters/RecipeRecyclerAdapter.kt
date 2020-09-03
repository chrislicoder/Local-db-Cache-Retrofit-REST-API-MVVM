package com.chrislicoder.foodrecipes_dbcache.adapters

import android.net.Uri
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.request.RequestOptions
import com.chrislicoder.foodrecipes_dbcache.R
import com.chrislicoder.foodrecipes_dbcache.models.Recipe
import com.chrislicoder.foodrecipes_dbcache.util.Constants.DEFAULT_SEARCH_CATEGORIES
import com.chrislicoder.foodrecipes_dbcache.util.Constants.DEFAULT_SEARCH_CATEGORY_IMAGES
import java.util.*

class RecipeRecyclerAdapter(private val mOnRecipeListener: OnRecipeListener) : RecyclerView.Adapter<RecyclerView.ViewHolder>() {
    private lateinit var mRecipes: MutableList<Recipe>

    override fun onCreateViewHolder(viewGroup: ViewGroup, i: Int): RecyclerView.ViewHolder {
        lateinit var view: View
        return when (i) {
            RECIPE_TYPE -> {
                view = LayoutInflater.from(viewGroup.context).inflate(R.layout.layout_recipe_list_item, viewGroup, false)
                RecipeViewHolder(view, mOnRecipeListener)
            }
            LOADING_TYPE -> {
                view = LayoutInflater.from(viewGroup.context).inflate(R.layout.layout_loading_list_item, viewGroup, false)
                LoadingViewHolder(view)
            }
            EXHAUSTED_TYPE -> {
                view = LayoutInflater.from(viewGroup.context).inflate(R.layout.layout_search_exhausted, viewGroup, false)
                SearchExhaustedViewHolder(view)
            }
            CATEGORY_TYPE -> {
                view = LayoutInflater.from(viewGroup.context).inflate(R.layout.layout_category_list_item, viewGroup, false)
                CategoryViewHolder(view, mOnRecipeListener)
            }
            else -> {
                view = LayoutInflater.from(viewGroup.context).inflate(R.layout.layout_recipe_list_item, viewGroup, false)
                RecipeViewHolder(view, mOnRecipeListener)
            }
        }
    }

    override fun onBindViewHolder(viewHolder: RecyclerView.ViewHolder, i: Int) {
        val requestOptions = RequestOptions()
            .placeholder(R.drawable.ic_launcher_background)
        when (getItemViewType(i)) {
            RECIPE_TYPE -> {
                with(viewHolder as RecipeViewHolder) {
                    Glide.with(itemView.context)
                        .setDefaultRequestOptions(requestOptions)
                        .load(mRecipes[i].image_url)
                        .into(image)
                    title.text = mRecipes[i].title
                    publisher.text = mRecipes[i].publisher
                    socialScore.text = mRecipes[i].social_rank.toString()
                }
            }
            CATEGORY_TYPE -> {
                val path = Uri.parse("android.resource://com.chrislicoder.foodrecipes/drawable/${mRecipes[i].image_url}")
                with((viewHolder as CategoryViewHolder)) {
                    Glide.with(viewHolder.itemView.context)
                        .setDefaultRequestOptions(requestOptions)
                        .load(path)
                        .into(categoryImage)
                    categoryTitle.text = mRecipes[i].title
                }
            }
        }
    }

    override fun getItemViewType(position: Int): Int {
        return when {
            mRecipes[position].social_rank == -1f -> {
                CATEGORY_TYPE
            }
            mRecipes[position].title == LOADING_TITLE -> {
                LOADING_TYPE
            }
            mRecipes[position].title == "EXHAUSTED..." -> {
                EXHAUSTED_TYPE
            }
            position == mRecipes.size - 1 && position != 0 && mRecipes[position].title != "EXHAUSTED..." -> {
                LOADING_TYPE
            }
            else -> {
                RECIPE_TYPE
            }
        }
    }

    fun setQueryExhausted() {
        hideLoading()
        val exhaustedRecipe = Recipe()
        exhaustedRecipe.title = "EXHAUSTED..."
        mRecipes.add(exhaustedRecipe)
        notifyDataSetChanged()
    }

    private fun hideLoading() {
        if (isLoading) {
            for (recipe in mRecipes) {
                if (recipe.title == LOADING_TITLE) {
                    mRecipes.remove(recipe)
                }
            }
            notifyDataSetChanged()
        }
    }

    fun displayLoading() {
        if (!isLoading) {
            val recipe = Recipe()
            recipe.title = LOADING_TITLE
            val loadingList: MutableList<Recipe> = ArrayList()
            loadingList.add(recipe)
            mRecipes = loadingList
            notifyDataSetChanged()
        }
    }

    private val isLoading: Boolean
        get() {
            if (mRecipes.size > 0) {
                if (mRecipes[mRecipes.size - 1].title == LOADING_TITLE) {
                    return true
                }
            }
            return false
        }

    fun displaySearchCategories() {
        val categories = mutableListOf<Recipe>()
        for ((index, cateboryString) in DEFAULT_SEARCH_CATEGORIES.withIndex()) {
            val mockRecipe = Recipe(
                title = cateboryString,
                image_url = DEFAULT_SEARCH_CATEGORY_IMAGES[index],
                social_rank = -1F
            )
            categories.add(mockRecipe)
        }
        mRecipes = categories
        notifyDataSetChanged()
    }

    override fun getItemCount(): Int {
        return if (!this::mRecipes.isInitialized) 0 else mRecipes.size
    }

    fun setRecipes(recipes: MutableList<Recipe>) {
        mRecipes = recipes
        notifyDataSetChanged()
    }

    fun getSelectedRecipe(position: Int): Recipe? {
        return if (mRecipes.size > 0) mRecipes[position] else null
    }

    companion object {
        private const val RECIPE_TYPE = 1
        private const val LOADING_TYPE = 2
        private const val CATEGORY_TYPE = 3
        private const val EXHAUSTED_TYPE = 4
        private const val LOADING_TITLE = "Loading..."
    }
}
