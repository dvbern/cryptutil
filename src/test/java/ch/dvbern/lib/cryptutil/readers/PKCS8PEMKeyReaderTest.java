package ch.dvbern.lib.cryptutil.readers;

import java.io.InputStream;
import java.io.IOException;
import java.net.URL;
import java.security.interfaces.RSAPrivateKey;

import org.junit.jupiter.api.Test;

import static ch.dvbern.lib.cryptutil.TestingUtil.resourceURL;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

public class PKCS8PEMKeyReaderTest {

	@Test
	public void test_readPrivateKey() throws IOException {
		final URL privateKeyURL = resourceURL("signing/testkey-nopass-pkcs8.pem");
		final InputStream privateKey = privateKeyURL.openStream();
		final RSAPrivateKey key = new PKCS8PEMKeyReader(privateKey, null).readPrivateKey();
		privateKey.close();

		assertEquals("RSA", key.getAlgorithm());
		assertEquals("PKCS#8", key.getFormat());
	}

	@Test
	public void test_readPrivateKeyInvalidPassword() throws IOException {
		final URL privateKeyURL = resourceURL("signing/testkey-nopass-pkcs8.pem");
		final InputStream privateKey = privateKeyURL.openStream();
		final ReaderException thrown = assertThrows(
				ReaderException.class,
				() -> new PKCS8PEMKeyReader(privateKey, "foo".toCharArray()).readPrivateKey());

		assertEquals("Could not read PKCS8EncodedPEM", thrown.getMessage());
		privateKey.close();
	}
}

