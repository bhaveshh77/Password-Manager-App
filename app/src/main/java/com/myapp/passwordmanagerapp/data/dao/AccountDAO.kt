package com.myapp.passwordmanagerapp.data.dao

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query
import androidx.room.Update
import com.myapp.passwordmanagerapp.domain.model.Account
import com.myapp.passwordmanagerapp.domain.repository.Accounts
import kotlinx.coroutines.flow.Flow

@Dao
interface AccountDAO {

    @Insert
    suspend fun addAccount(account: Account)

    @Update
    suspend fun updateAccount(account: Account)

    @Delete
    suspend fun deleteAccount(account: Account)

    @Query("SELECT * FROM `account_table`")
    fun getAllAccounts() : Flow<Accounts>


    @Query("SELECT * FROM `account_table` where id=:id")
    fun getAccountById(id:Long) : Account

}