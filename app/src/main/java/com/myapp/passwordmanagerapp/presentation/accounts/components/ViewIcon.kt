package com.myapp.passwordmanagerapp.presentation.accounts.components

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowForwardIos
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.runtime.Composable

@Composable
fun ViewIcon(
    viewAccount: () -> Unit
) {
    IconButton(
        onClick = viewAccount
    ) {
        Icon(
            imageVector = Icons.Default.ArrowForwardIos,
            contentDescription = "View Account",
        )
    }
}