package com.netmedsassignment.database.manager

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.netmedsassignment.data.model.ResponseData
import com.netmedsassignment.database.dao.TestApiDao
import com.netmedsassignment.database.entities.ApiItem
import com.netmedsassignment.database.entities.CartItem


/* This Class Is Used to create the instance of RoomDatabase, This database include ApiItem and CartItem table
   It will also return the Dao so that we can call Dao method with it
*/
@Database(entities = [ApiItem::class, CartItem::class], version = 4, exportSchema = false)
public abstract class DatabaseManager : RoomDatabase() {

    companion object {
        @Volatile
        private var INSTANCE: DatabaseManager? = null

        fun getDatabase(context: Context): DatabaseManager {
            val tempInstance = INSTANCE
            if (tempInstance != null) {
                return tempInstance
            }
            synchronized(this) {
                val instance = Room.databaseBuilder(
                    context.applicationContext,
                    DatabaseManager::class.java,
                    "netmeds_database"
                )
                    .fallbackToDestructiveMigration()
                    .build()
                INSTANCE = instance
                return instance
            }
        }
    }

    abstract fun testApiDao(): TestApiDao
}