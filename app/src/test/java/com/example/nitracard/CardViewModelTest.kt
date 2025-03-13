package com.example.nitracard

import com.example.nitracard.data.Card
import com.example.nitracard.data.CardRepository
import com.example.nitracard.viewmodel.CardViewModel
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.test.StandardTestDispatcher
import kotlinx.coroutines.test.runTest
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.mockito.Mockito.*

@ExperimentalCoroutinesApi
class CardViewModelTest {

    private lateinit var viewModel: CardViewModel
    private lateinit var repository: CardRepository
    private val testDispatcher = StandardTestDispatcher()

    @get:Rule
    val mainDispatcherRule = MainDispatcherRule(testDispatcher)

    @Before
    fun setUp() {
        repository = mock(CardRepository::class.java)
        `when`(repository.allCards).thenReturn(MutableStateFlow(emptyList()))
        viewModel = CardViewModel(repository)
    }

    @Test
    fun `addCard should add a new card to the list`() = runTest {
        val card = Card(
            cardName = "Test Card",
            cardHolderName = "John Doe",
            cardNumber = "1234567812345678",
            expYear = "2030",
            expMonth = "12",
            cvv = "123"
        )

        viewModel.addCard(card)
        testScheduler.advanceUntilIdle()  // Ensure suspend function completed.

        verify(repository).addCard(card)  // Ensure addCard is functional.
    }

    @Test
    fun `deleteCard should remove a card from the repository`() = runTest {
        val cardId = 1

        viewModel.deleteCard(cardId)
        testScheduler.advanceUntilIdle()  // Ensure suspend function completed.

        verify(repository).deleteCard(cardId)  // Ensure deleteCard is functional.
    }

}

