package ch.dvbern.lib.cryptutil.readers;

import java.security.PrivateKey;

import ch.dvbern.lib.cryptutil.fixme.NonNull;

import static java.util.Objects.requireNonNull;

public class IdentityKeyReader<T extends PrivateKey> implements KeyReader<T>  {

	private final @NonNull T key;

	public IdentityKeyReader(@NonNull T key) {
		this.key = requireNonNull(key);
	}

	@Override
	public @NonNull T readPrivateKey() throws ReaderException {
		return key;
	}
}
