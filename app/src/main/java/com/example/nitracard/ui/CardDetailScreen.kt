package com.example.nitracard.ui

import android.widget.Toast
import androidx.compose.foundation.Image
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.filled.ContentCopy
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalClipboardManager
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.AnnotatedString
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.example.nitracard.R
import com.example.nitracard.ui.theme.BackArrowColor
import com.example.nitracard.ui.theme.BorderColor
import com.example.nitracard.ui.theme.CardBackgroundColor
import com.example.nitracard.ui.theme.CardTextColor
import com.example.nitracard.ui.theme.DeleteCardBtnColor
import com.example.nitracard.ui.theme.DetailNameofCardGray
import com.example.nitracard.viewmodel.CardViewModel

/**
 * Displays the card details screen where users can view their saved card information.
 * Users can copy the card details and delete the card from their list.
 *
 * @param navController Navigation controller to handle navigation actions.
 * @param cardId The unique identifier of the card.
 * @param viewModel ViewModel that handles the card data and operations.
 */
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun CardDetailScreen(navController: NavController, cardId: Int, viewModel: CardViewModel) {

    val context = LocalContext.current
    var showDeleteDialog by remember { mutableStateOf(false) }
    val card = viewModel.cards.collectAsState().value.find { it.id == cardId }

    Scaffold(
        topBar = {
            TopAppBar(title = {
                Text(
                    stringResource(R.string.card_detail_title),
                    fontWeight = FontWeight.Bold
                )
            },
                navigationIcon = {
                    IconButton(onClick = { navController.popBackStack() }) {
                        Icon(
                            imageVector = Icons.AutoMirrored.Filled.ArrowBack,
                            contentDescription = stringResource(R.string.desc_back),
                            tint = BackArrowColor
                        )
                    }
                })
        },
    ) { paddingValues ->

        if (card != null) {
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(paddingValues)
                    .padding(16.dp),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {

                // Display credit card.
                Card(
                    shape = RoundedCornerShape(16.dp),
                    colors = CardDefaults.cardColors(containerColor = CardBackgroundColor),
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(220.dp)
                        .border(2.dp, BorderColor, RoundedCornerShape(16.dp))
                ) {
                    Column(
                        modifier = Modifier
                            .padding(horizontal = 16.dp)
                            .fillMaxHeight(),
                        verticalArrangement = Arrangement.SpaceBetween
                    ) {
                        Row(
                            modifier = Modifier.fillMaxWidth(),
                            horizontalArrangement = Arrangement.SpaceBetween,
                            verticalAlignment = Alignment.CenterVertically
                        ) {
                            Text(
                                text = card.cardName,
                                color = CardTextColor,
                                fontSize = 18.sp,
                                fontWeight = FontWeight.Bold,
                            )
                            Image(
                                painter = painterResource(id = R.drawable.nitra_logo),
                                contentDescription = stringResource(R.string.desc_nitra_logo),
                                modifier = Modifier.size(70.dp)
                            )
                        }
                        // Card Number.
                        Text(
                            text = stringResource(R.string.card_detail_card_number_title),
                            color = CardTextColor,
                            fontSize = 12.sp,
                            modifier = Modifier.padding(bottom = 5.dp)
                        )
                        Row(modifier = Modifier.padding(bottom = 20.dp)) {
                            Text(
                                text = card.cardNumber,
                                color = CardTextColor,
                                fontSize = 20.sp,
                                fontWeight = FontWeight.Bold,
                                modifier = Modifier.padding(end = 10.dp)
                            )
                            CopyButton(
                                card.cardNumber,
                                stringResource(R.string.card_detail_card_number_title)
                            )
                        }

                        // Exp Date & CVV
                        Row(
                            modifier = Modifier.fillMaxWidth(),
                            verticalAlignment = Alignment.CenterVertically
                        ) {
                            Column {
                                Text(
                                    text = stringResource(R.string.card_detail_card_expiry_date_title),
                                    color = CardTextColor,
                                    fontSize = 12.sp
                                )
                                Row {
                                    Text(
                                        text = "${card.expMonth}/${card.expYear.takeLast(2)}",
                                        color = CardTextColor,
                                        fontSize = 20.sp,
                                        modifier = Modifier.padding(end = 10.dp)
                                    )
                                    CopyButton(
                                        "${card.expMonth}/${card.expYear.takeLast(2)}",
                                        stringResource(R.string.card_detail_card_expiry_date_title)
                                    )
                                }

                            }
                            Spacer(modifier = Modifier.width(40.dp))
                            Column {
                                Text(
                                    text = stringResource(R.string.card_detail_card_cvv_title),
                                    color = CardTextColor,
                                    fontSize = 12.sp
                                )
                                Row {
                                    Text(
                                        text = card.cvv,
                                        color = CardTextColor,
                                        fontSize = 20.sp,
                                        modifier = Modifier.padding(end = 10.dp)
                                    )
                                    CopyButton(
                                        card.cvv,
                                        stringResource(R.string.card_detail_card_cvv_title)
                                    )
                                }
                            }
                        }

                        // Visa Logo.
                        Image(
                            painter = painterResource(id = R.drawable.visa),
                            contentDescription = stringResource(R.string.desc_visa_logo),
                            modifier = Modifier
                                .size(50.dp)
                                .align(Alignment.End),
                        )
                    }
                }

                Spacer(modifier = Modifier.height(100.dp))

                Card(
                    shape = RoundedCornerShape(16.dp),
                    colors = CardDefaults.cardColors(containerColor = DetailNameofCardGray),
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(70.dp)
                ) {
                    Column(
                        modifier = Modifier
                            .padding(start = 16.dp, top = 5.dp)
                            .fillMaxHeight(),
                        verticalArrangement = Arrangement.SpaceBetween
                    ) {
                        Text(
                            text = stringResource(R.string.add_card_name_of_card_label),
                            color = CardBackgroundColor,
                            fontSize = 12.sp,
                            modifier = Modifier.padding(5.dp),
                            fontWeight = FontWeight.Bold,
                            textAlign = TextAlign.Center
                        )
                        Row(modifier = Modifier.padding(bottom = 20.dp)) {
                            Text(
                                text = card.cardHolderName,
                                color = CardBackgroundColor,
                                fontSize = 20.sp,
                                fontWeight = FontWeight.Bold,
                                modifier = Modifier.padding(horizontal = 5.dp)
                            )
                            CopyButton(
                                card.cardNumber,
                                stringResource(R.string.card_detail_card_number_title)
                            )
                        }
                    }
                }

                Spacer(modifier = Modifier.weight(1f))

                // Delete Card Button
                Button(
                    onClick = { showDeleteDialog = true },
                    colors = ButtonDefaults.buttonColors(containerColor = DeleteCardBtnColor),
                    modifier = Modifier.fillMaxWidth(),
                    shape = RoundedCornerShape(8.dp),
                ) {
                    Text(
                        stringResource(id = R.string.card_detail_delete_card),
                        color = Color.White,
                        fontWeight = FontWeight.Bold
                    )
                }
            }
        } else {
            Text(
                stringResource(R.string.card_detail_card_not_found),
                fontSize = 20.sp,
                modifier = Modifier.padding(16.dp)
            )
        }
    }

    // Confirm deletion dialog.
    if (showDeleteDialog) {
        ConfirmDeleteDialog(
            onConfirm = {
                viewModel.deleteCard(cardId)
                showDeleteDialog = false
                Toast.makeText(
                    context,
                    context.getString(R.string.card_deleted_success),
                    Toast.LENGTH_SHORT
                ).show()
                navController.popBackStack()
            },
            onDismiss = { showDeleteDialog = false }
        )
    }
}

