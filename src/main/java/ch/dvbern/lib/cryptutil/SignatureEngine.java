package ch.dvbern.lib.cryptutil;

import java.io.IOException;
import java.io.InputStream;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.security.PrivateKey;
import java.security.Provider;
import java.security.PublicKey;
import java.security.Signature;
import java.security.SignatureException;

import ch.dvbern.lib.cryptutil.annotations.NonNull;
import ch.dvbern.lib.cryptutil.annotations.Nullable;

import static ch.dvbern.lib.cryptutil.Util.processFully;
import static java.util.Objects.requireNonNull;

public class SignatureEngine {
	private static final int READ_BUFFER_SIZE = 4096;

	private static final String ALGO_SHA256_WITH_RSA = "SHA256withRSA";
	private static final String ALGO_SHA512_WITH_RSA = "SHA512withRSA";

	public boolean supportsSHA256RSA(@Nullable Provider provider) {
		return supportsAlgorithm(ALGO_SHA256_WITH_RSA, provider);
	}

	public @NonNull byte[] signSHA256RSA(
			@NonNull PrivateKey privateKey,
			@NonNull InputStream is,
			@Nullable Provider provider
	) throws SignatureFailedException, IOException {
		return signUsingAlgorithm(privateKey, ALGO_SHA256_WITH_RSA, is, provider);
	}

	public boolean supportsSHA512RSA(@Nullable Provider provider) {
		return supportsAlgorithm(ALGO_SHA512_WITH_RSA, provider);
	}

	public @NonNull byte[] signSHA512RSA(
			@NonNull PrivateKey privateKey,
			@NonNull InputStream is,
			@Nullable Provider provider
	) throws SignatureFailedException, IOException {
		return signUsingAlgorithm(privateKey, ALGO_SHA512_WITH_RSA, is, provider);
	}

	public boolean supportsAlgorithm(@NonNull String algorithm, @Nullable Provider provider) {
		requireNonNull(algorithm);

		try {
			configureSignature(algorithm, provider);
			return true;
		} catch (NoSuchAlgorithmException ignored) {
			return false;
		}
	}

	public @NonNull byte[] signUsingAlgorithm(
			@NonNull PrivateKey privateKey,
			@NonNull String algorithm,
			@NonNull InputStream is,
			@Nullable Provider provider
	) throws SignatureFailedException, IOException {
		requireNonNull(privateKey);
		requireNonNull(is);
		requireNonNull(algorithm);

		try {
			Signature privateSignature = configureSignature(algorithm, provider);
			privateSignature.initSign(privateKey);
			processFully(is, READ_BUFFER_SIZE, privateSignature::update);
			byte[] signature = privateSignature.sign();

			return signature;
		} catch (NoSuchAlgorithmException | InvalidKeyException | SignatureException e) {
			throw new SignatureFailedException(e);
		}
	}

	public @NonNull boolean verifySHA256RSA(
			@NonNull PublicKey publicKey,
			@NonNull InputStream is,
			@NonNull byte referenceSignature[],
			@Nullable Provider provider
	) throws SignatureFailedException, IOException {
		return verifyUsingAlgorithm(publicKey, ALGO_SHA256_WITH_RSA, is, referenceSignature, provider);
	}

	public @NonNull boolean verifySHA512RSA(
			@NonNull PublicKey publicKey,
			@NonNull InputStream is,
			@NonNull byte referenceSignature[],
			@Nullable Provider provider
	) throws SignatureFailedException, IOException {
		return verifyUsingAlgorithm(publicKey, ALGO_SHA512_WITH_RSA, is, referenceSignature, provider);
	}

	public @NonNull boolean verifyUsingAlgorithm(
			@NonNull PublicKey publicKey,
			@NonNull String algorithm,
			@NonNull InputStream is,
			@NonNull byte referenceSignature[],
			@Nullable Provider provider
	) throws SignatureFailedException, IOException {
		requireNonNull(publicKey);
		requireNonNull(algorithm);
		requireNonNull(is);
		requireNonNull(referenceSignature);

		try {
			Signature privateSignature = configureSignature(algorithm, provider);
			privateSignature.initVerify(publicKey);
			processFully(is, READ_BUFFER_SIZE, privateSignature::update);
			boolean verified = privateSignature.verify(referenceSignature);

			return verified;
		} catch (NoSuchAlgorithmException | InvalidKeyException | SignatureException e) {
			throw new SignatureFailedException(e);
		}

	}

	private @NonNull Signature configureSignature(@NonNull String algorithm, @Nullable Provider provider)
			throws NoSuchAlgorithmException {
		requireNonNull(algorithm);

		return provider != null
				? Signature.getInstance(algorithm, provider)
				: Signature.getInstance(algorithm);
	}

}
