import Foundation

public typealias RCTPromiseResolveBlock = (Any?) -> Void
public typealias RCTPromiseRejectBlock = (String?, String?, NSError?) -> Void

@objc(E2ee)
class E2ee: NSObject {

    let RSAKeyManager = RSAKeyManager.shared

    @objc(generateKeyPair:reject:)
    func generateKeyPair(_ resolve: @escaping RCTPromiseResolveBlock, reject: @escaping RCTPromiseRejectBlock) {
        let publicKey = RSAKeyManager.generateKeyPair()
        resolve(publicKey)
    }
}
