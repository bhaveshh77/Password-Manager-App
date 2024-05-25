package com.myapp.passwordmanagerapp.data.network

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.myapp.passwordmanagerapp.data.dao.AccountDAO
import com.myapp.passwordmanagerapp.domain.model.Account
import kotlinx.coroutines.InternalCoroutinesApi


@Database(
    entities = [Account::class],
    version = 2,
    exportSchema = false
)
abstract class AccountDatabase: RoomDatabase() {
    abstract val accountDAO: AccountDAO


}