import { TurboModuleRegistry } from 'react-native';

const NativeE2ee = TurboModuleRegistry.get('RNE2ee');

export function generateKeyPair(): string | null {
  // @ts-expect-error
  return NativeE2ee.generateKeyPair();
}

export function getOwnPublicKey(): string | null {
  // @ts-expect-error
  return NativeE2ee.getOwnPublicKey();
}

export function encryptMessage(
  message: string,
  publicKey: string
): string | null {
  // @ts-expect-error
  return NativeE2ee.encrypt(message, publicKey);
}

export function decryptMessage(message: string): string | null {
  // @ts-expect-error
  return NativeE2ee.decrypt(message);
}
