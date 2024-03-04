#import "RNE2eeSpec.h"

#import "SwiftProtocol.h"

using namespace facebook::react;

@interface RNE2ee : NSObject <NativeE2eeSpec>
@end

@implementation RNE2ee {
    id<SwiftProtocol> _swiftClass;
}

- (instancetype)init {
    self = [super init];
    if (self) {
        _swiftClass = [[NSClassFromString(@"E2ee") alloc] init];
    }
    return self;
}

RCT_EXPORT_MODULE()

- (NSString * _Nullable)generateKeyPair {
    return [_swiftClass generateKeyPair];
}

- (NSString * _Nullable)getOwnPublicKey {
    return [_swiftClass getOwnPublicKey];
}

- (NSString * _Nullable)decrypt:(NSString *)message {
    return [_swiftClass decrypt:message];
}

- (NSString * _Nullable)encrypt:(NSString *)message publicKey:(NSString *)publicKey {
    return [_swiftClass encrypt:publicKey message:message];
}

- (std::shared_ptr<facebook::react::TurboModule>)getTurboModule:(const facebook::react::ObjCTurboModule::InitParams &)params {
    return std::make_shared<facebook::react::NativeE2eeSpecJSI>(params);
}

@end
