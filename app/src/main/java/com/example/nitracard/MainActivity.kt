package com.example.nitracard

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.animation.core.tween
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.animation.slideInHorizontally
import androidx.compose.animation.slideOutHorizontally
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.nitracard.ui.AddCardScreen
import com.example.nitracard.ui.CardListScreen
import com.example.nitracard.viewmodel.CardViewModel
import dagger.hilt.android.AndroidEntryPoint
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavType
import androidx.navigation.navArgument
import com.example.nitracard.ui.CardDetailScreen

/**
 * The main entry point of the application.
 *
 * This activity sets up the navigation using Jetpack Compose's `NavHost`.
 * It defines the navigation routes and transitions between different screens.
 *
 * The application follows the **MVVM architecture** and uses **Hilt** for dependency injection.
 *
 * @constructor Creates an instance of `MainActivity`.
 */
@AndroidEntryPoint
class MainActivity : ComponentActivity() {

    /**
     * Called when the activity is first created.
     *
     * - Initializes the **Jetpack Compose UI**.
     * - Sets up **NavHost** for managing screen navigation.
     * - Uses **HiltViewModel** to inject dependencies into screens.
     *
     * @param savedInstanceState The saved instance state bundle.
     */
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            val navController = rememberNavController()
            NavHost(
                navController = navController,
                startDestination = AppRoutes.CardList.route,
                enterTransition = { fadeIn(animationSpec = tween(500)) },
                exitTransition = { fadeOut(animationSpec = tween(500)) }
            ) {

                /**
                 * **Card List Screen**
                 * - Displays a list of saved credit/debit cards.
                 * - Uses `CardViewModel` to fetch and observe the card list.
                 */
                composable(AppRoutes.CardList.route) {
                    val viewModel: CardViewModel = hiltViewModel()
                    CardListScreen(navController, viewModel)
                }

                /**
                 * **Add Card Screen**
                 * - Allows users to add a new card.
                 * - Uses a slide-in transition from the right and slide-out to the right.
                 */
                composable(AppRoutes.AddCard.route,
                    enterTransition = { slideInHorizontally(initialOffsetX = { it }) },
                    exitTransition = { slideOutHorizontally(targetOffsetX = { it }) }
                ) {
                    val viewModel: CardViewModel = hiltViewModel()
                    AddCardScreen(navController, viewModel)
                }

                /**
                 * **Card Detail Screen**
                 * - Displays the details of a selected card.
                 * - Uses `cardId` as a navigation argument to fetch the specific card.
                 * - Uses a slide-in transition from the right and slide-out to the right.
                 */
                composable(AppRoutes.CardDetail.route,
                    arguments = listOf(navArgument("cardId") { type = NavType.IntType }),
                    enterTransition = { slideInHorizontally(initialOffsetX = { it }) },
                    exitTransition = { slideOutHorizontally(targetOffsetX = { it }) }
                ) {
                    val cardId = it.arguments?.getInt("cardId") ?: 0
                    val viewModel: CardViewModel = hiltViewModel()
                    CardDetailScreen(navController, cardId, viewModel)
                }
            }
        }
    }
}
