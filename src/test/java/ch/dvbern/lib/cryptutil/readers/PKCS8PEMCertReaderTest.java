package ch.dvbern.lib.cryptutil.readers;

import java.io.IOException;
import java.net.URL;
import java.security.interfaces.RSAPublicKey;

import org.junit.jupiter.api.Test;

import static ch.dvbern.lib.cryptutil.TestingUtil.resourceURL;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

public class PKCS8PEMCertReaderTest {

	@Test
	public void test_readPublicKey() throws IOException {
		final URL publicKeyURL = resourceURL("signing/testkey-nopass.pub");

		final RSAPublicKey key = new PKCS8PEMCertReader(publicKeyURL.openStream()).readPublicKey();

		assertEquals("RSA", key.getAlgorithm());
		assertEquals("X.509", key.getFormat());
	}

	@Test
	public void test_readPublicKeyInvalidPassword() {
		final URL publicKeyURL = resourceURL("signing/testkey-nopass.pem");

		final ReaderException thrown = assertThrows(
				ReaderException.class,
				() -> new PKCS8PEMCertReader(publicKeyURL.openStream()).readPublicKey());

		assertEquals("Could not read PKCS8EncodedPEM", thrown.getMessage());
	}
}

