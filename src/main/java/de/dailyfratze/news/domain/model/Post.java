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

import java.time.ZonedDateTime;
import java.util.Objects;
import java.util.function.Function;
import java.util.function.UnaryOperator;

/**
 * @author Michael J. Simons, 2018-05-31
 */
public class Post {
	private final String content;

	private final String createdBy;

	private final ZonedDateTime createdAt;

	private final String updatedBy;

	private final ZonedDateTime updatedAt;

	public static Function<UnaryOperator<PostBuilder>, Post> builder() {
		return f -> f.andThen(Post::new).apply(new PostBuilder());
	}

	private Post(final PostBuilder postBuilder) {
		this.content = Objects.requireNonNull(postBuilder.content, "A post requires content.");
		this.createdAt = Objects.requireNonNull(postBuilder.createdAt, "Date of creation is required.");
		this.createdBy = Objects.requireNonNull(postBuilder.createdBy, "Creator is required.");
		this.updatedAt = Objects.requireNonNullElse(postBuilder.updatedAt, postBuilder.createdAt);
		this.updatedBy = Objects.requireNonNullElse(postBuilder.updatedBy, postBuilder.updatedBy);
	}

	public String getContent() {
		return content;
	}

	public ZonedDateTime getCreatedAt() {
		return createdAt;
	}

	public String getCreatedBy() {
		return createdBy;
	}

	public ZonedDateTime getUpdatedAt() {
		return updatedAt;
	}

	public String getUpdatedBy() {
		return updatedBy;
	}

	public static class PostBuilder {

		private String content;

		private String createdBy;

		private ZonedDateTime createdAt;

		private String updatedBy;

		private ZonedDateTime updatedAt;

		private PostBuilder() {
		}

		public PostBuilder content(final String content) {
			this.content = content;
			return this;
		}

		public DateStep createdAt(final ZonedDateTime createdAt) {
			return new DateStep(createdAt, ds -> {
				this.createdAt = ds.at;
				this.createdBy = ds.user;
				return this;
			});
		}

		public DateStep updatedAt(final ZonedDateTime updatedAt) {
			return new DateStep(updatedAt, ds -> {
				this.updatedAt = ds.at;
				this.updatedBy = ds.user;
				return this;
			});
		}
	}

	public static class DateStep {
		private final Function<DateStep, PostBuilder> sink;

		private final ZonedDateTime at;

		private String user;

		private DateStep(ZonedDateTime at, Function<DateStep, PostBuilder> sink) {
			this.at = at;
			this.sink = sink;
		}

		public PostBuilder by(final String user) {
			this.user = user;
			return this.sink.apply(this);
		}
	}
}
