/**
 * Thanks to Nipun Ruwanpathirana for sharing the core methods for e2ee on Android
 * (https://nipunr.medium.com/end-to-end-encryption-ios-android-rsa-65cfd015184a)
 */

import android.content.Context
import android.os.Build
import android.security.KeyPairGeneratorSpec
import android.security.keystore.KeyGenParameterSpec
import android.security.keystore.KeyProperties
import android.util.Base64
import java.math.BigInteger
import java.security.KeyPair
import java.security.KeyPairGenerator
import java.security.KeyStore
import java.security.PrivateKey
import javax.security.auth.x500.X500Principal

class KeyStoreWrapper(private val context: Context) {

    private val keyStore: KeyStore = createAndroidKeyStore()

    fun getAndroidKeyStoreAsymmetricKeyPair(alias: String): KeyPair? {
        val privateKey = keyStore.getKey(alias, null) as PrivateKey?
        val publicKey = keyStore.getCertificate(alias)?.publicKey

        return if (privateKey != null && publicKey != null) {
            KeyPair(publicKey, privateKey)
        } else {
            null
        }
    }

    fun createAndroidKeyStoreAsymmetricKey(alias: String): KeyPair {
        val generator = KeyPairGenerator.getInstance(
            EncryptionManager.KEY_PAIR_ALGORITHM,
            EncryptionManager.KEY_PROVIDER
        )

        if (Build.VERSION.SDK_INT < Build.VERSION_CODES.M) {
            initGeneratorWithKeyPairGeneratorSpec(generator, alias)
        } else {
            initGeneratorWithKeyGenParameterSpec(generator, alias)
        }

        return generator.generateKeyPair()
    }

    fun removeAndroidKeyStoreKey(alias: String) = keyStore.deleteEntry(alias)

    @Suppress("DEPRECATION")
    private fun initGeneratorWithKeyPairGeneratorSpec(generator: KeyPairGenerator, alias: String) {
        val builder = KeyPairGeneratorSpec.Builder(context)
            .setAlias(alias)
            .setSerialNumber(BigInteger.ONE)
            .setSubject(X500Principal("CN=${alias} CA Certificate"))

        generator.initialize(builder.build())
    }

    private fun initGeneratorWithKeyGenParameterSpec(generator: KeyPairGenerator, alias: String) {
        val builder = KeyGenParameterSpec.Builder(
            alias,
            KeyProperties.PURPOSE_ENCRYPT or KeyProperties.PURPOSE_DECRYPT
        )
            .setBlockModes(KeyProperties.BLOCK_MODE_ECB)
            .setDigests(KeyProperties.DIGEST_SHA256)
            .setEncryptionPaddings(KeyProperties.ENCRYPTION_PADDING_RSA_PKCS1)
            .setKeySize(2048)
        generator.initialize(builder.build())
    }

    private fun createAndroidKeyStore(): KeyStore {
        val keyStore = KeyStore.getInstance(EncryptionManager.KEY_PROVIDER)
        keyStore.load(null)
        return keyStore
    }

}
