package ch.dvbern.lib.cryptutil.readers;

import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.security.interfaces.RSAPrivateKey;

import org.junit.jupiter.api.Test;

import static ch.dvbern.lib.cryptutil.TestingUtil.resourceURL;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

public class PKCS8PEMKeyReaderTest {

	@Test
	public void test_readPrivateKey() throws IOException {
		URL privateKeyURL = resourceURL("signing/testkey-nopass-pkcs8.pem");
		try (InputStream privateKey = privateKeyURL.openStream()) {
			RSAPrivateKey key = new PKCS8PEMKeyReader(privateKey, null).readPrivateKey();

			assertEquals("RSA", key.getAlgorithm());
			assertEquals("PKCS#8", key.getFormat());
		}
	}

	@Test
	public void test_readPrivateKeyInvalidPassword() throws IOException {
		URL privateKeyURL = resourceURL("signing/testkey-nopass-pkcs8.pem");
		try (InputStream privateKey = privateKeyURL.openStream()) {
			ReaderException thrown = assertThrows(
					ReaderException.class,
					() -> new PKCS8PEMKeyReader(privateKey, "foo".toCharArray()).readPrivateKey()
			);

			assertEquals("Could not read PKCS8EncodedPEM", thrown.getMessage());
		}
	}
}

