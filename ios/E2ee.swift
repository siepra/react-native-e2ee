import UIKit
import SwiftyRSA

import Foundation

public typealias RCTPromiseResolveBlock = (Any?) -> Void
public typealias RCTPromiseRejectBlock = (String?, String?, NSError?) -> Void

@objc(E2ee)
class E2ee: NSObject {

    let _RSAKeyManager = RSAKeyManager.shared

    @objc(generateKeyPair:reject:)
    func generateKeyPair(_ resolve: @escaping RCTPromiseResolveBlock, reject: @escaping RCTPromiseRejectBlock) {
        let publicKey = _RSAKeyManager.generateKeyPair()
        resolve(publicKey)
    }
    
    @objc(getOwnPublicKey:reject:)
    func getOwnPublicKey(_ resolve: @escaping RCTPromiseResolveBlock, reject: @escaping RCTPromiseRejectBlock) {
        let publicKey = _RSAKeyManager.getMyPublicKeyString()
        resolve(publicKey)
    }
    
    @objc(encrypt:message:resolve:reject:)
    func encrypt(_ publicKey: String, message: String, resolve: @escaping RCTPromiseResolveBlock, reject: @escaping RCTPromiseRejectBlock) {
        do {
            let clear = try ClearMessage(string: message, using: .utf8)
            
            let publicKeyObject = _RSAKeyManager.getPublicKey(pemEncoded: publicKey)!
            
            let encryptedMessage = try clear.encrypted(with: publicKeyObject, padding: .PKCS1)
            let encryptedMessageString = encryptedMessage.base64String
            
            resolve(encryptedMessageString)
        } catch let error {
            print("MESSAGE ENCRYPTION FAILED: \(error.localizedDescription)")
            resolve(nil)
        }
    }
    
    @objc(decrypt:resolve:reject:)
    func decrypt(_ message: String, resolve: @escaping RCTPromiseResolveBlock, reject: @escaping RCTPromiseRejectBlock) {
        do {
            let privateKey = RSAKeyManager.shared.getMyPrivateKey()!
            
            let encrypted = try EncryptedMessage(base64Encoded: message)
            let clear = try encrypted.decrypted(with: privateKey, padding: .PKCS1)
            let decrypted = try clear.string(encoding: .utf8)
            
            resolve(decrypted)
        } catch let error {
            print("MESSAGE DECRYPTION FAILED: \(error.localizedDescription)")
            resolve(nil)
        }
    }
}
