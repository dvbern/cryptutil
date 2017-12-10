package ch.dvbern.lib.cryptutil;

import ch.dvbern.lib.cryptutil.fixme.NonNull;

public class DigestFailedException extends Exception {
	private static final long serialVersionUID = 5378899387233981937L;

	public DigestFailedException(@NonNull String message, @NonNull Throwable cause) {
		super(message, cause);
	}
}
