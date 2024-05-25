package com.myapp.passwordmanagerapp.utils

import android.util.Base64
import java.security.SecureRandom
import javax.crypto.Cipher
import javax.crypto.KeyGenerator
import javax.crypto.SecretKey
import javax.crypto.spec.GCMParameterSpec
import javax.crypto.spec.IvParameterSpec
import javax.crypto.spec.SecretKeySpec

object AESUtil {
    private const val AES_KEY_SIZE = 256
    private const val GCM_IV_LENGTH = 12
    private const val GCM_TAG_LENGTH = 16

    private fun generateSecretKey(): SecretKey {
        val keyGenerator = KeyGenerator.getInstance("AES")
        keyGenerator.init(AES_KEY_SIZE)
        return keyGenerator.generateKey()
    }

    fun encrypt(data: String, secretKey: SecretKey): String {
        val cipher = Cipher.getInstance("AES/GCM/NoPadding")
        val iv = ByteArray(GCM_IV_LENGTH)
        SecureRandom().nextBytes(iv)
        val gcmParameterSpec = GCMParameterSpec(GCM_TAG_LENGTH * 8, iv)
        cipher.init(Cipher.ENCRYPT_MODE, secretKey, gcmParameterSpec)
        val cipherText = cipher.doFinal(data.toByteArray())
        val ivAndCipherText = ByteArray(iv.size + cipherText.size)
        System.arraycopy(iv, 0, ivAndCipherText, 0, iv.size)
        System.arraycopy(cipherText, 0, ivAndCipherText, iv.size, cipherText.size)
        return Base64.encodeToString(ivAndCipherText, Base64.DEFAULT)
    }

    fun decrypt(data: String, secretKey: SecretKey): String {
        val ivAndCipherText = Base64.decode(data, Base64.DEFAULT)
        val iv = ByteArray(GCM_IV_LENGTH)
        val cipherText = ByteArray(ivAndCipherText.size - GCM_IV_LENGTH)
        System.arraycopy(ivAndCipherText, 0, iv, 0, iv.size)
        System.arraycopy(ivAndCipherText, iv.size, cipherText, 0, cipherText.size)
        val cipher = Cipher.getInstance("AES/GCM/NoPadding")
        val gcmParameterSpec = GCMParameterSpec(GCM_TAG_LENGTH * 8, iv)
        cipher.init(Cipher.DECRYPT_MODE, secretKey, gcmParameterSpec)
        val plainText = cipher.doFinal(cipherText)
        return String(plainText)
    }

    fun getKeyFromString(key: String): SecretKey {
        val decodedKey = Base64.decode(key, Base64.DEFAULT)
        return SecretKeySpec(decodedKey, 0, decodedKey.size, "AES")
    }

    fun generateKeyString(): String {
        val secretKey = generateSecretKey()
        return Base64.encodeToString(secretKey.encoded, Base64.DEFAULT)
    }
}