package com.example.nitracard

import com.example.nitracard.data.Card
import com.example.nitracard.data.CardDao
import com.example.nitracard.data.CardRepository
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.runTest
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import org.mockito.Mock
import org.mockito.Mockito.verify
import org.mockito.junit.MockitoJUnitRunner

@ExperimentalCoroutinesApi
@RunWith(MockitoJUnitRunner::class)
class CardRepositoryTest {

    @Mock
    private lateinit var cardDao: CardDao

    private lateinit var repository: CardRepository

    @Before
    fun setup() {
        repository = CardRepository(cardDao)
    }

    @Test
    fun `addCard should call insertCard on Dao`() = runTest {
        val card = Card(cardName = "Visa", cardHolderName = "John Doe", cardNumber = "1234567812345678", expMonth = "12", expYear = "2030", cvv = "123")

        repository.addCard(card)

        verify(cardDao).insertCard(card) // Ensure DAO and insertCard is called.
    }

    @Test
    fun `deleteCard should call deleteCardById on Dao`() = runTest {
        repository.deleteCard(1)

        verify(cardDao).deleteCardById(1) // Ensure deleteCardById is completed.
    }
}
