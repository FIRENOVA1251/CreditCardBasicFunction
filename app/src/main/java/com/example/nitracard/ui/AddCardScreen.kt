package com.example.nitracard.ui

import android.widget.Toast
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.filled.ArrowDropDown
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.TextRange
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.TextFieldValue
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.example.nitracard.R
import com.example.nitracard.viewmodel.CardViewModel
import com.example.nitracard.data.Card
import com.example.nitracard.ui.theme.AddCardBtnColor
import com.example.nitracard.ui.theme.EditHintColor


/**
 * Composable function for the "Add Card" screen.
 * Allows users to input card details and save them in the database.
 *
 * @param navController The navigation controller to handle screen transitions.
 * @param viewModel The ViewModel responsible for managing card-related operations.
 */
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AddCardScreen(navController: NavController, viewModel: CardViewModel) {
    val context = LocalContext.current

    var cardName by remember { mutableStateOf("") }
    var cardHolderName by remember { mutableStateOf("") }
    var cardNumber by remember { mutableStateOf(TextFieldValue("")) }
    var cvv by remember { mutableStateOf("") }

    val months = (1..12).map { it.toString().padStart(2, '0') }
    val years = (2010..2035).map { it.toString() }
    var expMonth by remember { mutableStateOf<String?>(null) }
    var expYear by remember { mutableStateOf<String?>(null) }

    Scaffold(
        topBar = {
            TopAppBar(title = {
                Text(
                    stringResource(id = R.string.add_card_title),
                    fontWeight = FontWeight.Bold
                )
            },
                navigationIcon = {
                    IconButton(onClick = { navController.popBackStack() }) {
                        Icon(
                            imageVector = Icons.AutoMirrored.Filled.ArrowBack,
                            contentDescription = stringResource(R.string.desc_back),
                            tint = Color.LightGray
                        )
                    }
                })
        },
    ) { paddingValues ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(paddingValues)
                .padding(16.dp),
        ) {
            // Card Name
            CustomTextField(
                title = stringResource(R.string.add_card_card_name_title),
                hint = stringResource(R.string.add_card_card_name_label),
                value = cardName,
                onValueChange = { if (it.length <= 15) cardName = it }
            )

            Spacer(modifier = Modifier.height(8.dp))

            // Card Holder Name
            CustomTextField(
                title = stringResource(R.string.add_card_name_of_card_title),
                hint = stringResource(R.string.add_card_name_of_card_label),
                value = cardHolderName,
                onValueChange = { if (it.length <= 15) cardHolderName = it }
            )

            Spacer(modifier = Modifier.height(8.dp))

            // Card Number.
            Text(stringResource(R.string.add_card_card_number_title), fontWeight = FontWeight.Bold)
            OutlinedTextField(
                value = cardNumber,
                onValueChange = { newValue ->

                    val newText = newValue.text
                    // Only 16 numbers.
                    val digits = newValue.text.filter { char -> char.isDigit() }.take(16)
                    // Add space within every 4 digits.
                    val formattedText =
                        digits.chunked(4).joinToString(" ")

                    // Calculate cursor position.
                    var newCursorPos = newValue.selection.start
                    if (newText.length % 5 == 0) {
                        newCursorPos = newValue.selection.start + 1
                    }

                    // Update state
                    cardNumber = TextFieldValue(
                        text = formattedText,
                        selection = TextRange(newCursorPos)
                    )
                },
                label = {
                    Text(
                        stringResource(R.string.add_card_card_number_label),
                        color = Color.LightGray
                    )
                },
                modifier = Modifier.fillMaxWidth(),
                keyboardOptions = KeyboardOptions.Default.copy(
                    keyboardType = KeyboardType.NumberPassword
                )
            )

            Spacer(modifier = Modifier.height(8.dp))

            // Expiry Date.
            Text(stringResource(R.string.add_card_expiry_date_title), fontWeight = FontWeight.Bold)
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                DropdownMenuField(
                    stringResource(R.string.add_card_month_label),
                    expMonth,
                    months,
                    { expMonth = it },
                    modifier = Modifier
                        .weight(1f)
                        .padding(vertical = 8.dp)
                        .border(1.dp, Color.Gray, RoundedCornerShape(4.dp))
                        .padding(16.dp)

                )

                Spacer(modifier = Modifier.width(8.dp))

                DropdownMenuField(
                    stringResource(R.string.add_card_year_label), expYear, years, { expYear = it },
                    modifier = Modifier
                        .weight(1f)
                        .padding(vertical = 8.dp)
                        .border(1.dp, Color.Gray, RoundedCornerShape(4.dp))
                        .padding(16.dp)
                )
            }

            Spacer(modifier = Modifier.height(8.dp))

            // CVV
            CustomTextField(
                title = stringResource(R.string.add_card_cvv_title),
                hint = stringResource(R.string.add_card_cvv_label),
                value = cvv,
                onValueChange = { if (it.length <= 3) cvv = it },
                keyboardType = KeyboardType.NumberPassword
            )

            Spacer(modifier = Modifier.height(30.dp))

            // Add Card Button.
            Button(
                onClick = {
                    val errorMessage = validateCardDetails(
                        cardName, cardHolderName, cardNumber.text.replace(" ", ""),
                        expMonth, expYear, cvv, context
                    )

                    if (errorMessage != null) {
                        Toast.makeText(context, errorMessage, Toast.LENGTH_SHORT).show()
                    } else {
                        viewModel.addCard(
                            Card(
                                cardName = cardName,
                                cardHolderName = cardHolderName,
                                cardNumber = cardNumber.text,
                                expMonth = expMonth!!,
                                expYear = expYear!!,
                                cvv = cvv
                            )
                        )
                        Toast.makeText(
                            context,
                            context.getString(R.string.toast_add_card_successfully),
                            Toast.LENGTH_SHORT
                        ).show()
                        navController.popBackStack()
                    }
                },
                colors = ButtonDefaults.buttonColors(containerColor = AddCardBtnColor),
                modifier = Modifier
                    .fillMaxWidth()
                    .height(50.dp),
                shape = RoundedCornerShape(8.dp)
            ) {
                Text(
                    stringResource(R.string.add_card_btn),
                    color = Color.White,
                    fontWeight = FontWeight.Bold
                )
            }
        }
    }
}

