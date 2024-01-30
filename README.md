<div align='center'>
  <h1>React Native E2E Encryption (WIP)</h1>
  <p>Handling cryptography easily with JS interfaces.</P>
</div>


## Description
This projects addresses the purpose of providing a developer friendly JS interface for generating, safely storing and serving cryptographic keys for end-to-end encryption on Android and iOS devices.
Of course, the server (or another handy way of distributing data between devices) is needed, but the point of this package is to provide a developer with clients' public key and all the necessary methods to use in end-to-end encrypted communication without forcing him/her to dive into native implementations for generating and storing cryptohgraphic data.


## Security
The project encrypts with RSA (Ultimately it'll use hybrid encryption with RSA and AES).

## Installation

Open a Terminal in your project's folder and run:

#### Using `yarn`

```sh
yarn add react-native-e2ee
```

#### Using `npm`

```sh
npm install react-native-e2ee
```


## Usage/API

* `generateKeyPair(): Promise<string>` - generates/fetches key pair for fixed alias and returns the public key.
* `getOwnPublicKey(): Promise<string | null>` - returns own public key or null if non existing.
* `encryptMessage(
  message: string,
  publicKey: string
): Promise<string | null>` - encrypts message for the given public key.
* `decryptMessage(message: string): Promise<string | null>` - decrypts message using own public key.


## Key progress so far
* Full API coverage for <b>Android</b>
* Full API coverage for <b>iOS</b>


## Roadmap
Examine the upcoming changes [here](https://github.com/siepra/react-native-e2ee/issues/1).


## Contributing

See the [contributing guide](CONTRIBUTING.md) to learn how to contribute to the repository and the development workflow.

## License

MIT

---

Made with [create-react-native-library](https://github.com/callstack/react-native-builder-bob)
