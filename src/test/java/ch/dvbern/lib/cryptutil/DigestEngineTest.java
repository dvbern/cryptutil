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

import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.util.Locale;

import org.junit.jupiter.api.Test;

import ch.dvbern.lib.cryptutil.annotations.NonNull;

import jakarta.xml.bind.DatatypeConverter;

import static ch.dvbern.lib.cryptutil.TestingUtil.resourceURL;
import static org.junit.jupiter.api.Assertions.assertEquals;

class DigestEngineTest {
	private static final String SHA512_SUM =
			"d2ef23b350507c87c09c27661ed5d95b173862c5ebe3fd385946d720f9fa8d7839885ea0c5462a09983ddee2ea2fa7bb03cc64cba51ac5f9977c577588edaed8";
	private static final String SHA256_SUM = "61c150be017422affe371afa1d94a15f4a1f086f094eea42abbe8141f0053d94";
	private final URL inputFile = resourceURL("test-input.jpg");

	@Test
	public void test_digestSHA256() throws IOException, DigestFailedException {
		try (InputStream is = inputFile.openStream()) {
			@NonNull byte[] digestBytes = new DigestEngine().digestSHA256(is, null);
			String hexText = DatatypeConverter.printHexBinary(digestBytes).toLowerCase(Locale.US);
			assertEquals(SHA256_SUM, hexText);
		}
	}

	@Test
	public void test_digestSHA512() throws IOException, DigestFailedException {
		try (InputStream is = inputFile.openStream()) {
			@NonNull byte[] digestBytes = new DigestEngine().digestSHA512(is, null);
			String hexText = DatatypeConverter.printHexBinary(digestBytes).toLowerCase(Locale.US);
			assertEquals(SHA512_SUM, hexText);
		}
	}

}
