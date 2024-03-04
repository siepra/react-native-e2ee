import type { TurboModule } from 'react-native/Libraries/TurboModule/RCTExport';
import { TurboModuleRegistry } from 'react-native';

export interface Spec extends TurboModule {
  generateKeyPair: () => string | null;
  getOwnPublicKey: () => string | null;
  encrypt: (message: string, publicKey: string) => string | null;
  decrypt: (message: string) => string | null;
}

export default TurboModuleRegistry.get<Spec>('RNE2ee') as Spec | null;
