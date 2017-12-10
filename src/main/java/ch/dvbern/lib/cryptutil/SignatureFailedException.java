package ch.dvbern.lib.cryptutil;

import ch.dvbern.lib.cryptutil.fixme.NonNull;

public class SignatureFailedException extends Exception {
	private static final long serialVersionUID = 5378899387233981937L;

	public SignatureFailedException(@NonNull Throwable cause) {
		super(cause);
	}
}
