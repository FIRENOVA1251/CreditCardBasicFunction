package com.example.nitracard.ui

import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.animation.core.tween
import androidx.compose.foundation.Image
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.example.nitracard.AppRoutes
import com.example.nitracard.R
import com.example.nitracard.data.Card
import com.example.nitracard.ui.theme.AddCardBtnColor
import com.example.nitracard.ui.theme.BorderColor
import com.example.nitracard.ui.theme.CardBackgroundColor
import com.example.nitracard.ui.theme.CardTextColor
import com.example.nitracard.viewmodel.CardViewModel

/**
 * Displays the list of saved credit/debit cards.
 *
 * This screen includes:
 * - A **top bar** with the title.
 * - A **floating action button (FAB)** to add a new card.
 * - A **LazyColumn** that dynamically lists all cards.
 * - If no cards exist, an **empty state UI** is shown.
 *
 * @param navController Used for navigation between screens.
 * @param viewModel Provides the list of cards using state management.
 */
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun CardListScreen(navController: NavController, viewModel: CardViewModel) {
    val cards by viewModel.cards.collectAsState()

    Scaffold(
        topBar = {
            TopAppBar(title = {
                Text(
                    stringResource(R.string.card_list_title),
                    fontWeight = FontWeight.Bold
                )
            })
        },
        floatingActionButton = {
            if (cards.isNotEmpty()) {
                FloatingActionButton(
                    onClick = { navController.navigate(AppRoutes.AddCard.route) },
                    containerColor = AddCardBtnColor,
                    modifier = Modifier
                        .width(130.dp)
                        .height(45.dp)
                ) {
                    Text(
                        stringResource(id = R.string.card_list_add_card_btn),
                        color = CardTextColor,
                        fontWeight = FontWeight.Bold
                    )
                }
            }

        }
    ) { paddingValues ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(paddingValues)
                .padding(16.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {

            if (cards.isEmpty()) {
                EmptyState(navController)

            } else {
                LazyColumn(
                    modifier = Modifier.fillMaxSize(),
                ) {
                    itemsIndexed(cards) { index, card ->
                        // Set an initial offset to ensure the card slides in from outside the screen.
                        var animatedOffset by remember { mutableFloatStateOf(500f) }
                        val offset by animateFloatAsState(
                            targetValue = animatedOffset,
                            animationSpec = tween(durationMillis = 500), label = ""
                        )

                        // Launch the animation while start.
                        LaunchedEffect(Unit) {
                            // Slide to the target position
                            animatedOffset =
                                if (index == 0) 0f else (-275f * index)
                        }

                        CardItem(
                            card = card,
                            modifier = Modifier
                                .fillMaxWidth()
                                .graphicsLayer {
                                    translationY = offset
                                }
                        ) {
                            navController.navigate(AppRoutes.CardDetail.createRoute(card.id))
                        }
                    }

                }
            }
        }
    }
}

/**
 * Displays a single credit/debit card in a stylized UI.
 *
 * This card layout includes:
 * - **Card name** (top left).
 * - **Company logo** (top right).
 * - **Masked card number** (middle left).
 * - **Visa logo** (bottom right).
 *
 * @param card The card object containing details such as name and number.
 * @param modifier Used to customize the appearance and layout.
 * @param onClick The action to perform when the card is clicked.
 */
@Composable
fun CardItem(card: Card, modifier: Modifier, onClick: () -> Unit) {

    Box(
        modifier = modifier
            .fillMaxWidth()
            .clickable { onClick() }
    ) {

        Card(
            shape = RoundedCornerShape(16.dp),
            colors = CardDefaults.cardColors(containerColor = CardBackgroundColor),
            modifier = Modifier
                .height(200.dp)
                .aspectRatio(16f/9f)
                .border(1.dp, BorderColor, RoundedCornerShape(16.dp))
                .align(Alignment.Center)

        ) {
            Column(
                modifier = Modifier
                    .padding(horizontal = 16.dp)
                    .fillMaxHeight(),
                verticalArrangement = Arrangement.SpaceBetween

            ) {
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(70.dp),
                    horizontalArrangement = Arrangement.SpaceBetween
                ) {
                    Text(
                        text = card.cardName,
                        color = CardTextColor,
                        fontSize = 18.sp,
                        fontWeight = FontWeight.Bold,
                        modifier = Modifier.align(Alignment.CenterVertically)

                    )
                    Image(
                        painter = painterResource(id = R.drawable.nitra_logo),
                        contentDescription = stringResource(R.string.desc_nitra_logo),
                        modifier = Modifier.size(70.dp)
                    )
                }
                Row(
                    modifier = Modifier
                        .fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceBetween
                ) {
                    Text(
                        text = stringResource(
                            R.string.card_list_masked_card_number,
                            card.cardNumber.takeLast(4)
                        ),
                        color = CardTextColor,
                        fontSize = 18.sp,
                        fontWeight = FontWeight.Bold,
                        modifier = Modifier.align(Alignment.CenterVertically)
                    )

                    Image(
                        painter = painterResource(id = R.drawable.visa),
                        contentDescription = stringResource(R.string.desc_visa_logo),
                        modifier = Modifier.size(50.dp)
                    )
                }
            }
        }
    }
}

/**
 * Displays an empty state UI when no cards are available.
 *
 * Includes:
 * - A **card icon** as a visual placeholder.
 * - A **description message** about adding a new card.
 * - A **button** to navigate to the "Add Card" screen.
 *
 * @param navController Used to navigate to the Add Card screen.
 */
@Composable
fun EmptyState(navController: NavController) {
    Spacer(modifier = Modifier.height(100.dp))

    Image(
        painter = painterResource(id = R.drawable.card_icon),
        contentDescription = stringResource(id = R.string.desc_card_image),
        modifier = Modifier.size(150.dp)
    )

    Spacer(modifier = Modifier.height(30.dp))

    Text(
        stringResource(id = R.string.card_list_main_content),
        modifier = Modifier.padding(horizontal = 15.dp),
        textAlign = TextAlign.Center
    )

    Spacer(modifier = Modifier.height(30.dp))

    Button(
        onClick = { navController.navigate(AppRoutes.AddCard.route) },
        colors = ButtonDefaults.buttonColors(containerColor = AddCardBtnColor),
        modifier = Modifier
            .fillMaxWidth()
            .height(50.dp),
        shape = RoundedCornerShape(8.dp)
    ) {
        Text(
            stringResource(id = R.string.card_list_add_first_card),
            color = CardTextColor,
            fontWeight = FontWeight.Bold
        )
    }
}

