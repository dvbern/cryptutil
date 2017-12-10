package ch.dvbern.lib.cryptutil.fileformats;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.nio.charset.StandardCharsets;
import java.security.AlgorithmParameters;
import java.security.InvalidAlgorithmParameterException;
import java.security.InvalidKeyException;
import java.security.Key;
import java.security.KeyFactory;
import java.security.NoSuchAlgorithmException;
import java.security.interfaces.RSAPrivateCrtKey;
import java.security.interfaces.RSAPrivateKey;
import java.security.interfaces.RSAPublicKey;
import java.security.spec.InvalidKeySpecException;
import java.security.spec.KeySpec;
import java.security.spec.PKCS8EncodedKeySpec;
import java.security.spec.X509EncodedKeySpec;
import java.util.Base64;
import java.util.regex.Pattern;
import java.util.stream.Collectors;

import javax.crypto.Cipher;
import javax.crypto.EncryptedPrivateKeyInfo;
import javax.crypto.NoSuchPaddingException;
import javax.crypto.SecretKeyFactory;
import javax.crypto.spec.PBEKeySpec;

import ch.dvbern.lib.cryptutil.fixme.NonNull;
import ch.dvbern.lib.cryptutil.fixme.Nullable;

import static java.util.Objects.requireNonNull;

/**
 * Supports PKCS#8 public and privte keys, with and without password.
 *
 * You can determine if your file is in the correct format by looking at the file header:
 *
 * Valid headers (exact match required!):
 * <pre>
 * -----BEGIN PUBLIC KEY-----
 * -----BEGIN PRIVATE KEY-----
 * -----BEGIN ENCRYPTED PRIVATE KEY-----
 * </pre>
 *
 * In converting between key formats, google + openssl is your friend :)
 */
public final class PKCS8PEM {

	// header/footer for a PKCS#8 encoded PEM file without password/encryption
	private static final Pattern PEM_KEY_PATTERN =
			Pattern.compile("^-----(BEGIN|END) (ENCRYPTED )?(PRIVATE|PUBLIC) KEY-----\\s*$");
	private static final String ALGO_RSA = "RSA";

	/**
	 * Very(!) simple and non-forgiving parsing method:
	 * extracts the Base64 encoded payload of the PEM file
	 * and then base64 decodes it to get the raw key
	 */
	private @NonNull byte[] parseKeyFromPKCS8PEM(@NonNull InputStream pkcs8pem) {
		requireNonNull(pkcs8pem);

		BufferedReader br =
				new BufferedReader(new InputStreamReader(pkcs8pem, StandardCharsets.UTF_8));

		String content = br.lines()
				.filter(line -> !line.isEmpty())
				.filter(line -> !PEM_KEY_PATTERN.matcher(line).matches())
				.collect(Collectors.joining("\r\n"));
		byte[] keyBytes = Base64.getMimeDecoder().decode(content);
		return keyBytes;

	}

	public @NonNull RSAPrivateKey readKeyFromPKCS8EncodedPEM(
			@NonNull InputStream privateKey,
			@Nullable String password
	) throws IOException, NoSuchAlgorithmException, InvalidKeySpecException, NoSuchPaddingException,
			InvalidKeyException, InvalidAlgorithmParameterException {
		requireNonNull(privateKey);

		@NonNull byte[] keyData = parseKeyFromPKCS8PEM(privateKey);
		@NonNull RSAPrivateKey rsaPrivateKey;
		if (password != null) {
			rsaPrivateKey = createFromPKCS8EndocdedRSAKey(keyData, password);
		} else {
			rsaPrivateKey = createFromPKCS8EndocdedRSAKey(keyData);
		}
		return rsaPrivateKey;
	}

	public @NonNull RSAPublicKey readCertFromPKCS8EncodedPEM(@NonNull InputStream pkcs8pem)
			throws NoSuchAlgorithmException, InvalidKeySpecException {
		@NonNull byte[] keyData = parseKeyFromPKCS8PEM(pkcs8pem);
		return createFromX509EncodedRSACert(keyData);
	}

	private @NonNull RSAPublicKey createFromX509EncodedRSACert(@NonNull byte publicKey[])
			throws NoSuchAlgorithmException, InvalidKeySpecException {
		KeyFactory kf = KeyFactory.getInstance(ALGO_RSA);
		RSAPublicKey pubKey = (RSAPublicKey) kf.generatePublic(new X509EncodedKeySpec(publicKey));
		return pubKey;
	}

	private @NonNull RSAPrivateKey createFromPKCS8EndocdedRSAKey(@NonNull byte privateKey[])
			throws NoSuchAlgorithmException, InvalidKeySpecException {
		KeyFactory kf = KeyFactory.getInstance(ALGO_RSA);
		PKCS8EncodedKeySpec keySpec = new PKCS8EncodedKeySpec(privateKey);
		RSAPrivateKey privKey = (RSAPrivateKey) kf.generatePrivate(keySpec);
		return privKey;
	}

	private @NonNull RSAPrivateKey createFromPKCS8EndocdedRSAKey(@NonNull byte privateKey[], @NonNull String passwordString)
			throws IOException, NoSuchAlgorithmException, NoSuchPaddingException,
			InvalidKeySpecException, InvalidKeyException, InvalidAlgorithmParameterException {
		requireNonNull(privateKey);
		requireNonNull(passwordString);

		EncryptedPrivateKeyInfo ePKInfo = new EncryptedPrivateKeyInfo(privateKey);
		char[] password = passwordString.toCharArray();
		Cipher cipher = Cipher.getInstance(ePKInfo.getAlgName());
		PBEKeySpec pbeKeySpec = new PBEKeySpec(password);
		// Now create the Key from the PBEKeySpec
		SecretKeyFactory skFac = SecretKeyFactory.getInstance(ePKInfo.getAlgName());
		Key pbeKey = skFac.generateSecret(pbeKeySpec);
		// Extract the iteration count and the salt
		AlgorithmParameters algParams = ePKInfo.getAlgParameters();
		cipher.init(Cipher.DECRYPT_MODE, pbeKey, algParams);
		// Decrypt the encryped private key into a PKCS8EncodedKeySpec
		KeySpec pkcs8KeySpec = ePKInfo.getKeySpec(cipher);
		// Now retrieve the RSA Public and private keys by using an
		// RSA keyfactory.
		KeyFactory rsaKeyFac = KeyFactory.getInstance(ALGO_RSA);
		// First get the private key
		RSAPrivateCrtKey rsaPriv = (RSAPrivateCrtKey) rsaKeyFac.generatePrivate(pkcs8KeySpec);
		return rsaPriv;
	}
}
