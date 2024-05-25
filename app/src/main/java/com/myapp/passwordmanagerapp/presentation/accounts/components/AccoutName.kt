package com.myapp.passwordmanagerapp.presentation.accounts.components

import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp


@Composable
fun AccountName(
    accountName: String
) {
    Text(
        text = accountName,
        color = Color.Black,
        style = TextStyle(
            fontWeight = FontWeight.Bold
        ),
        fontSize = 20.sp,
        modifier = Modifier
            .padding(start = 18.dp)
    )
}