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

package ch.dvbern.lib.cryptutil;

import java.io.InputStream;
import java.net.URL;
import java.security.PrivateKey;
import java.security.PublicKey;

import ch.dvbern.lib.cryptutil.annotations.NonNull;
import ch.dvbern.lib.cryptutil.fileformats.PKCS8PEM;
import org.junit.jupiter.api.Test;

import static ch.dvbern.lib.cryptutil.TestingUtil.readFully;
import static ch.dvbern.lib.cryptutil.TestingUtil.resourceStream;
import static ch.dvbern.lib.cryptutil.TestingUtil.resourceURL;
import static java.util.Objects.requireNonNull;
import static org.junit.jupiter.api.Assertions.assertArrayEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

class SignatureEngineTest {
	private final URL inputFile = resourceURL("test-input.jpg");

	private PrivateKey pk = null;
	private PublicKey pub = null;

	@Test
	void supportsSHA256RSA() {
		// FIXME: verify this from the docs
		// as per spec, every JDK must impmenent this!
		assertTrue(new SignatureEngine().supportsSHA256RSA(null));
	}

	@Test
	void supportsSHA512RSA() {
		// FIXME: verify this from the docs
		// as per spec, every JDK must impmenent this!
		assertTrue(new SignatureEngine().supportsSHA512RSA(null));
	}

	@Test
	void signSHA256RSA() throws Exception {
		givenRSAKeyPair();

		try (InputStream is = inputFile.openStream()) {
			@NonNull byte[] signatureBytes = new SignatureEngine().signSHA256RSA(pk, is, null);
			assertSignatureEqualsReference(signatureBytes, "signing/signed-by-openssl/sha256.dsig");
		}
	}

	@Test
	void signSHA256RSA_withPassword() throws Exception {
		givenRSAKeyPairWithPassword();

		try (InputStream is = inputFile.openStream()) {
			@NonNull byte[] signatureBytes = new SignatureEngine().signSHA256RSA(pk, is, null);
			assertSignatureEqualsReference(signatureBytes, "signing/signed-by-openssl/sha256-passasdffdsa.dsig");
		}
	}

	@Test
	void signSHA512RSA() throws Exception {
		givenRSAKeyPair();

		try (InputStream is = inputFile.openStream()) {
			@NonNull byte[] signatureBytes = new SignatureEngine().signSHA512RSA(pk, is, null);
			assertSignatureEqualsReference(signatureBytes, "signing/signed-by-openssl/sha512.dsig");
		}
	}

	@Test
	void signSHA512RSA_withPassword() throws Exception {
		givenRSAKeyPairWithPassword();

		try (InputStream is = inputFile.openStream()) {
			@NonNull byte[] signatureBytes = new SignatureEngine().signSHA512RSA(pk, is, null);
			assertSignatureEqualsReference(signatureBytes, "signing/signed-by-openssl/sha512-passasdffdsa.dsig");
		}
	}

	@Test
	void verifySHA256RSA() throws Exception {
		givenRSAKeyPair();

		byte[] reference = readFully(resourceURL("signing/signed-by-openssl/sha256.dsig"));

		try (InputStream is = inputFile.openStream()) {
			@NonNull boolean verified = new SignatureEngine().verifySHA256RSA(reference, pub, is, null);
			assertTrue(verified);
		}
	}

	@Test
	void verifySHA512RSA() throws Exception {
		givenRSAKeyPair();

		byte[] reference = readFully(resourceURL("signing/signed-by-openssl/sha512.dsig"));

		try (InputStream is = inputFile.openStream()) {
			@NonNull boolean verified = new SignatureEngine().verifySHA512RSA(reference, pub, is, null);
			assertTrue(verified);
		}
	}

	public void assertSignatureEqualsReference(@NonNull byte[] signatureBytes, @NonNull String referenceDsigpath) {
		requireNonNull(signatureBytes);
		requireNonNull(referenceDsigpath);

		URL referenceDsigURL = resourceURL(referenceDsigpath);
		@NonNull byte[] openSSLReference = readFully(referenceDsigURL);
		assertArrayEquals(openSSLReference, signatureBytes);
	}

	void givenRSAKeyPair() throws Exception {
		pk = new PKCS8PEM().readKeyFromPKCS8EncodedPEM(
				resourceStream("signing/testkey-nopass-pkcs8.pem"), null);
		pub = new PKCS8PEM().readCertFromPKCS8EncodedPEM(
				resourceStream("signing/testkey-nopass.pub"));
	}

	void givenRSAKeyPairWithPassword() throws Exception {
		pk = new PKCS8PEM().readKeyFromPKCS8EncodedPEM(
				resourceStream("signing/testkey-passasdffdsa-pkcs8.pem"), "asdffdsa".toCharArray());
		pub = new PKCS8PEM().readCertFromPKCS8EncodedPEM(
				resourceStream("signing/testkey-passasdffdsa.pub"));
	}
}