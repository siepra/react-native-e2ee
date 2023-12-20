import android.content.Context

import org.junit.Test
import org.junit.Assert.assertEquals
import org.junit.Assert.assertNull

import androidx.test.platform.app.InstrumentationRegistry

import com.e2ee.encryption.EncryptionManager
import org.junit.BeforeClass
import java.security.KeyPair


class EncryptionTest {

    companion object {

        private lateinit var encryptionManager: EncryptionManager
        private var keyPair: KeyPair? = null

        @JvmStatic
        @BeforeClass
        fun setup(): Unit {
            val context: Context = InstrumentationRegistry.getInstrumentation().context
            encryptionManager = EncryptionManager(context)

            // Clear up the key store
            encryptionManager.removeMasterKey()

            // Re-generate key pair
            keyPair = encryptionManager.createMasterKey()
        }
    }

    @Test
    fun generates_key_pair() {
        val publicKey = encryptionManager.getMyPublicKey()
        assertEquals(publicKey, keyPair?.public)
    }

    @Test
    fun decrypts_message() {
        val message = "Hello World!"
        val encrypted = encryptionManager.encrypt(message) ?: return
        val decrypted = encryptionManager.decrypt(encrypted)
        assertEquals(message, decrypted)
    }

    @Test
    fun returns_null_if_invalid_public_key() {
        val message = "Hello World!"
        val encrypted = encryptionManager.encryptOthers(message, "publicKey")
        assertNull(encrypted)
    }
}