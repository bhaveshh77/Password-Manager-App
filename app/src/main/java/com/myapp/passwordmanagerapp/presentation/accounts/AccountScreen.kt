package com.myapp.passwordmanagerapp.presentation.accounts

import android.widget.Toast
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Visibility
import androidx.compose.material.icons.filled.VisibilityOff
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.ModalBottomSheet
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.rememberModalBottomSheetState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.input.key.Key
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import com.myapp.passwordmanagerapp.domain.model.Account
import com.myapp.passwordmanagerapp.navigation.screens.Screen
import com.myapp.passwordmanagerapp.presentation.accounts.components.AccountDetailItem
import com.myapp.passwordmanagerapp.presentation.accounts.components.AccountTopBar
import com.myapp.passwordmanagerapp.presentation.accounts.components.AccountsContent
import com.myapp.passwordmanagerapp.presentation.accounts.components.AddAccountFloatingActionButton
import com.myapp.passwordmanagerapp.presentation.accounts.components.EditAccountDialog
import com.myapp.passwordmanagerapp.presentation.accounts.viewmodels.AccountViewModel
import com.myapp.passwordmanagerapp.utils.AESUtil
import com.myapp.passwordmanagerapp.utils.KeyStore


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AccountScreen(
    viewModel: AccountViewModel,
) {
    val accounts by viewModel.accounts.collectAsState(
        initial = emptyList()
    )

    val sheetState = rememberModalBottomSheetState()

    val isAddAccountSheetOpen = remember { mutableStateOf(false) }

    var showDialog by remember { mutableStateOf(false) }
    var accountToDelete by remember { mutableStateOf<Account?>(null) }

    var showEditDialog by remember { mutableStateOf(false) }
    var selectedEditAccount by remember { mutableStateOf<Account?>(null) }


    val isViewAccountSheetOpen = remember { mutableStateOf(false) }
    val accountSheetState = rememberModalBottomSheetState()
    val selectedAccount = remember { mutableStateOf<Account?>(null) }

    val secretKey = KeyStore.getSecretKey(LocalContext.current)


    Scaffold(
        topBar = {
            AccountTopBar()
        },
        content = { padding ->
            AccountsContent(
                padding = padding,
                accounts = accounts,
                viewAccount = { account ->
                    selectedAccount.value = account
                    isViewAccountSheetOpen.value = true
                },
            )
        },
        floatingActionButton = {
            AddAccountFloatingActionButton(
                openBottomSheet = {
                    isAddAccountSheetOpen.value = true
                }
            )
        }
    )

    if(isAddAccountSheetOpen.value) {
        ModalBottomSheet(onDismissRequest = {isAddAccountSheetOpen.value = false},
            sheetState = sheetState,
            content = {
                BottomSheetItems(
                    onAddAccount = { accountType, username, password ->
                        // Add logic to handle adding the account here
                        // For example, call a ViewModel function to add the account
                        viewModel.addAccount(Account(accountType = accountType, accountEmail = username, password = password))
                        // Close the bottom sheet
                        isAddAccountSheetOpen.value = false
                    }
                )
            },
            modifier = Modifier.height(400.dp)
        )
    }

    val context = LocalContext.current

    if (isViewAccountSheetOpen.value && selectedAccount.value != null) {
        ModalBottomSheet(
            onDismissRequest = { isViewAccountSheetOpen.value = false },
            sheetState = accountSheetState,
            content = {
                BottomSheetDetailItem(
                    account = selectedAccount.value!!,
                    onEditAccount = { account ->

                        selectedEditAccount = account
                        showEditDialog = true

                        Toast.makeText(context, "You Clicked Edit", Toast.LENGTH_SHORT).show()
                        isViewAccountSheetOpen.value = false
                    },
                    onDeleteAccount = { account ->
                        accountToDelete = account
                        showDialog = true
                    }
                )
            },
            modifier = Modifier.height(500.dp)
        )
    }

    if (showDialog) {
        DeleteConfirmationDialog(
            onConfirm = {
                viewModel.deleteAccount(accountToDelete!!)
                Toast.makeText(context, "Account deleted", Toast.LENGTH_SHORT).show()
                showDialog = false
                isViewAccountSheetOpen.value = false
            },
            onCancel = {
                showDialog = false
                isViewAccountSheetOpen.value = false
            }
        )
    }

    if (showEditDialog) {
        EditAccountDialog(
            account = selectedEditAccount!!,
            onDismiss = { showEditDialog = false },
            onUpdate = { updatedAccount ->
                viewModel.updateAccount(updatedAccount)
                showEditDialog = false
            },
            secretKey = secretKey
        )
    }
}

@Composable
fun DeleteConfirmationDialog(
    onConfirm: () -> Unit,
    onCancel: () -> Unit
) {
    AlertDialog(
        onDismissRequest = onCancel,
        title = { Text("Delete Account") },
        text = { Text("Are you sure you want to delete this account?") },
        confirmButton = {
            Button(
                onClick = onConfirm,
                elevation = ButtonDefaults.buttonElevation(
                    defaultElevation = 5.dp
                ),
                colors = ButtonDefaults.buttonColors(
                    containerColor = androidx.compose.ui.graphics.Color.Red,
                    contentColor = androidx.compose.ui.graphics.Color.White
                ),
                modifier = Modifier
            ) {
                Text("Confirm")
            }
        },
        dismissButton = {
            Button(
                onClick = onCancel,
                elevation = ButtonDefaults.buttonElevation(
                    defaultElevation = 5.dp
                ),
                colors = ButtonDefaults.buttonColors(
                    containerColor = androidx.compose.ui.graphics.Color.Black,
                    contentColor = androidx.compose.ui.graphics.Color.White
                )
            ) {

                Text("Cancel")

            }
        }
    )
}


