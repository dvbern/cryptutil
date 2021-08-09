package ch.dvbern.lib.cryptutil.readers;

import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.security.PublicKey;
import java.security.interfaces.RSAPublicKey;

import org.junit.jupiter.api.Test;

import static ch.dvbern.lib.cryptutil.TestingUtil.resourceURL;
import static org.junit.jupiter.api.Assertions.assertEquals;

public class IdentityCertReaderTest {

	@Test
	public void test_readPublicKey() throws IOException {
		URL publicKeyURL = resourceURL("signing/testkey-nopass.pub");
		try (InputStream publicKey = publicKeyURL.openStream()) {
			RSAPublicKey rsaKey = new PKCS8PEMCertReader(publicKey).readPublicKey();

			PublicKey key = new IdentityCertReader<>(rsaKey).readPublicKey();
			assertEquals("RSA", key.getAlgorithm());
			assertEquals("X.509", key.getFormat());

		}
	}
}

