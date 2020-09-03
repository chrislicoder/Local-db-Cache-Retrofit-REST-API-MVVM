package com.chrislicoder.foodrecipes_dbcache.ui

import android.content.Intent
import android.os.Bundle
import android.view.View
import androidx.appcompat.widget.SearchView
import androidx.appcompat.widget.Toolbar
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.chrislicoder.foodrecipes_dbcache.R
import com.chrislicoder.foodrecipes_dbcache.adapters.OnRecipeListener
import com.chrislicoder.foodrecipes_dbcache.adapters.RecipeRecyclerAdapter
import com.chrislicoder.foodrecipes_dbcache.util.ui.VerticalSpacingDecorator
import com.chrislicoder.foodrecipes_dbcache.viewmodels.RecipeListViewModel

class RecipeListActivity : BaseActivity(), OnRecipeListener {
    private lateinit var mSearchView: SearchView
    private lateinit var mRecipeListViewModel: RecipeListViewModel
    private lateinit var mRecyclerView: RecyclerView
    private lateinit var mAdapter: RecipeRecyclerAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_recipe_list)
        mRecyclerView = findViewById(R.id.recipe_list)
        mSearchView = findViewById(R.id.search_view)
        mRecipeListViewModel = ViewModelProvider(this).get(RecipeListViewModel::class.java)
        initRecyclerView()
        initSearchView()
        setSupportActionBar(findViewById<View>(R.id.toolbar) as Toolbar)
        subscribeObservers()
    }

    private fun initRecyclerView() {
        mAdapter = RecipeRecyclerAdapter(this)
        val itemDecorator = VerticalSpacingDecorator(30)
        mRecyclerView.addItemDecoration(itemDecorator)
        mRecyclerView.adapter = mAdapter
        mRecyclerView.layoutManager = LinearLayoutManager(this)
    }

    private fun initSearchView() {
        mSearchView.setOnQueryTextListener(object : SearchView.OnQueryTextListener {
            override fun onQueryTextSubmit(s: String): Boolean {
                return false
            }

            override fun onQueryTextChange(s: String): Boolean {
                return false
            }
        })
    }

    private fun subscribeObservers() {
        mRecipeListViewModel.viewState.observe(
            this,
            { viewState ->
                viewState?.let {
                    when (viewState) {
                        RecipeListViewModel.ViewState.RECIPES -> {
                        }
                        RecipeListViewModel.ViewState.CATEGORIES -> {
                            displaySearchCategories()
                        }
                    }
                }
            }
        )
    }

    override fun onRecipeClick(position: Int) {
        val intent = Intent(this, RecipeActivity::class.java)
        intent.putExtra("recipe", mAdapter.getSelectedRecipe(position))
        startActivity(intent)
    }

    override fun onCategoryClick(category: String?) {}

    private fun displaySearchCategories() {
        mAdapter.displaySearchCategories()
    }

    companion object {
        private const val TAG = "RecipeListActivity"
    }
}
