package ch.dvbern.lib.cryptutil.readers;

import java.io.InputStream;
import java.security.interfaces.RSAPrivateKey;

import ch.dvbern.lib.cryptutil.fileformats.PKCS8PEM;
import ch.dvbern.lib.cryptutil.fixme.NonNull;
import ch.dvbern.lib.cryptutil.fixme.Nullable;

import static java.util.Objects.requireNonNull;

public class PKCS8PEMKeyReader implements KeyReader<RSAPrivateKey> {

	private final @NonNull PKCS8PEM pkcs8PEM = new PKCS8PEM();
	private final @NonNull InputStream privateKey;
	private final @Nullable String keyPassword;

	public PKCS8PEMKeyReader(@NonNull InputStream privateKey, @Nullable String keyPassword) {
		this.privateKey = requireNonNull(privateKey);
		this.keyPassword = keyPassword;
	}

	@Override
	public @NonNull RSAPrivateKey readPrivateKey() {
		try {
			return pkcs8PEM.readKeyFromPKCS8EncodedPEM(privateKey, keyPassword);
		} catch (Exception e) {
			throw new ReaderException("Could not read PKCS8EncodedPEM", e);
		}
	}

}
