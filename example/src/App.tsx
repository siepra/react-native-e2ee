import * as React from 'react';

import { StyleSheet, View, Button, Text, TextInput, Alert } from 'react-native';
import Clipboard from '@react-native-community/clipboard';

import {
  decryptMessage,
  encryptMessage,
  generateKeyPair,
} from 'react-native-e2ee';

export default function App() {
  const [publicKey, setPublicKey] = React.useState<string>('');
  const [inputValue, setInputValue] = React.useState<string>('');
  const [encryptedMessage, setEncryptedMessage] = React.useState<string>('');

  React.useEffect(() => {
    generateKeyPair().then((publicKey) => {
      setPublicKey(publicKey);
    });
  }, [setPublicKey]);

  const printPublicKey = React.useCallback(() => {
    Alert.alert('Public Key', publicKey, [
      {
        text: 'Copy to Clipboard',
        onPress: () => Clipboard.setString(publicKey),
      },
      {
        text: 'Close',
        onPress: () => {},
        style: 'cancel',
      },
    ]);
  }, [publicKey]);

  const handleEncryption = React.useCallback(async () => {
    const encrypted = await encryptMessage(inputValue, publicKey);

    if (!encrypted) {
      console.error('Message encryption failed');
      return;
    }

    setEncryptedMessage(encrypted);

    Alert.alert('Encrypted Message', encrypted, [
      {
        text: 'Copy to Clipboard',
        onPress: () => Clipboard.setString(encrypted),
      },
      {
        text: 'Close',
        onPress: () => {},
        style: 'cancel',
      },
    ]);
  }, [inputValue, publicKey, setEncryptedMessage]);

  const handleDecryption = React.useCallback(async () => {
    const decrypted = await decryptMessage(encryptedMessage);

    if (!decrypted) {
      console.error('Message decryption failed');
      return;
    }

    Alert.alert('Decrypted Message', decrypted, [
      {
        text: 'Close',
        onPress: () => {},
        style: 'cancel',
      },
    ]);
  }, [encryptedMessage]);

  return (
    <View style={styles.container}>
      <View style={styles.wrapper}>
        <Text style={styles.label}>Enter message:</Text>
        <TextInput
          value={inputValue}
          onChangeText={setInputValue}
          placeholder="Enter some text"
          style={styles.input}
        />
      </View>
      <Button title="Encrypt" onPress={handleEncryption} />
      <Button title="Decrypt" onPress={handleDecryption} />
      <Button title="Print my public key" onPress={printPublicKey} />
    </View>
  );
}

const styles = StyleSheet.create({
  container: {
    flex: 1,
    alignItems: 'center',
    justifyContent: 'center',
  },
  wrapper: {
    width: '80%',
  },
  label: {
    alignSelf: 'flex-start',
    fontSize: 16,
    marginBottom: 5,
  },
  input: {
    height: 40,
    borderColor: 'gray',
    borderWidth: 1,
    width: '100%',
    marginBottom: 20,
    padding: 10,
  },
});
