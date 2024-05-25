package com.myapp.passwordmanagerapp.di

import android.content.Context
import androidx.room.Room
import com.myapp.passwordmanagerapp.data.dao.AccountDAO
import com.myapp.passwordmanagerapp.data.network.AccountDatabase
import com.myapp.passwordmanagerapp.data.repository.AccountRepositoryImpl
import com.myapp.passwordmanagerapp.domain.repository.AccountRepository
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent

@Module
@InstallIn(SingletonComponent::class)
class AppModule {
    @Provides
    fun provideAccountDb(
        @ApplicationContext
        context: Context
    ) = Room.databaseBuilder(
        context,
        AccountDatabase::class.java,
        "account_database"
    )
        .fallbackToDestructiveMigration()
        .build()

    @Provides
    fun provideAccountDao(
        accountDatabase: AccountDatabase
    ) = accountDatabase.accountDAO

    @Provides
    fun provideAccountRepository(
        accountDAO: AccountDAO
    ): AccountRepository = AccountRepositoryImpl(
        accountDAO = accountDAO
    )
}