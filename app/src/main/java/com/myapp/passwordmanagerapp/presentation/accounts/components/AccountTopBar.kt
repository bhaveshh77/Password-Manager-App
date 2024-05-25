package com.myapp.passwordmanagerapp.presentation.accounts.components

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.height
import androidx.compose.material3.Divider
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AccountTopBar() {

    Column {
        Spacer(modifier = Modifier.height(12.dp)) // Add some space above the top app bar
        TopAppBar(
            title = {
                Text(
                    text = "Password Manager App",
                    fontSize = 24.sp,
                    fontWeight = FontWeight.Bold,
                    style = MaterialTheme.typography.labelLarge,
                    color = Color.Black // Assuming you want the text to be white
                )
                    },
            colors = TopAppBarDefaults.smallTopAppBarColors(
                containerColor = Color.White // Set the background color of the app bar
            )
        )
        Divider() // Add a divider below the top app bar
    }
}
