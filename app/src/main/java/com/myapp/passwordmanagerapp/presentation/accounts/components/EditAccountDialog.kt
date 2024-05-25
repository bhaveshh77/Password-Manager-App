package com.myapp.passwordmanagerapp.presentation.accounts.components

import android.util.Log
import android.widget.Toast
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.myapp.passwordmanagerapp.domain.model.Account
import com.myapp.passwordmanagerapp.utils.AESUtil
import javax.crypto.SecretKey

@Composable
fun EditAccountDialog(
    account: Account,
    onDismiss: () -> Unit,
    onUpdate: (Account) -> Unit,
    secretKey: SecretKey
) {
    var accountType by remember { mutableStateOf(account.accountType) }
    var username by remember { mutableStateOf(account.accountEmail) }
    var password by remember { mutableStateOf(account.password) }

    val context = LocalContext.current

    LaunchedEffect(account) {
        password = AESUtil.decrypt(account.password, secretKey)
    }

    Log.e("decrypt", password)

    AlertDialog(
        onDismissRequest = { onDismiss() },
        title = { Text(text = "Update Account", fontWeight = FontWeight.Bold, fontSize = 20.sp) },
        text = {
            Column {
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
            }
        },
        confirmButton = {
            Button(
                onClick = {
                    if (accountType.isNotBlank() && username.isNotBlank() && password.isNotBlank()) {

                        val encryptedPassword = AESUtil.encrypt(password, secretKey)
                        Log.e("encrypt", encryptedPassword)


                        val updatedAccount = account.copy(
                            accountType = accountType,
                            accountEmail = username,
                            password = encryptedPassword
                        )
                        onUpdate(updatedAccount)
                        Toast.makeText(context, "Account Updated!", Toast.LENGTH_SHORT).show()
                        onDismiss()
                    } else {
                        Toast.makeText(context, "Please fill in all the fields", Toast.LENGTH_SHORT).show()
                    }
                },
                colors = ButtonDefaults.buttonColors(
                    containerColor = Color(0xFF4CAF50), // Custom green color
                    contentColor = androidx.compose.ui.graphics.Color.White
                ),
                shape = RoundedCornerShape(8.dp),
                modifier = Modifier
                    .padding(8.dp)
                    .height(48.dp)
                    .fillMaxWidth()
                    .shadow(4.dp, shape = RoundedCornerShape(8.dp)) // Adding shadow
            ) {
                Text("Update", fontWeight = FontWeight.Bold, fontSize = 16.sp)
            }
        },
        dismissButton = {
            Button(
                onClick = { onDismiss() },
                colors = ButtonDefaults.buttonColors(
                    containerColor = Color(0xFFF44336), // Custom red color
                    contentColor = androidx.compose.ui.graphics.Color.White
                ),
                shape = RoundedCornerShape(8.dp),
                modifier = Modifier
                    .padding(8.dp)
                    .height(48.dp)
                    .fillMaxWidth()
                    .shadow(4.dp, shape = RoundedCornerShape(8.dp)) // Adding shadow
            ) {
                Text("Cancel", fontWeight = FontWeight.Bold, fontSize = 16.sp)
            }
        }
    )
}