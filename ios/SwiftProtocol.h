#import <Foundation/Foundation.h>

@protocol SwiftProtocol <NSObject>

- (NSString * _Nullable)generateKeyPair;

- (NSString * _Nullable)getOwnPublicKey;

- (NSString * _Nullable)decrypt:(NSString *_Nonnull)message;

- (NSString * _Nullable)encrypt:(NSString *_Nonnull)publicKey message:(NSString *_Nonnull)message;

@end
