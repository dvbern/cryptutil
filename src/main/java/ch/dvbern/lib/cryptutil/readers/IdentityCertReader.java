package ch.dvbern.lib.cryptutil.readers;

import java.security.PublicKey;

import ch.dvbern.lib.cryptutil.fixme.NonNull;

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
