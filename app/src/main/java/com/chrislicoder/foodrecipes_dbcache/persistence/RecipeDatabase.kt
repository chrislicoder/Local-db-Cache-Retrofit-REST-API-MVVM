package com.chrislicoder.foodrecipes_dbcache.persistence

import android.content.Context
import androidx.room.Room
import androidx.room.RoomDatabase

abstract class RecipeDatabase : RoomDatabase() {

    private lateinit var instance: RecipeDatabase
    fun getInstance(context: Context): RecipeDatabase =
        if (this::instance.isInitialized) instance else {
            instance = Room.databaseBuilder(
                context.applicationContext,
                RecipeDatabase::class.java,
                DATABASE_NAME
            ).build()
            instance
        }

    companion object {
        const val DATABASE_NAME = "recipes_db"
    }
}
