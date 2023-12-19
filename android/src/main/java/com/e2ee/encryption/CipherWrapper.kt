/**
 * Thanks to Nipun Ruwanpathirana for sharing the core methods for e2ee on Android
 * (https://nipunr.medium.com/end-to-end-encryption-ios-android-rsa-65cfd015184a)
 */

import android.util.Base64
import android.util.Log
import com.e2ee.encryption.EncryptionManager
import java.io.ByteArrayOutputStream
import java.security.Key
import javax.crypto.Cipher

class CipherWrapper(private val transformation: String) {
    fun encrypt(message: String, key: Key?): String? {
        try {
            val cipher: Cipher = Cipher.getInstance(transformation)
            cipher.init(Cipher.ENCRYPT_MODE, key)

            val messageData = message.toByteArray(Charsets.UTF_8)
            var limit: Int = (key?.encoded?.size)?.minus(62) ?: 128

            var position = 0
            val byteArrayOutputStream = ByteArrayOutputStream()

            while (position < messageData.size) {
                if (messageData.size - position < limit)
                    limit = messageData.size - position
                val data = cipher.doFinal(messageData, position, limit)
                byteArrayOutputStream.write(data)
                position += limit
            }
            val enc = Base64.encodeToString(byteArrayOutputStream.toByteArray(), Base64.NO_WRAP)
            byteArrayOutputStream.flush()
            byteArrayOutputStream.close()
            return enc
        } catch (e: Exception) {
            Log.e("CharWrapper", e.localizedMessage?: "StringEncrypt")
            return null
        }
    }

    fun encrypt(messageData: ByteArray, key: Key?): ByteArray? {
        try {
            val cipher: Cipher = Cipher.getInstance(transformation)
            cipher.init(Cipher.ENCRYPT_MODE, key)

            var limit: Int = (key?.encoded?.size)?.minus(62) ?: 128
            var position = 0
            val byteArrayOutputStream = ByteArrayOutputStream()

            while (position < messageData.size) {
                if (messageData.size - position < limit)
                    limit = messageData.size - position
                val data = cipher.doFinal(messageData, position, limit)
                byteArrayOutputStream.write(data)
                position += limit
            }
            val enc = byteArrayOutputStream.toByteArray()
            byteArrayOutputStream.flush()
            byteArrayOutputStream.close()
            return enc
        } catch (e: Exception) {
            Log.e("CharWrapper", e.localizedMessage?: "ByteEncrypt")
            return null
        }
    }

    fun decrypt(message: String, key: Key?): String? {
        try {
            val cipher: Cipher = Cipher.getInstance(transformation)
            cipher.init(Cipher.DECRYPT_MODE, key)
            val encryptedData = Base64.decode(message.toByteArray(Charsets.UTF_8), Base64.NO_WRAP)

            var limit: Int = EncryptionManager.KEY_SIZE / 8
            var position = 0
            val byteArrayOutputStream = ByteArrayOutputStream()
            while (position < encryptedData.size) {
                if (encryptedData.size - position < limit)
                    limit = encryptedData.size - position
                val data = cipher.doFinal(encryptedData, position, limit)
                byteArrayOutputStream.write(data)
                position += limit
            }
            val dec = byteArrayOutputStream.toString(Charsets.UTF_8.name())
            byteArrayOutputStream.flush()
            byteArrayOutputStream.close()
            return dec
        } catch (e: Exception) {
            Log.e("CharWrapper", e.localizedMessage?: "StringDecrypt")
            return null
        }
    }

    fun decrypt(data: ByteArray, key: Key?): ByteArray? {
        try {
            val cipher: Cipher = Cipher.getInstance(transformation)
            cipher.init(Cipher.DECRYPT_MODE, key)

            var limit: Int = EncryptionManager.KEY_SIZE / 8
            var position = 0
            val byteArrayOutputStream = ByteArrayOutputStream()
            while (position < data.size) {
                if (data.size - position < limit)
                    limit = data.size - position
                val data = cipher.doFinal(data, position, limit)
                byteArrayOutputStream.write(data)
                position += limit
            }
            val dec = byteArrayOutputStream.toByteArray()
            byteArrayOutputStream.flush()
            byteArrayOutputStream.close()
            return dec
        } catch (e: Exception) {
            Log.e("CharWrapper", e.localizedMessage?: "ByteDecrypt")
            return null
        }
    }
}
