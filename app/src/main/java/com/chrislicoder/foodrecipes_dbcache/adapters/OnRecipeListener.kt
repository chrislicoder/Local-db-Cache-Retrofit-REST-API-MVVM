package com.chrislicoder.foodrecipes_dbcache.adapters

interface OnRecipeListener {
    fun onRecipeClick(position: Int)
    fun onCategoryClick(category: String?)
}
