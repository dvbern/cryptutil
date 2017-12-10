package ch.dvbern.lib.cryptutil.fileformats;

import java.net.URL;
import java.security.interfaces.RSAPrivateKey;
import java.security.interfaces.RSAPublicKey;

import ch.dvbern.lib.cryptutil.fixme.NonNull;
import org.junit.jupiter.api.Test;

import static ch.dvbern.lib.cryptutil.TestingUtil.resourceURL;
import static org.junit.jupiter.api.Assertions.assertNotNull;

class PKCS8PEMTest {
	@Test
	void readCertFromPKCS8EncodedPEM() throws Exception {
		URL privateKeyURL = resourceURL("signing/testkey-nopass.pub");
		@NonNull RSAPublicKey pk = new PKCS8PEM().readCertFromPKCS8EncodedPEM(privateKeyURL.openStream());
		assertNotNull(pk);
	}

	@Test
	void getPublicKeyFromPKCS8EncodedPEM() throws Exception {
		URL privateKeyURL = resourceURL("signing/testkey-nopass-pkcs8.pem");
		@NonNull RSAPrivateKey pk = new PKCS8PEM().readKeyFromPKCS8EncodedPEM(privateKeyURL.openStream(), null);
		assertNotNull(pk);
	}

	@Test
	void getPrivateKeyFromPKCS8EncodedPEM_withPassword() throws Exception {
		URL privateKeyURL = resourceURL("signing/testkey-passasdffdsa-pkcs8.pem");
		@NonNull RSAPrivateKey pk = new PKCS8PEM().readKeyFromPKCS8EncodedPEM(privateKeyURL.openStream(), "asdffdsa");
		assertNotNull(pk);
	}


}