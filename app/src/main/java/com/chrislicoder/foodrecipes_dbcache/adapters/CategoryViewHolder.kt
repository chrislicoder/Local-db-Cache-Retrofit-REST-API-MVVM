package com.chrislicoder.foodrecipes_dbcache.adapters

import android.view.View
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.chrislicoder.foodrecipes_dbcache.R
import de.hdodenhof.circleimageview.CircleImageView

class CategoryViewHolder(itemView: View, var listener: OnRecipeListener) : RecyclerView.ViewHolder(itemView), View.OnClickListener {
    val categoryImage: CircleImageView = itemView.findViewById(R.id.category_image)
    val categoryTitle: TextView = itemView.findViewById(R.id.category_title)
    override fun onClick(v: View) {
        listener.onCategoryClick(categoryTitle.text.toString())
    }

    init {
        itemView.setOnClickListener(this)
    }
}
