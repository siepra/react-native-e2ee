import org.junit.Test
import org.junit.Assert.assertEquals

import androidx.test.platform.app.InstrumentationRegistry

class EncryptionTest {

    val context = InstrumentationRegistry.getInstrumentation().context

    @Test
    fun generates_key_pair() {
        val encryptionManager = EncryptionManager(context)
        val keyPair = encryptionManager.createMasterKey()

        val publicKey = encryptionManager.getMyPublicKey()

        assertEquals(publicKey, keyPair?.public)
    }
}