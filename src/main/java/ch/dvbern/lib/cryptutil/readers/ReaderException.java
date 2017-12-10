package ch.dvbern.lib.cryptutil.readers;

public class ReaderException extends RuntimeException {
	private static final long serialVersionUID = 6536271005147382132L;

	public ReaderException(String message) {
		super(message);
	}

	public ReaderException(String message, Throwable cause) {
		super(message, cause);
	}
}
