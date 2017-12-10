package ch.dvbern.lib.cryptutil.readers;

import java.io.InputStream;
import java.security.interfaces.RSAPublicKey;

import ch.dvbern.lib.cryptutil.fileformats.PKCS8PEM;
import ch.dvbern.lib.cryptutil.fixme.NonNull;

import static java.util.Objects.requireNonNull;

public class PKCS8PEMCertReader implements CertReader<RSAPublicKey> {

	private final @NonNull PKCS8PEM pkcs8PEM = new PKCS8PEM();
	private final @NonNull InputStream publicKey;

	public PKCS8PEMCertReader(@NonNull InputStream publicKey) {
		this.publicKey = requireNonNull(publicKey);
	}

	@Override
	public @NonNull RSAPublicKey readPublicKey() throws ReaderException {
		try {
			return pkcs8PEM.readCertFromPKCS8EncodedPEM(publicKey);
		} catch (Exception e) {
			throw new ReaderException("Could not read PKCS8EncodedPEM", e);
		}
	}

}
