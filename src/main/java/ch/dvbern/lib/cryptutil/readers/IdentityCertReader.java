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

import java.security.PublicKey;

import ch.dvbern.lib.cryptutil.annotations.NonNull;

import static java.util.Objects.requireNonNull;

public class IdentityCertReader<T extends PublicKey> implements CertReader<T> {
	private final @NonNull T cert;

	public IdentityCertReader(@NonNull T cert) {
		this.cert = requireNonNull(cert);
	}

	@Override
	public @NonNull T readPublicKey() throws ReaderException {
		return cert;
	}
}
