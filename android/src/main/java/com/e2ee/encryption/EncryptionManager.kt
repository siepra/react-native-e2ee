package com.e2ee.encryption

/**
 * Thanks to Nipun Ruwanpathirana for sharing the core methods for e2ee on Android
 * (https://nipunr.medium.com/end-to-end-encryption-ios-android-rsa-65cfd015184a)
 */

import CipherWrapper
import KeyStoreWrapper

import android.content.Context
import android.util.Base64

import java.security.KeyFactory
import java.security.KeyPair
import java.security.PublicKey
import java.security.spec.X509EncodedKeySpec


class EncryptionManager(context: Context) {

    companion object {
        const val MASTER_KEY = "master_key"
        const val KEY_PAIR_ALGORITHM = "RSA"
        const val KEY_SIZE: Int = 2048
        const val KEY_PROVIDER = "AndroidKeyStore"
        const val TRANSFORMATION_ASYMMETRIC = "RSA/None/PKCS1Padding"
    }

    private val keyStoreWrapper = KeyStoreWrapper(context)

    /*
     * Encryption Stage
     */
    fun createMasterKey(): KeyPair? {
        return if (keyStoreWrapper.getAndroidKeyStoreAsymmetricKeyPair(MASTER_KEY) == null) {
            keyStoreWrapper.createAndroidKeyStoreAsymmetricKey(MASTER_KEY)
        } else {
            keyStoreWrapper.getAndroidKeyStoreAsymmetricKeyPair(MASTER_KEY)
        }
    }

    fun removeMasterKey() {
        keyStoreWrapper.removeAndroidKeyStoreKey(MASTER_KEY)
    }

    fun encrypt(data: String): String? {
        val masterKey = keyStoreWrapper.getAndroidKeyStoreAsymmetricKeyPair(MASTER_KEY)
        return CipherWrapper(TRANSFORMATION_ASYMMETRIC).encrypt(data, masterKey?.public)
    }

    fun encrypt(data: ByteArray): ByteArray? {
        val masterKey = keyStoreWrapper.getAndroidKeyStoreAsymmetricKeyPair(MASTER_KEY)
        return CipherWrapper(TRANSFORMATION_ASYMMETRIC).encrypt(data, masterKey?.public)
    }

    fun encryptOthers(data: String, publicKeyString: String): String? {
        val publicKey: PublicKey? = getOtherPublicKey(publicKeyString)
        return if (publicKey !== null) {
            CipherWrapper(TRANSFORMATION_ASYMMETRIC).encrypt(data, publicKey)
        } else {
            null
        }
    }

    fun encryptOthers(data: ByteArray, publicKeyString: String): ByteArray? {
        val publicKey: PublicKey? = getOtherPublicKey(publicKeyString)
        return if (publicKey !== null) {
            CipherWrapper(TRANSFORMATION_ASYMMETRIC).encrypt(data, publicKey)
        } else {
            null
        }
    }

    fun decrypt(data: String): String? {
        val masterKey = keyStoreWrapper.getAndroidKeyStoreAsymmetricKeyPair(MASTER_KEY)
        return CipherWrapper(TRANSFORMATION_ASYMMETRIC).decrypt(data, masterKey?.private)
    }

    fun decrypt(data: ByteArray): ByteArray? {
        val masterKey = keyStoreWrapper.getAndroidKeyStoreAsymmetricKeyPair(MASTER_KEY)
        return CipherWrapper(TRANSFORMATION_ASYMMETRIC).decrypt(data, masterKey?.private)
    }

    /*
     * Manage Keys
     */
    fun getMyPublicKey(): PublicKey? {
        val masterKey = keyStoreWrapper.getAndroidKeyStoreAsymmetricKeyPair(MASTER_KEY)
        return masterKey?.public
    }

    fun getMyPublicKeyString(): String? {
        val publicKey: PublicKey? = getMyPublicKey()
        return if (publicKey !== null) {
            String(Base64.encode(publicKey.encoded, Base64.DEFAULT))
        } else {
            null
        }
    }

    private fun getOtherPublicKey(key: String): PublicKey? {
        return try {
            val publicBytes: ByteArray =
                Base64.decode(key, Base64.DEFAULT)
            val keySpec = X509EncodedKeySpec(publicBytes)
            val keyFactory = KeyFactory.getInstance(KEY_PAIR_ALGORITHM)
            keyFactory.generatePublic(keySpec)
        } catch (e: Exception) {
            e.printStackTrace()
            null
        }
    }
}
