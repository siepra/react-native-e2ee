#import "RNE2eeSpec.h"
#import "RNE2ee.h"


@implementation RNE2ee

RCT_EXPORT_MODULE()

- (NSString * _Nullable)generateKeyPair {
    return nil;
}

- (NSString * _Nullable)getOwnPublicKey {
    return nil;
}

- (NSString * _Nullable)decrypt:(NSString *)message {
    return nil;
}

- (NSString * _Nullable)encrypt:(NSString *)message publicKey:(NSString *)publicKey {
    return nil;
}

- (std::shared_ptr<facebook::react::TurboModule>)getTurboModule:(const facebook::react::ObjCTurboModule::InitParams &)params {
    return std::make_shared<facebook::react::NativeE2eeSpecJSI>(params);
}

@end
