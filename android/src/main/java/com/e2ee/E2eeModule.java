package com.e2ee;

import androidx.annotation.NonNull;

import com.facebook.react.bridge.Promise;
import com.facebook.react.bridge.ReactApplicationContext;
import com.facebook.react.bridge.ReactContextBaseJavaModule;
import com.facebook.react.bridge.ReactMethod;
import com.facebook.react.module.annotations.ReactModule;

import com.e2ee.encryption.EncryptionManager;


@ReactModule(name = E2eeModule.NAME)
public class E2eeModule extends ReactContextBaseJavaModule {
  public static final String NAME = "E2ee";

  public E2eeModule(ReactApplicationContext reactContext) {
    super(reactContext);
  }

  @Override
  @NonNull
  public String getName() {
    return NAME;
  }

  static {
    System.loadLibrary("cpp");
  }

  EncryptionManager encryptionManager = new EncryptionManager(getReactApplicationContext());

  private static native double nativeMultiply(double a, double b);

  // Example method
  // See https://reactnative.dev/docs/native-modules-android
  @ReactMethod
  public void multiply(double a, double b, Promise promise) {
    promise.resolve(nativeMultiply(a, b));
  }

  @ReactMethod
  public void generateKeyPair(Promise promise) {
    encryptionManager.createMasterKey();
    String publicKey = encryptionManager.getMyPublicKeyString();
    promise.resolve(publicKey);
  }

  @ReactMethod
  public void getOwnPublicKey(Promise promise) {
    String publicKey = encryptionManager.getMyPublicKeyString();
    promise.resolve(publicKey);
  }

  @ReactMethod
  public void encryptMessage(String message, String publicKey, Promise promise) {
    String encrypted = encryptionManager.encryptOthers(message, publicKey);
    promise.resolve(encrypted);
  }

  @ReactMethod
  public void decryptMessage(String message, Promise promise) {
    String decrypted = encryptionManager.decrypt(message);
    promise.resolve(decrypted);
  }
}
