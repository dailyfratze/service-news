/*
 * Copyright 2018 michael-simons.eu.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package de.dailyfratze.news.domain;

import static org.junit.jupiter.api.Assertions.assertAll;
import static org.junit.jupiter.api.Assertions.assertThrows;

import java.time.OffsetDateTime;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

/**
 * @author Michael J. Simons, 2018-06-18
 */
class PostTest {
	@Test
	@DisplayName("Constructor should check preconditions")
	void constructorShouldCheckPreconditions() {
		assertAll(
				() -> assertThrows(NullPointerException.class, () -> new Post(null, OffsetDateTime.now(), "michael"), "A post requires some content."),
				() -> assertThrows(NullPointerException.class, () -> new Post("", OffsetDateTime.now(), "michael"), "A post requires some content."),
				() -> assertThrows(NullPointerException.class, () -> new Post("	", OffsetDateTime.now(), "michael"), "A post requires some content."),
				() -> assertThrows(NullPointerException.class, () -> new Post("test", null, "michael"), "A post requires a creation date."),
				() -> assertThrows(NullPointerException.class, () -> new Post("test", OffsetDateTime.now(), null), "A post requires a creator."),
				() -> assertThrows(NullPointerException.class, () -> new Post("test", OffsetDateTime.now(), ""), "A post requires a creator."),
				() -> assertThrows(NullPointerException.class, () -> new Post("test", OffsetDateTime.now(), "	"), "A post requires a creator.")
		);
	}
}