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

import java.io.InputStream;
import java.security.cert.CertificateException;
import java.security.cert.CertificateFactory;
import java.security.cert.X509Certificate;

import ch.dvbern.lib.cryptutil.annotations.NonNull;

import static java.util.Objects.requireNonNull;

public final class X509PEM {

	public @NonNull X509Certificate read(@NonNull InputStream certificate) {
		requireNonNull(certificate);

		try {
			CertificateFactory fact = CertificateFactory.getInstance("X.509");
			X509Certificate cer = (X509Certificate) fact.generateCertificate(certificate);
			return cer;
		} catch (CertificateException e) {
			throw new IllegalStateException("Could not build CertificateFactory for X.509???", e);
		}
	}

}
