# About
This library is intended to simple-to-use routines for everyday cryptography tasks.

The library does target limited environments (such as for use in an app update) and as such tries to
minimize dependencies (currently: JDK8 only).

Currently implemented utilities:
* Read keys/certificates stored in PKCS8 format, PEM encoded, optionally protected with password
* create digests (checksums) using strong cryptographic algorithms (like SHA256/SHA512)
* sign files/checksums using strong cryptographic algorithms (like SHA256/SHA256 with RSA)
* verify these checksums


# Usage

<What things you need to install and how to install them>

## Dependency

1. Add the following dependencies to your project:
```
<dependency>
	<groupId>ch.dvbern.oss.cdipersistenceapi</groupId>
	<artifactId>cdi-persistence-api</artifactId>
	<version>1.0</version>
</dependency>
<dependency>
	<groupId>ch.dvbern.oss.cdipersistencetest</groupId>
	<artifactId>cdi-persistence-test</artifactId>
	<version>1.0</version>
	<scope>test</scope>
</dependency>
