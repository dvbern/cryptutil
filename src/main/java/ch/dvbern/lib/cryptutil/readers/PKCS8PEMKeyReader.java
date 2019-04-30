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

package ch.dvbern.lib.cryptutil.readers;

import java.io.InputStream;
import java.security.interfaces.RSAPrivateKey;

import ch.dvbern.lib.cryptutil.annotations.NonNull;
import ch.dvbern.lib.cryptutil.annotations.Nullable;
import ch.dvbern.lib.cryptutil.fileformats.PKCS8PEM;

import static java.util.Objects.requireNonNull;

public class PKCS8PEMKeyReader implements KeyReader<RSAPrivateKey> {

	private final @NonNull PKCS8PEM pkcs8PEM = new PKCS8PEM();
	private final @NonNull InputStream privateKey;
	private final @Nullable String keyPassword;

	/**
	 * @param privateKey ownership is not taken, caller needs to close the stream
	 */
	public PKCS8PEMKeyReader(@NonNull InputStream privateKey, @Nullable String keyPassword) {
		this.privateKey = requireNonNull(privateKey);
		this.keyPassword = keyPassword;
	}

	@Override
	public @NonNull RSAPrivateKey readPrivateKey() {
		try {
			return pkcs8PEM.readKeyFromPKCS8EncodedPEM(privateKey, keyPassword);
		} catch (Exception e) {
			throw new ReaderException("Could not read PKCS8EncodedPEM", e);
		}
	}

}
