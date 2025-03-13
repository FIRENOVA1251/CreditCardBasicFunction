package com.example.nitracard

/**
 * Defines application navigation routes.
 *
 * This sealed class represents different screens and their navigation paths.
 * - **`CardList`**: Displays all saved cards.
 * - **`AddCard`**: Allows users to add a new card.
 * - **`CardDetail`**: Shows the details of a specific card using `cardId` as a parameter.
 *
 * @property route The navigation route string.
 */
sealed class AppRoutes(val route: String) {

    /** Represents the Card List screen route. */
    data object CardList : AppRoutes("card_list")

    /** Represents the Add Card screen route. */
    data object AddCard : AppRoutes("add_card")

    /**
     * Represents the Card Detail screen route.
     *
     * @property createRoute Generates the full route string with the given `cardId`.
     */
    data object CardDetail : AppRoutes("card_detail/{cardId}") {
        /**
         * Helper function to generate the navigation route for a specific card ID.
         * @param cardId The unique identifier of the card.
         * @return The formatted route string.
         */
        fun createRoute(cardId: Int) = "card_detail/$cardId"
    }
}