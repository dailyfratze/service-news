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
package de.dailyfratze.news.port.adapter.serialization.json;

import static org.assertj.core.api.Assertions.assertThatExceptionOfType;

import java.io.IOException;
import java.time.LocalDate;
import java.time.LocalTime;
import java.time.ZoneId;
import java.time.ZonedDateTime;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.json.JsonTest;
import org.springframework.boot.test.json.JacksonTester;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.FilterType;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import com.fasterxml.jackson.databind.exc.InvalidDefinitionException;
import de.dailyfratze.news.application.FetchPostsCommand;
import de.dailyfratze.news.config.NewsServiceConfig;
import de.dailyfratze.news.domain.model.PostRepository.Position;

/**
 * @author Michael J. Simons, 2018-06-10
 */
@ExtendWith(SpringExtension.class)
@JsonTest(includeFilters = @ComponentScan.Filter(type = FilterType.ASSIGNABLE_TYPE, classes = {NewsServiceConfig.class}))
class CommandsModuleIT {
	private final JacksonTester<FetchPostsCommand> fetchPostsCommandTester;

	@SuppressWarnings("SpringJavaInjectionPointsAutowiringInspection")
	@Autowired
	public CommandsModuleIT(JacksonTester<FetchPostsCommand> fetchPostsCommandTester) {
		this.fetchPostsCommandTester = fetchPostsCommandTester;
	}

	@Nested
	class FetchPostCommandTest {
		@Test
		@DisplayName("FetchPostsCommandMixIn should handle invalid input.")
		public void handleInvalidInput() throws IOException {
			assertThatExceptionOfType(InvalidDefinitionException.class)
					.isThrownBy(() -> fetchPostsCommandTester.parse("{}"))
					.withCauseInstanceOf(IllegalArgumentException.class)
					.withMessageContaining("Number of posts to fetch must be greater than 1 (was 0).");

			assertThatExceptionOfType(InvalidDefinitionException.class)
					.isThrownBy(() -> fetchPostsCommandTester.parse("{\"numberToFetch\":-1}"))
					.withCauseInstanceOf(IllegalArgumentException.class)
					.withMessageContaining("Number of posts to fetch must be greater than 1 (was -1).");

			assertThatExceptionOfType(InvalidDefinitionException.class)
					.isThrownBy(() -> fetchPostsCommandTester.parse("{\"numberToFetch\":1, \"seekTo\":{}}"))
					.withCauseInstanceOf(NullPointerException.class)
					.withMessageContaining("Date of creation is required.");

			assertThatExceptionOfType(InvalidDefinitionException.class)
					.isThrownBy(() -> fetchPostsCommandTester.parse("{\"numberToFetch\":1, \"seekTo\":{\"createdAt\":\"2018-06-10T20:58:04.135029+02:00[Europe/Berlin]\"}}"))
					.withCauseInstanceOf(NullPointerException.class)
					.withMessageContaining("Creator is required.");
		}

		@Test
		@DisplayName("FetchPostsCommandMixIn should handle valid input.")
		public void shouldHandleValidInput() throws IOException {
			var json = "" +
					"{\n" +
					"    \"numberToFetch\": 23,\n" +
					"    \"seekTo\": {\n" +
					"        \"createdAt\": \"2018-06-10T21:21:00.000000+02:00[Europe/Berlin]\",\n" +
					"        \"createdBy\": \"michael\"\n" +
					"    }\n" +
					"}\n";
			fetchPostsCommandTester.parse(json).assertThat()
					.hasFieldOrPropertyWithValue("numberToFetch", 23)
					.hasFieldOrPropertyWithValue("seekTo", new Position(
							ZonedDateTime.of(LocalDate.of(2018, 6, 10), LocalTime.of(21,21,0), ZoneId.of("Europe/Berlin")),
							"michael"));
		}
	}
}