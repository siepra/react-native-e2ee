#import <React/RCTBridgeModule.h>

@interface RCT_EXTERN_MODULE(E2ee, NSObject)
RCT_EXTERN_METHOD(generateKeyPair:(RCTPromiseResolveBlock)resolve
                  reject:(RCTPromiseRejectBlock)reject)
@end
