package com.myapp.passwordmanagerapp.presentation.accounts.viewmodels

import android.app.Application
import android.content.Context
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.compose.ui.platform.LocalContext
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.asLiveData
import androidx.lifecycle.viewModelScope
import com.myapp.passwordmanagerapp.domain.model.Account
import com.myapp.passwordmanagerapp.domain.repository.AccountRepository
import com.myapp.passwordmanagerapp.utils.AESUtil
import com.myapp.passwordmanagerapp.utils.KeyStore
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow

import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class AccountViewModel @Inject constructor(
    private val repository: AccountRepository,
    application: Application
) : AndroidViewModel(application = application) {

    val accounts = repository.getAccounts()

    val context = application.applicationContext
    private val secretKey = KeyStore.getSecretKey(context)


    var account by mutableStateOf(Account(0, "", "", ""))
    private set

    // Function to add a new account
    fun addAccount(account: Account) {
        val encryptedPassword = AESUtil.encrypt(account.password, secretKey)
        val encryptedAccount = Account(accountType = account.accountType, accountEmail = account.accountEmail, password = encryptedPassword)
        viewModelScope.launch(Dispatchers.IO) {
            repository.addAccount(account = encryptedAccount)
        }
    }

    // Function to update an existing account
    fun updateAccount(account: Account) {
        viewModelScope.launch(Dispatchers.IO) {
            repository.updateAccount(account)
        }
    }

    // Function to delete an account
    fun deleteAccount(account: Account) {
        viewModelScope.launch(Dispatchers.IO) {
            repository.deleteAccount(account)
        }
    }

    // Function to get an account by its ID
    fun getAccountById(id: Long): LiveData<Account?> {
        val result = MutableLiveData<Account?>()
        viewModelScope.launch(Dispatchers.IO) {
            result.postValue(repository.getAccountById(id))
        }
        return result
    }

    // Helper function to encrypt an account
    private suspend fun encryptAccount(account: Account): Account {
        val encryptedPassword = AESUtil.encrypt(account.password, secretKey)
        return Account(
            id = account.id,
            accountType = account.accountType,
            accountEmail = account.accountEmail,
            password = encryptedPassword
        )
    }

    // Helper function to decrypt an account
    private suspend fun decryptAccount(account: Account?): Account? {
        return account?.let {
            val decryptedPassword = AESUtil.decrypt(account.password, secretKey)
            Account(
                id = account.id,
                accountType = account.accountType,
                accountEmail = account.accountEmail,
                password = decryptedPassword
            )
        }
    }

}
