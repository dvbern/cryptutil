/*
 * Copyright (C) 2017 DV Bern AG, Switzerland
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package ch.dvbern.lib.cryptutil.fileformats;

import java.net.URL;
import java.security.interfaces.RSAPrivateKey;
import java.security.interfaces.RSAPublicKey;

import ch.dvbern.lib.cryptutil.annotations.NonNull;
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