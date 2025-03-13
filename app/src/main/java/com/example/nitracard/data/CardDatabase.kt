package com.example.nitracard.data


import androidx.room.Database
import androidx.room.RoomDatabase

/**
 * Room database instance for storing credit card information.
 *
 * @property cardDao Provides access to the DAO functions.
 */
@Database(entities = [Card::class], version = 3, exportSchema = false)
abstract class CardDatabase : RoomDatabase() {
    abstract fun cardDao(): CardDao
}
