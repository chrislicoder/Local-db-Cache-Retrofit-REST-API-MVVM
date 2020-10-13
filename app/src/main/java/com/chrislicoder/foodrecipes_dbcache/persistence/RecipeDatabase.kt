package com.chrislicoder.foodrecipes_dbcache.persistence

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import com.chrislicoder.foodrecipes_dbcache.models.Recipe

@Database(entities = [Recipe::class], version = 1)
@TypeConverters(Converters::class)
abstract class RecipeDatabase : RoomDatabase() {

    companion object {
        const val DATABASE_NAME = "recipes_db"
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
    }
}
