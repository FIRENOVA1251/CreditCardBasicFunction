package com.example.nitracard.data

import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

/**
 * Repository class for handling data operations related to credit cards.
 * This repository provides a clean API for accessing card-related data.
 *
 * @property cardDao The DAO used for performing database operations.
 */
class CardRepository @Inject constructor(private val cardDao: CardDao) {

    /**
     * Retrieves all stored cards as a Flow, allowing real-time updates.
     */
    val allCards: Flow<List<Card>> = cardDao.getAllCards()

    /**
     * Adds a new card to the database.
     *
     * @param card The card entity to insert.
     */
    suspend fun addCard(card: Card) {
        cardDao.insertCard(card)
    }

    /**
     * Deletes a specific card from the database using its ID.
     *
     * @param cardId The ID of the card to delete.
     */
    suspend fun deleteCard(cardId: Int) {
        cardDao.deleteCardById(cardId)
    }
}
