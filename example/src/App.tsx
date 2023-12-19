import * as React from 'react';

import { StyleSheet, View, Text } from 'react-native';
import { generateKeyPair } from 'react-native-e2ee';

export default function App() {
  const [result, setResult] = React.useState<string | undefined>();

  React.useEffect(() => {
    generateKeyPair().then(setResult);
  }, []);

  return (
    <View style={styles.container}>
      <Text>Public key (Base64): {result}</Text>
    </View>
  );
}

const styles = StyleSheet.create({
  container: {
    flex: 1,
    alignItems: 'center',
    justifyContent: 'center',
  },
  box: {
    width: 60,
    height: 60,
    marginVertical: 20,
  },
});
