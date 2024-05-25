package com.myapp.passwordmanagerapp.presentation.accounts.components

import androidx.compose.foundation.background
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier

@Composable
fun AddAccountFloatingActionButton(
    openBottomSheet: () -> Unit
) {
    FloatingActionButton(
        onClick = openBottomSheet,
    ) {
        Icon(
            imageVector = Icons.Default.Add,
            contentDescription = "Add Account"
        )
    }
}