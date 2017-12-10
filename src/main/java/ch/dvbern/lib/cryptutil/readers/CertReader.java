package ch.dvbern.lib.cryptutil.readers;

import java.security.PublicKey;

import ch.dvbern.lib.cryptutil.fixme.NonNull;

public interface CertReader<T extends PublicKey> {
	@NonNull T readPublicKey() throws ReaderException;
}
