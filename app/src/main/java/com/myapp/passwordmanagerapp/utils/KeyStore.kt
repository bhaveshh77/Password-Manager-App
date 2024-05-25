package com.myapp.passwordmanagerapp.utils

import android.content.Context
import androidx.security.crypto.EncryptedSharedPreferences
import androidx.security.crypto.MasterKeys
import javax.crypto.SecretKey

object KeyStore {
    private const val KEY_ALIAS = "my_app_master_key"
    private const val PREFS_NAME = "app_encrypted_preferences"
    private const val KEY_AES = "aes_encryption_key"

    fun getSecretKey(context: Context): SecretKey {
        val masterKeyAlias = MasterKeys.getOrCreate(MasterKeys.AES256_GCM_SPEC)
        val sharedPreferences = EncryptedSharedPreferences.create(
            PREFS_NAME,
            masterKeyAlias,
            context,
            EncryptedSharedPreferences.PrefKeyEncryptionScheme.AES256_SIV,
            EncryptedSharedPreferences.PrefValueEncryptionScheme.AES256_GCM
        )
        val keyString = sharedPreferences.getString(KEY_AES, null)
        return if (keyString != null) {
            AESUtil.getKeyFromString(keyString)
        } else {
            val newKeyString = AESUtil.generateKeyString()
            sharedPreferences.edit().putString(KEY_AES, newKeyString).apply()
            AESUtil.getKeyFromString(newKeyString)
        }
    }
}