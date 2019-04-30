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
import java.util.Arrays;

import ch.dvbern.lib.cryptutil.annotations.NonNull;

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
	 * @param is ownership is not taken, caller needs to close the stream
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
		byte[] buffer = new byte[readChunkBufferBytes];
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
