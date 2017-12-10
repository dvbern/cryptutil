package ch.dvbern.lib.cryptutil;

import java.io.BufferedInputStream;
import java.io.ByteArrayOutputStream;
import java.io.InputStream;
import java.net.URL;

import ch.dvbern.lib.cryptutil.fixme.NonNull;

import static ch.dvbern.lib.cryptutil.Util.processFully;
import static java.util.Objects.requireNonNull;

public final class TestingUtil {
	private TestingUtil() {
		// utility class
	}

	public static @NonNull URL resourceURL(@NonNull String resourcePath) {
		URL url = Thread.currentThread().getContextClassLoader().getResource(resourcePath);
		return requireNonNull(url);
	}

	public static @NonNull InputStream resourceStream(@NonNull String resourcePath) {
		InputStream stream = Thread.currentThread().getContextClassLoader().getResourceAsStream(resourcePath);
		return requireNonNull(stream);
	}



	public static @NonNull byte[] readFully(@NonNull URL url) {
		try (InputStream is = new BufferedInputStream(url.openStream())) {
			ByteArrayOutputStream baos = new ByteArrayOutputStream();
			processFully(is, 4096, baos::write);
			return baos.toByteArray();
		} catch(Exception e) {
			throw new IllegalStateException("Could not read: " + url, e);
		}
	}
}
