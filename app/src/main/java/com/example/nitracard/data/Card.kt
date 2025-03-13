package com.example.nitracard.data

import androidx.room.Entity
import androidx.room.PrimaryKey

/**
 * Represents a credit card entity in the local Room database.
 *
 * @property id Unique identifier for the card (auto-generated).
 * @property cardName The name of the card (e.g., "Personal Card").
 * @property cardHolderName The name of the cardholder.
 * @property cardNumber The card number (masked or full storage, depending on security).
 * @property expYear The expiration year of the card (e.g., "2025").
 * @property expMonth The expiration month of the card (01~12).
 * @property cvv The card's security code (CVV).
 */
@Entity(tableName = "cards")
data class Card(
    @PrimaryKey(autoGenerate = true) val id: Int = 0,
    val cardName: String,
    val cardHolderName: String,
    val cardNumber: String,
    val expYear: String, // Year (2010~2035)
    val expMonth: String, // Month (1~12)
    val cvv: String
)

