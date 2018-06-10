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

import java.io.IOException;
import java.time.ZonedDateTime;
import java.util.Optional;

import javax.validation.ValidationException;

import org.springframework.boot.jackson.JsonComponent;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.core.ObjectCodec;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.JsonDeserializer;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.module.SimpleModule;
import de.dailyfratze.news.application.FetchPostsCommand;
import de.dailyfratze.news.domain.model.PostRepository.Position;

/**
 * @author Michael J. Simons, 2018-06-10
 */
public class CommandsModule extends SimpleModule {
	public CommandsModule() {
		setMixInAnnotation(FetchPostsCommand.class, FetchPostsCommandMixIn.class);
		setMixInAnnotation(Position.class, PositionMixIn.class);

		//addDeserializer(FetchPostsCommand.class, new FetchPostsCommandDeserializer());
	}

abstract static class FetchPostsCommandMixIn {



	@JsonCreator
	public FetchPostsCommandMixIn(@JsonProperty("numberToFetch") final int numberToFetch, @JsonProperty("seekTo") final Position seekTo) {}

}

	abstract static class PositionMixIn {
		@JsonCreator
		PositionMixIn(@JsonProperty("createdAt") final ZonedDateTime createdAt, @JsonProperty("createdBy") final String createdBy) {
		}
	}
}
