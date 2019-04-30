/*
 * Copyright (C) 2017 DV Bern AG, Switzerland
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package ch.dvbern.lib.cryptutil;

import java.io.IOException;
import java.io.InputStream;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.security.Provider;

import ch.dvbern.lib.cryptutil.annotations.NonNull;
import ch.dvbern.lib.cryptutil.annotations.Nullable;

import static ch.dvbern.lib.cryptutil.Util.processFully;
import static java.util.Objects.requireNonNull;

public class DigestEngine {

	private static final int READ_BUFFER_SIZE = 4096;
	public static final String ALGO_SHA256 = "SHA-256";
	public static final String ALGO_SHA512 = "SHA-512";

	public boolean supportsSHA256(@Nullable Provider provider) {
		return supports(ALGO_SHA256, provider);
	}

	/**
	 * @param is ownership is not taken, caller needs to close the stream
	 */
	public @NonNull byte[] digestSHA256(@NonNull InputStream is, @Nullable Provider provider)
			throws IOException, DigestFailedException {
		return digestWithAlgo(is, ALGO_SHA256, provider);
	}

	public @NonNull MessageDigest configureSHA256(@Nullable Provider provider) {
		try {
			return configureMessageDigest(ALGO_SHA256, provider);
		} catch (NoSuchAlgorithmException e) {
			throw new IllegalArgumentException("No such algorithm: " + ALGO_SHA256, e);
		}
	}

	public boolean supportsSHA512(@Nullable Provider provider) {
		return supports(ALGO_SHA512, provider);
	}

	/**
	 * @param is ownership is not taken, caller needs to close the stream
	 */
	public @NonNull byte[] digestSHA512(@NonNull InputStream is, @Nullable Provider provider)
			throws IOException, DigestFailedException {
		return digestWithAlgo(is, ALGO_SHA512, provider);
	}

	public @NonNull MessageDigest configureSHA512(@Nullable Provider provider) {
		try {
			return configureMessageDigest(ALGO_SHA512, provider);
		} catch (NoSuchAlgorithmException e) {
			throw new IllegalArgumentException("No such algorithm: " + ALGO_SHA512, e);
		}
	}

	public boolean supports(@NonNull String algorithm, @Nullable Provider provider) {
		try {
			configureMessageDigest(algorithm, provider);
			return true;
		} catch (NoSuchAlgorithmException ignored) {
			return false;
		}
	}

	/**
	 * @param is ownership is not taken, caller needs to close the stream
	 */
	public @NonNull byte[] digestWithAlgo(
			@NonNull InputStream is,
			@NonNull String algorithm,
			@Nullable Provider provider
	) throws DigestFailedException, IOException {
		requireNonNull(is);
		requireNonNull(algorithm);

		MessageDigest md = null;
		try {
			md = configureMessageDigest(algorithm, provider);
		} catch (NoSuchAlgorithmException e) {
			throw new DigestFailedException("No such algorithm: " + algorithm, e);
		}
		byte[] digest = digestFully(md, is);

		return digest;
	}

	/**
	 * @param is ownership is not taken, caller needs to close the stream
	 */
	private @NonNull byte[] digestFully(@NonNull MessageDigest md, @NonNull InputStream is)
	throws IOException {
		requireNonNull(is);
		requireNonNull(md);

		processFully(is, READ_BUFFER_SIZE, md::update);

		return md.digest();
	}

	public @NonNull MessageDigest configureMessageDigest(@NonNull String algorithm, @Nullable Provider provider)
			throws NoSuchAlgorithmException {
		requireNonNull(algorithm);

		return provider != null
				? MessageDigest.getInstance(algorithm, provider)
				: MessageDigest.getInstance(algorithm);
	}

}
