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
fun Password(

) {
    Text(
        text = "******",
        color = Color.LightGray,
        style = TextStyle(
            fontWeight = FontWeight.Medium
        ),
        fontSize = 30.sp,
        modifier = Modifier
            .padding(start = 8.dp)
            .padding(
                top = 12.dp
            )
    )
}