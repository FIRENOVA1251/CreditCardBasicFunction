package com.example.nitracard

import android.content.Context
import androidx.room.Room
import androidx.room.migration.Migration
import androidx.sqlite.db.SupportSQLiteDatabase
import com.example.nitracard.data.CardDao
import com.example.nitracard.data.CardDatabase
import com.example.nitracard.data.CardRepository
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

/**
 * **Dependency Injection Module for Database and Repository**
 *
 * This module provides dependencies for the **Room Database**, **DAO**, and **Repository** using Dagger-Hilt.
 * It ensures that a **single instance** of the database is used across the entire application.
 */
@Module
@InstallIn(SingletonComponent::class) // Installs this module at the application level.
object AppModule {

    /**
     * Provides a **singleton instance** of the Room database.
     *
     * - Uses `Room.databaseBuilder()` to create the database.
     * - Uses `.fallbackToDestructiveMigration()` (⚠️ **Not recommended for production**).
     * - For production, **migrations should be handled safely** using `.addMigrations()`.
     *
     * @param app The application context.
     * @return The **CardDatabase** instance.
     */
    @Provides
    @Singleton  // Ensures that there is only one instance of the database throughout the app.
    fun provideDatabase(@ApplicationContext app: Context): CardDatabase {
        return Room.databaseBuilder(app, CardDatabase::class.java, "card_db")
            // Delete the old data and create a new one.
            .fallbackToDestructiveMigration()

            // ✅ Use safe migrations in production (uncomment below).
            // .addMigrations(MIGRATION_3_4)

            .build()
    }

    /**
     * Provides a **DAO (Data Access Object)** for accessing card-related database operations.
     *
     * @param db The Room database instance.
     * @return The **CardDao** instance.
     */
    @Provides
    fun provideDao(db: CardDatabase): CardDao {
        return db.cardDao()
    }

    /**
     * Provides a **singleton instance** of the Card Repository.
     *
     * - The repository acts as an **abstraction layer** between the ViewModel and the DAO.
     *
     * @param cardDao The **DAO instance** for database operations.
     * @return The **CardRepository** instance.
     */
    @Provides
    @Singleton
    fun provideRepository(cardDao: CardDao): CardRepository {
        return CardRepository(cardDao)
    }
}


// --------------------  Safe Migration (For Future Use) --------------------

/// **Migration Example**: Upgrading the database from version 3 to 4.
/// - This **adds a new column** (`cardNickname`) **without losing user data**.
/// - Instead of `fallbackToDestructiveMigration()`, **safe migrations** should be used in production.
///
///
// val MIGRATION_3_4 = object : Migration(3, 4) {
//    override fun migrate(database: SupportSQLiteDatabase) {
//        // Add a new column `cardNickname` with a default value
//        database.execSQL("ALTER TABLE cards ADD COLUMN cardNickname TEXT DEFAULT '' NOT NULL")
//    }
// }
///