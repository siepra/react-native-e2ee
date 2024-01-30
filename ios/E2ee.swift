import UIKit
import SwiftyRSA

import Foundation

@objc(E2ee)
class E2ee: NSObject {

    let _RSAKeyManager = RSAKeyManager.shared

    @objc(generateKeyPair)
    func generateKeyPair() -> String? {
        guard let publicKey = _RSAKeyManager.generateKeyPair() else { return nil }
        return publicKey
    }
    
    @objc(getOwnPublicKey)
    func getOwnPublicKey() -> String? {
        guard let publicKey = _RSAKeyManager.getMyPublicKeyString() else { return nil }
        return publicKey
    }
    
    @objc(encrypt:message:)
    func encrypt(_ publicKey: String, message: String) -> String? {
        do {
            let clear = try ClearMessage(string: message, using: .utf8)
            
            let publicKeyObject = _RSAKeyManager.getPublicKey(pemEncoded: publicKey)!
            
            let encryptedMessage = try clear.encrypted(with: publicKeyObject, padding: .PKCS1)
            let encryptedMessageString = encryptedMessage.base64String
            
            return encryptedMessageString
        } catch let error {
            print("MESSAGE ENCRYPTION FAILED: \(error.localizedDescription)")
            return nil
        }
    }
    
    @objc(decrypt:)
    func decrypt(_ message: String) -> String? {
        do {
            let privateKey = RSAKeyManager.shared.getMyPrivateKey()!
            
            let encrypted = try EncryptedMessage(base64Encoded: message)
            let clear = try encrypted.decrypted(with: privateKey, padding: .PKCS1)
            let decrypted = try clear.string(encoding: .utf8)
            
            return decrypted
        } catch let error {
            print("MESSAGE DECRYPTION FAILED: \(error.localizedDescription)")
            return nil
        }
    }
}
