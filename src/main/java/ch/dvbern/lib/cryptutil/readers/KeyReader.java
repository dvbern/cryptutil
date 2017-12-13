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

package ch.dvbern.lib.cryptutil.readers;

import java.security.PrivateKey;

import ch.dvbern.lib.cryptutil.annotations.NonNull;

public interface KeyReader<T extends PrivateKey> {
	@NonNull T readPrivateKey() throws ReaderException;
}
