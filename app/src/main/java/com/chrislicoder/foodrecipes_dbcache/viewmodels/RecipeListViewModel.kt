package com.chrislicoder.foodrecipes_dbcache.viewmodels

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.MutableLiveData

class RecipeListViewModel(application: Application) : AndroidViewModel(application) {
    private var _viewState: MutableLiveData<ViewState> = MutableLiveData()

    val viewState
        get() = _viewState

    init {
        _viewState.value = ViewState.CATEGORIES
    }

    enum class ViewState {
        CATEGORIES, RECIPES
    }

    companion object {
        private const val TAG = "RecipeListViewModel"
    }
}
