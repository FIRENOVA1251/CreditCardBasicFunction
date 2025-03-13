package com.example.nitracard.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.nitracard.data.Card
import com.example.nitracard.data.CardRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch
import javax.inject.Inject

/**
 * ViewModel for managing credit card data.
 *
 * This ViewModel interacts with the repository to fetch, add, and delete cards.
 * It ensures data is retained across configuration changes and follows best practices
 * by using `stateIn` to collect and manage `Flow` data efficiently.
 *
 * @property repository The repository that handles database operations.
 */
@HiltViewModel
class CardViewModel @Inject constructor(private val repository: CardRepository) : ViewModel() {

    /**
     * StateFlow that holds the list of all stored cards.
     * It automatically collects data from the repository and updates when database changes occur.
     */
    val cards: StateFlow<List<Card>> = repository.allCards
        .stateIn(viewModelScope, SharingStarted.WhileSubscribed(5000), emptyList())

    /**
     * Adds a new card to the database.
     *
     * @param card The card entity to be inserted.
     */
    fun addCard(card: Card) {
        viewModelScope.launch {
            repository.addCard(card)
        }
    }

    /**
     * Deletes a card from the database by its ID.
     *
     * @param cardId The unique identifier of the card to be removed.
     */
    fun deleteCard(cardId: Int) {
        viewModelScope.launch {
            repository.deleteCard(cardId)
        }
    }
}
