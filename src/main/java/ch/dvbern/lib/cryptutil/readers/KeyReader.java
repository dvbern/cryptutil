package ch.dvbern.lib.cryptutil.readers;

import java.security.PrivateKey;

import ch.dvbern.lib.cryptutil.annotations.NonNull;

public interface KeyReader<T extends PrivateKey> {
	@NonNull T readPrivateKey() throws ReaderException;
}
