package com.myapp.passwordmanagerapp.domain.model

import android.os.Parcel
import android.os.Parcelable
import androidx.room.Entity
import androidx.room.PrimaryKey
import kotlinx.parcelize.Parcelize

@Entity(tableName = "account_table")
data class Account(

    @PrimaryKey(autoGenerate = true)
    val id : Long = 0,
    var accountType : String,
    var accountEmail: String,
    var password: String
)
