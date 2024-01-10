import Foundation

public typealias RCTPromiseResolveBlock = (Any?) -> Void
public typealias RCTPromiseRejectBlock = (String?, String?, NSError?) -> Void

@objc(E2ee)
class E2ee: NSObject {
    @objc(generateKeyPair:reject:)
    func generateKeyPair(_ resolve: @escaping RCTPromiseResolveBlock, reject: @escaping RCTPromiseRejectBlock) {
        resolve("Successfully generated key pair in Swift!")
    }
}