/**
 * A button for copying text to the clipboard.
 *
 * @param textToCopy The text that will be copied.
 * @param toastMessage The message displayed when the text is copied.
 * @param modifier Modifier for styling the button.
 */
@Composable
fun CopyButton(
    textToCopy: String,
    toastMessage: String,
    modifier: Modifier = Modifier.size(20.dp)
) {
    val clipboardManager = LocalClipboardManager.current
    val context = LocalContext.current

    Box(
        modifier = modifier
            .clickable {
                clipboardManager.setText(AnnotatedString(textToCopy))
                Toast
                    .makeText(
                        context, context.getString(
                            R.string.desc_copy_detail,
                            toastMessage
                        ), Toast.LENGTH_SHORT
                    )
                    .show()
            },
        contentAlignment = Alignment.Center
    ) {
        Icon(
            imageVector = Icons.Filled.ContentCopy,
            contentDescription = stringResource(
                R.string.desc_copy_detail,
                textToCopy
            ),
            tint = BackArrowColor,
            modifier = Modifier.size(15.dp)
        )
    }
}

/**
 * A button to confirm deletion of the card.
 *
 * @param onConfirm Callback function to execute when confirmed.
 * @param onDismiss Callback function to execute when canceled.
 */
@Composable
fun ConfirmDeleteDialog(
    onConfirm: () -> Unit,
    onDismiss: () -> Unit
) {
    AlertDialog(
        onDismissRequest = onDismiss,
        title = { Text(stringResource(R.string.dialog_delete_card_title)) },
        text = { Text(stringResource(R.string.dialog_delete_card_message)) },
        confirmButton = {
            TextButton(onClick = onConfirm) {
                Text(stringResource(R.string.dialog_delete_confirm), color = Color.Red)
            }
        },
        dismissButton = {
            TextButton(onClick = onDismiss) {
                Text(stringResource(R.string.dialog_delete_cancel))
            }
        }
    )
}