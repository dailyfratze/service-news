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
package de.dailyfratze.news.domain.model;

import lombok.Builder;
import lombok.Getter;

import java.time.ZonedDateTime;

/**
 * @author Michael J. Simons, 2018-05-31
 */
@Builder
@Getter
public class Post {
	private final String content;

	private final ZonedDateTime createdAt;

	private final String createdBy;

	private final ZonedDateTime updatedAt;

	private final String updatedBy;
}
