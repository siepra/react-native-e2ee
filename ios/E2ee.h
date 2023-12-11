#ifdef __cplusplus
#import "react-native-e2ee.h"
#endif

#ifdef RCT_NEW_ARCH_ENABLED
#import "RNE2eeSpec.h"

@interface E2ee : NSObject <NativeE2eeSpec>
#else
#import <React/RCTBridgeModule.h>

@interface E2ee : NSObject <RCTBridgeModule>
#endif

@end
