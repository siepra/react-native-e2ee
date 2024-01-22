#import <React/RCTBridgeModule.h>

@interface RCT_EXTERN_MODULE(E2ee, NSObject)

RCT_EXTERN_METHOD(generateKeyPair:(RCTPromiseResolveBlock)resolve
                           reject:(RCTPromiseRejectBlock)reject
                  )

RCT_EXTERN_METHOD(getOwnPublicKey:(RCTPromiseResolveBlock)resolve
                           reject:(RCTPromiseRejectBlock)reject
                  )

RCT_EXTERN_METHOD(encrypt:(NSString)publicKey
                       message:(NSString)publicKey
                         resolve:(RCTPromiseResolveBlock)resolve
                          reject:(RCTPromiseRejectBlock)reject
                  )

RCT_EXTERN_METHOD(decrypt:(NSString)message
                         resolve:(RCTPromiseResolveBlock)resolve
                          reject:(RCTPromiseRejectBlock)reject
                  )
@end
