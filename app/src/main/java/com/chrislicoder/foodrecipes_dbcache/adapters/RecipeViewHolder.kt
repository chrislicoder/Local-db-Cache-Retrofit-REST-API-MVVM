package com.codingwithmitch.foodrecipes_dbcache.adapters

import android.view.View
import android.widget.TextView
import androidx.appcompat.widget.AppCompatImageView
import androidx.recyclerview.widget.RecyclerView
import com.codingwithmitch.foodrecipes_dbcache.R

class RecipeViewHolder(itemView: View, private var onRecipeListener: OnRecipeListener) : RecyclerView.ViewHolder(itemView), View.OnClickListener {

    val title: TextView = itemView.findViewById(R.id.recipe_title)

    val publisher: TextView = itemView.findViewById(R.id.recipe_publisher)

    val socialScore: TextView = itemView.findViewById(R.id.recipe_social_score)

    val image: AppCompatImageView = itemView.findViewById(R.id.recipe_image)
    override fun onClick(v: View) {
        onRecipeListener.onRecipeClick(adapterPosition)
    }

    init {
        itemView.setOnClickListener(this)
    }
}
