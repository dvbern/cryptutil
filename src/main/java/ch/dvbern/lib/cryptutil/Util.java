package ch.dvbern.lib.cryptutil;

import java.io.IOException;
import java.io.InputStream;
import java.util.Arrays;

import ch.dvbern.lib.cryptutil.fixme.NonNull;

import static java.util.Objects.requireNonNull;

final class Util {
	private Util() {
		// utility class
	}

	@FunctionalInterface
	public interface CheckedConsumer<T, E extends Exception> {
		void accept(T o) throws E;

	}

	/**
	 * read the inputstream chunk-wise and call the processor for each chunk
	 *
	 * @param readChunkBufferBytes size of the read buffer for each read cycle.
	 */
	static <E extends Exception> void processFully(
			@NonNull InputStream is,
			int readChunkBufferBytes,
			@NonNull CheckedConsumer<@NonNull byte[], E> processor
	) throws IOException {
		requireNonNull(is);
		requireNonNull(processor);
		assert readChunkBufferBytes > 0;

		int byteOffset = 0;
		byte buffer[] = new byte[readChunkBufferBytes];
		for (int bytes = 0; bytes > -1; bytes = is.read(buffer)) {
			byte[] tmp = buffer;
			if (bytes != readChunkBufferBytes) {
				// mostly on the last chunk
				tmp = Arrays.copyOf(buffer, bytes);
			}
			try {
				processor.accept(tmp);
				byteOffset += bytes;
			} catch (Exception e) {
				throw new IllegalStateException("Error processing at byte offset: " + byteOffset, e);
			}
		}

	}
}
