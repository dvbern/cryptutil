package ch.dvbern.lib.cryptutil.readers;

import java.io.IOException;
import java.net.URL;
import java.security.PrivateKey;
import java.security.interfaces.RSAPrivateKey;

import org.junit.jupiter.api.Test;

import static ch.dvbern.lib.cryptutil.TestingUtil.resourceURL;
import static org.junit.jupiter.api.Assertions.assertEquals;

public class IdentityKeyReaderTest {

	@Test
	public void test_readPrivateKey() throws IOException {
		final URL privateKeyURL = resourceURL("signing/testkey-nopass-pkcs8.pem");
		final RSAPrivateKey rsaKey = new PKCS8PEMKeyReader(privateKeyURL.openStream(), null).readPrivateKey();

		final PrivateKey key = new IdentityKeyReader(rsaKey).readPrivateKey();

		assertEquals("RSA", key.getAlgorithm());
		assertEquals("PKCS#8", key.getFormat());
	}
}

