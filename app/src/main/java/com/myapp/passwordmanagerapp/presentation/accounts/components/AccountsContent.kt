package com.myapp.passwordmanagerapp.presentation.accounts.components

import androidx.compose.foundation.layout.ExperimentalLayoutApi
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import com.myapp.passwordmanagerapp.domain.model.Account
import com.myapp.passwordmanagerapp.domain.repository.Accounts

@Composable
fun AccountsContent(
    padding: PaddingValues,
    accounts : Accounts,
    viewAccount: (account : Account) -> Unit,
) {
    LazyColumn(
        modifier = Modifier.fillMaxSize().padding(padding)
    ) {
        items(
            accounts
        ) { account ->
            AccountCard(
                account = account,
                viewAccount = {
                    viewAccount(account)
                }
            )
        }
    }
}

