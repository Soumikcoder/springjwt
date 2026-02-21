```bash
keytool -genkeypair -alias jwt -keyalg EC -groupname secp256r1 -keystore jwt-keystore.p12 -storetype PKCS12 -storepass password -validity 3650 ```