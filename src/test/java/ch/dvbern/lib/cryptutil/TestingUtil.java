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

import java.io.BufferedInputStream;
import java.io.ByteArrayOutputStream;
import java.io.InputStream;
import java.net.URL;

import ch.dvbern.lib.cryptutil.annotations.NonNull;

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
		} catch (Exception e) {
			throw new IllegalStateException("Could not read: " + url, e);
		}
	}
}
