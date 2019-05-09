package ch.dvbern.lib.cryptutil.readers;

import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.security.interfaces.RSAPublicKey;

import org.junit.jupiter.api.Test;

import static ch.dvbern.lib.cryptutil.TestingUtil.resourceURL;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

public class PKCS8PEMCertReaderTest {

	@Test
	public void test_readPublicKey() throws IOException {
		URL publicKeyURL = resourceURL("signing/testkey-nopass.pub");
		try (InputStream publicKey = publicKeyURL.openStream()) {
			RSAPublicKey key = new PKCS8PEMCertReader(publicKey).readPublicKey();

			assertEquals("RSA", key.getAlgorithm());
			assertEquals("X.509", key.getFormat());
		}
	}

	@Test
	public void test_readPublicKeyInvalidPassword() throws IOException {
		URL publicKeyURL = resourceURL("signing/testkey-nopass.pem");
		try (InputStream publicKey = publicKeyURL.openStream()) {
			ReaderException thrown = assertThrows(
					ReaderException.class,
					() -> new PKCS8PEMCertReader(publicKey).readPublicKey());

			assertEquals("Could not read PKCS8EncodedPEM", thrown.getMessage());
		}
	}
}