@Composable
fun BottomSheetItems(
    onAddAccount: (accountType: String, username: String, password: String) -> Unit
) {
    var accountType by remember { mutableStateOf("") }
    var username by remember { mutableStateOf("") }
    var password by remember { mutableStateOf("") }

    Column(
        modifier = Modifier
            .fillMaxWidth()
            .padding(16.dp)
    ) {
        OutlinedTextField(
            value = accountType,
            onValueChange = { accountType = it },
            label = { Text("Account Type") },
            modifier = Modifier.fillMaxWidth()
        )

        Spacer(modifier = Modifier.height(16.dp))

        OutlinedTextField(
            value = username,
            onValueChange = { username = it },
            label = { Text("Username/Email") },
            modifier = Modifier.fillMaxWidth()
        )

        Spacer(modifier = Modifier.height(16.dp))

        OutlinedTextField(
            value = password,
            onValueChange = { password = it },
            label = { Text("Password") },
            modifier = Modifier.fillMaxWidth()
        )

        Spacer(modifier = Modifier.height(24.dp))
//        val context = ContextAmbient.current

        val context = LocalContext.current


        Button(
            onClick = {
                if (accountType.isBlank() || username.isBlank() || password.isBlank()) {
                    Toast.makeText(context, "Please fill in all the fields", Toast.LENGTH_SHORT).show()
                } else {
                    onAddAccount(accountType, username, password)
                }
            },

            modifier = Modifier
                .fillMaxWidth()
                .padding(15.dp)
                .height(60.dp),
            colors = ButtonDefaults.buttonColors(
                containerColor = androidx.compose.ui.graphics.Color.Black,
                contentColor = androidx.compose.ui.graphics.Color.White
            )
        ) {
            Text(
                "Add Account",
                fontWeight = FontWeight.Bold,
                fontSize = 22.sp
            )
        }
    }

}

@Composable
fun BottomSheetDetailItem(
    account: Account,
    onEditAccount: (Account) -> Unit,
    onDeleteAccount: (Account) -> Unit
) {
    var passwordVisible by remember { mutableStateOf(false) }
    var decryptedPassword by remember { mutableStateOf("********") }
    val context  = LocalContext.current

    Column(
        modifier = Modifier
            .fillMaxWidth()
            .padding(16.dp)
    ) {
        // Heading "Account Details" in blue
        Text(
            text = "Account Details",
            color = androidx.compose.ui.graphics.Color.Blue,
            fontSize = 24.sp,
            fontWeight = FontWeight.Bold,
            modifier = Modifier.padding(bottom = 8.dp)
        )
        Spacer(modifier = Modifier.height(20.dp))

        // Displaying account details
        AccountDetailItem("Account Type", account.accountType)

        Spacer(modifier = Modifier.height(20.dp))

        AccountDetailItem("Username/Email", account.accountEmail)

        Spacer(modifier = Modifier.height(20.dp))

//        AccountDetailItem("Password", "********")

        Row(
            verticalAlignment = Alignment.CenterVertically,
            modifier = Modifier.fillMaxWidth()
        ) {
            Text(text = "Password", fontWeight = FontWeight.Bold, fontSize = 16.sp)
            Spacer(modifier = Modifier.width(8.dp))
            Box(modifier = Modifier.weight(1f)) {
                BasicTextField(
                    value = decryptedPassword,
                    onValueChange = {},
                    readOnly = true,
                    visualTransformation = if (passwordVisible) VisualTransformation.None else PasswordVisualTransformation(),
                    modifier = Modifier.fillMaxWidth()
                )
            }
            IconButton(
                onClick = {
                    passwordVisible = !passwordVisible
                    if (passwordVisible) {
                        decryptedPassword = AESUtil.decrypt(account.password, secretKey = KeyStore.getSecretKey(
                            context = context))
                    } else {
                        decryptedPassword = "********"
                    }
                }
            ) {
                val icon: ImageVector = if (passwordVisible) Icons.Filled.Visibility else Icons.Filled.VisibilityOff
                Icon(imageVector = icon, contentDescription = if (passwordVisible) "Hide password" else "Show password")
            }
        }
        Spacer(modifier = Modifier.height(24.dp))

        // Add more account details as needed

        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            Button(
                onClick = {onEditAccount(account) },
                shape = RoundedCornerShape(30.dp),
                modifier = Modifier
                    .weight(1f)
                    .height(50.dp)
                    .padding(end = 8.dp),
                elevation = ButtonDefaults.buttonElevation(
                    defaultElevation = 5.dp
                ),
                colors = ButtonDefaults.buttonColors(
                    containerColor = androidx.compose.ui.graphics.Color.Black,
                    contentColor = androidx.compose.ui.graphics.Color.White
                )
            ) {
                Text(
                    text = "Edit", color = Color.White,
                    fontWeight = FontWeight.Bold,
                    fontSize = 24.sp
                )
            }
            Button(
                onClick = {onDeleteAccount(account)},
                modifier = Modifier
                    .weight(1f)
                    .height(50.dp),
                elevation = ButtonDefaults.buttonElevation(
                    defaultElevation = 5.dp
                ),
                colors = ButtonDefaults.buttonColors(
                    containerColor = androidx.compose.ui.graphics.Color.Red,
                    contentColor = androidx.compose.ui.graphics.Color.White
                )
            ) {
                Text(
                    text = "Delete", color = Color.White,
                    fontWeight = FontWeight.Bold,
                    fontSize = 24.sp
                )
            }
        }

    }
}
