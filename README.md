# CryptUtil
This Java library provides simple-to-use routines for everyday cryptography tasks.

It does target limited environments (such as for use in an app updater) and as such tries to
minimize dependencies (currently: JDK8 only).

Currently implemented utilities:
* Read keys/certificates stored in PKCS8 format, PEM encoded, optionally protected with password
* Create digests (checksums) using strong cryptographic algorithms (like SHA256/SHA512)
* Sign files/digests using strong cryptographic algorithms (like SHA256/SHA256 with RSA)
* verify these signatures


# Usage

<What things you need to install and how to install them>

## Maven-Dependency

```xml
<dependency>
	<groupId>ch.dvbern.oss.cryptutil</groupId>
	<artifactId>cryptutil</artifactId>
	<version>${cryptutil.version}</version>
</dependency>
```

## HowTo

### Create a cryptographically strong digest/checksum using [DigestEngine](src/main/java/ch/dvbern/cryptutil/DigestEngine.java)
```java
byte[] digestBytes = new DigestEngine().digestSHA256(is, null);
// openssl compatible: hex-encoding, lowercase
String hexText = DatatypeConverter.printHexBinary(digestBytes).toLowerCase(Locale.US);
```

### Read Keys/Certificates stored in a PKCS#8 PEM file using [PKCS8PEM](src/main/java/ch/dvbern/cryptutil/fileformats/PKCS8PEM.java)
```java
// read public key/certificate
RSAPublicKey publicKey = new PKCS8PEM().readCertFromPKCS8EncodedPEM(privateKeyURL.openStream());

// read password protected private key
RSAPrivateKey privateKey = new PKCS8PEM().readKeyFromPKCS8EncodedPEM(privateKeyURL.openStream(), "asdffdsa");
```

### Create a cryptographically strong file signature on a digest and verify it using [SignatureEngine](src/main/java/ch/dvbern/cryptutil/SignatureEngine.java)
```java
// read private key
RSAPrivateKey privateKey = ... see example above

// create digest
byte digestBytes[] = ... see example above
ByteArrayInputStream digestByteStream = new ByteArrayInputStream(digestBytes);

// sign digest
@NonNull byte[] signatureBytes = new SignatureEngine().signSHA256RSA(privateKey, digestByteStream, null);
writeToFile(signatureBytes, "path/to/signed-digest.bin");
```

```java
// verify
RSAPublicKey publicKey = ... seeeExample above

byte digestBytes[] = ... see DigestEngine example above
InputStream digestByteStream = new ByteArrayInputStream(digestBytes);

byte signatureBytes[] = readFromFile("path/to/signed-digest.bin");
@NonNull boolean verified = new SignatureEngine().verifySHA256RSA(publicKey, digestByteStream, signatureBytes, null);

```


## Useful information
The following information is also used to generate data for use in unit tests.

### Generate an keypair using [OpenSSL]
First, create the RSA private key using 4096 bits (do not omit the password!):
```
openssl genrsa -out mykey.pem 4096
```

Java (i.e.: this library) needs the certificate in PKCS#8 format, so convert it:
```
openssl pkcs8 -topk8 -inform PEM -in mykey.pem -out mykey-pkcs8.pem
```                       

Now generate a public key:
```
openssl rsa -in meykey.pem -pubout > mykey.pub
```

### Generate signed digests and verify them using [OpenSSL]
Taken from: https://stackoverflow.com/questions/5140425/openssl-command-line-to-verify-the-signature

Generated a RSA signed SHA256 digest using our private key:
```
openssl dgst -sha256 -sign mykey.pem -out foo.txt.sha256.signed foo.txt 
```

Verify this digest using our public key:
```
openssl dgst -sha256 -verify mykey.pub -signature foo.txt.sha256.signed foo.txt
=> Verified OK
```

# Built With
* [Maven](https://maven.apache.org/) - Dependency Management
* Love :)

# Contributing Guidelines

Please read [CONTRIBUTING.md](CONTRIBUTING.md) for the process for submitting pull requests to us.

# Code of Conduct

One healthy social atmospehere is very important to us, wherefore we rate our Code of Conduct high. For details check
 the file <CODE_OF_CONDUCT.md>

# Authors

* **DV Bern AG**, Christoph Linder - *Initial work* - [dvbern](https://github.com/dvbern)

See also the list of [contributors](https://github.com/dvbern/cryptutil/contributors) who participated in this project.

## License

This project is licensed under the Apache 2.0 License - see the [LICENSE.md](LICENSE.md) file for details.



<Link-Reference>
[OpenSSL]: https://www.openssl.org/
