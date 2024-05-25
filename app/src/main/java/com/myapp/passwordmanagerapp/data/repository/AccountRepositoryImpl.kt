package com.myapp.passwordmanagerapp.data.repository

import com.myapp.passwordmanagerapp.data.dao.AccountDAO
import com.myapp.passwordmanagerapp.domain.model.Account
import com.myapp.passwordmanagerapp.domain.repository.AccountRepository
import com.myapp.passwordmanagerapp.domain.repository.Accounts
import kotlinx.coroutines.flow.Flow

class AccountRepositoryImpl(
    private val accountDAO : AccountDAO
) : AccountRepository{
    override fun getAccounts(): Flow<Accounts> {
        return accountDAO.getAllAccounts()
    }

    override suspend fun getAccountById(id: Long): Account {
        return accountDAO.getAccountById(id)
    }

    override suspend fun addAccount(account: Account) {
        accountDAO.addAccount(account)
    }

    override suspend fun updateAccount(account: Account) {
        accountDAO.updateAccount(account)
    }

    override suspend fun deleteAccount(account: Account) {
        accountDAO.deleteAccount(account)
    }

}