/**
 * A reusable dropdown menu field for selecting values from a list.
 *
 * @param label The placeholder text displayed when no value is selected.
 * @param selectedValue The currently selected value, or null if none is selected.
 * @param options A list of options available for selection.
 * @param onValueChange A callback function triggered when an option is selected.
 * @param modifier The modifier to apply to the dropdown container.
 */
@Composable
fun DropdownMenuField(
    label: String,
    selectedValue: String?,
    options: List<String>,
    onValueChange: (String) -> Unit,
    modifier: Modifier = Modifier
) {

    var expanded by remember { mutableStateOf(false) }

    Box(
        modifier = modifier
            .clickable { expanded = true }

    ) {
        Row(
            modifier = Modifier.fillMaxWidth()

        ) {
            Text(
                text = selectedValue ?: label,
                color = if (selectedValue != null) Color.Black else Color.LightGray,
                fontSize = 16.sp,
                modifier = Modifier
                    .weight(1f)
                    .align(Alignment.CenterVertically)
            )
            Icon(
                imageVector = Icons.Default.ArrowDropDown,
                contentDescription = stringResource(R.string.desc_drop_down_arrow)

            )
        }

        DropdownMenu(expanded = expanded, onDismissRequest = { expanded = false }) {
            options.forEach { value ->
                DropdownMenuItem(
                    text = { Text(value) },
                    onClick = {
                        onValueChange(value)
                        expanded = false
                    }
                )
            }
        }
    }
}

/**
 * A reusable composable function for input fields.
 *
 * @param title The title displayed above the input field.
 * @param hint The placeholder text inside the input field.
 * @param value The current text value of the input.
 * @param onValueChange A callback function to handle input changes.
 * @param keyboardType The type of keyboard input (default is text).
 */
@Composable
fun CustomTextField(
    title: String,
    hint: String,
    value: String,
    onValueChange: (String) -> Unit,
    keyboardType: KeyboardType = KeyboardType.Text
) {
    Column {
        Text(title, fontWeight = FontWeight.Bold)
        OutlinedTextField(
            value = value,
            onValueChange = onValueChange,
            label = { Text(hint, color = EditHintColor) },
            modifier = Modifier.fillMaxWidth(),
            keyboardOptions = KeyboardOptions.Default.copy(keyboardType = keyboardType)
        )
    }
}

/**
 * Validates the card details input by the user.
 *
 * @return A string containing the validation error message, or null if the input is valid.
 */
fun validateCardDetails(
    cardName: String,
    cardHolderName: String,
    cardNumber: String,
    expMonth: String?,
    expYear: String?,
    cvv: String,
    context: android.content.Context
): String? {
    return when {
        cardName.isEmpty() -> context.getString(R.string.toast_add_card_invalid_name)
        cardHolderName.isEmpty() -> context.getString(R.string.toast_add_card_invalid_holder_name)
        cardNumber.length != 16 -> context.getString(R.string.toast_add_card_invalid_card_number)
        expMonth == null || expYear == null -> context.getString(R.string.toast_add_card_invalid_expiry_date)
        cvv.length != 3 -> context.getString(R.string.toast_add_card_invalid_cvv)
        else -> null
    }
}