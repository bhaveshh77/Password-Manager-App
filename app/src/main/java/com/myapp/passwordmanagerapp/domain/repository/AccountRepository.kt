package com.myapp.passwordmanagerapp.domain.repository

import com.myapp.passwordmanagerapp.domain.model.Account
import kotlinx.coroutines.flow.Flow


typealias Accounts = List<Account>

interface AccountRepository {

    fun getAccounts() : Flow<Accounts>

    suspend fun getAccountById(id: Long) : Account

    suspend fun addAccount(account: Account)

    suspend fun updateAccount(account: Account)

    suspend fun deleteAccount(account: Account)


}