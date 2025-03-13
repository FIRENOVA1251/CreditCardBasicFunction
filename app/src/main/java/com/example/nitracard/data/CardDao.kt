package com.example.nitracard.data

import androidx.room.*
import kotlinx.coroutines.flow.Flow

/**
 * Data Access Object (DAO) for managing card-related database operations.
 */
@Dao
interface CardDao {

    /**
     * Inserts a new card into the database.
     * If a card with the same ID already exists, it will be replaced.
     *
     * @param card The card entity to insert.
     */
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertCard(card: Card)

    /**
     * Retrieves all stored cards as a Flow, allowing real-time updates when changes occur.
     *
     * @return A Flow containing the list of all cards.
     */
    @Query("SELECT * FROM cards")
    fun getAllCards(): Flow<List<Card>>

    /**
     * Fetches a single card based on its unique ID.
     *
     * @param cardId The ID of the card to retrieve.
     * @return The card entity if found, otherwise null.
     */
    @Query("SELECT * FROM cards WHERE id = :cardId")
    suspend fun getCardById(cardId: Int): Card?

    /**
     * Deletes a card from the database based on its unique ID.
     *
     * @param cardId The ID of the card to delete.
     */
    @Query("DELETE FROM cards WHERE id = :cardId")
    suspend fun deleteCardById(cardId: Int)
}
