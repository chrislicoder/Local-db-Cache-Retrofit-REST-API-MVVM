package com.chrislicoder.foodrecipes_dbcache.models

import android.os.Parcelable
import androidx.annotation.NonNull
import androidx.room.ColumnInfo
import androidx.room.PrimaryKey
import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName
import kotlinx.android.parcel.Parcelize

@Parcelize
data class Recipe(
    @PrimaryKey
    @NonNull
    @SerializedName("recipe_id")
    @Expose
    val recipe_id: String? = null,

    @ColumnInfo(name = "title")
    @SerializedName("title")
    @Expose
    var title: String? = null,

    @ColumnInfo(name = "publisher")
    @SerializedName("publisher")
    @Expose val publisher: String? = null,

    @ColumnInfo(name = "ingredients")
    @SerializedName("ingredients")
    @Expose
    val ingredients: Array<String>? = null,

    @ColumnInfo(name = "image_url")
    @SerializedName("image_url")
    @Expose
    val image_url: String? = null,

    @ColumnInfo(name = "social_rank")
    @SerializedName("social_rank")
    @Expose
    val social_rank: Float? = null,

    @ColumnInfo(name = "timestamp")
    @SerializedName("timestamp")
    @Expose
    val timestamp: Int? = null
) : Parcelable {
    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (javaClass != other?.javaClass) return false

        other as Recipe

        if (recipe_id != other.recipe_id) return false
        if (title != other.title) return false
        if (publisher != other.publisher) return false
        if (ingredients != null) {
            if (other.ingredients == null) return false
            if (!ingredients.contentEquals(other.ingredients)) return false
        } else if (other.ingredients != null) return false
        if (image_url != other.image_url) return false
        if (social_rank != other.social_rank) return false
        if (timestamp != other.timestamp) return false

        return true
    }

    override fun hashCode(): Int {
        var result = recipe_id?.hashCode() ?: 0
        result = 31 * result + (title?.hashCode() ?: 0)
        result = 31 * result + (publisher?.hashCode() ?: 0)
        result = 31 * result + (ingredients?.contentHashCode() ?: 0)
        result = 31 * result + (image_url?.hashCode() ?: 0)
        result = 31 * result + (social_rank?.hashCode() ?: 0)
        result = 31 * result + (timestamp?.hashCode() ?: 0)
        return result
    }
}
