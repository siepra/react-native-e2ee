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
  const [encryptedMessage, setEncryptedMessage] = React.useState<
    string | null
  >();
  const [error, setError] = React.useState<string | null>(null);

  React.useEffect(() => {
    generateKeyPair().then((publicKey) => {
      setPublicKey(publicKey);
    });
  }, [setPublicKey]);

  const handleInputChange = React.useCallback(
    (text: string) => {
      setError(null);
      setEncryptedMessage(null);
      setInputValue(text);
    },
    [setInputValue, setEncryptedMessage, setError]
  );

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
    if (inputValue.length === 0) {
      setError('Input is empty');
      return;
    }

    setError(null);

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
  }, [inputValue, publicKey, setEncryptedMessage, setError]);

  const handleDecryption = React.useCallback(async () => {
    if (!encryptedMessage) {
      setError('Encrypt message first');
      return;
    }

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
  }, [encryptedMessage, setError]);

  return (
    <View style={styles.container}>
      <View style={styles.wrapper}>
        <Text style={styles.label}>Enter message:</Text>
        <TextInput
          value={inputValue}
          onChangeText={handleInputChange}
          placeholder="Enter some text"
          style={styles.input}
        />
        {error ? <Text style={styles.error}>{error}</Text> : null}
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
    marginBottom: 20,
  },
  label: {
    alignSelf: 'flex-start',
    fontSize: 16,
    marginBottom: 5,
  },
  error: {
    color: 'red',
  },
  input: {
    height: 40,
    borderColor: 'gray',
    borderWidth: 1,
    width: '100%',
    marginBottom: 5,
    padding: 10,
  },
});
