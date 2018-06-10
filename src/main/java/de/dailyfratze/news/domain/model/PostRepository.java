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
import java.util.List;
import java.util.Objects;

/**
 * @author Michael J. Simons, 2018-05-31
 */
public interface PostRepository {
	void save(Post post);

	List<Post> findAll(int limit, Position seekTo);

	/**
	 * This value object represents a position in the list of posts
	 * ordered by date of creation and by author.
	 */
	class Position {
		private final ZonedDateTime createdAt;

		private final String createdBy;

		public Position(final ZonedDateTime createdAt, final String createdBy) {
			this.createdAt = Objects.requireNonNull(createdAt, "Date of creation is required.");
			this.createdBy = Objects.requireNonNull(createdBy, "Creator is required.");
		}

		public ZonedDateTime getCreatedAt() {
			return createdAt;
		}

		public String getCreatedBy() {
			return createdBy;
		}

		@Override
		public boolean equals(Object o) {
			if (this == o) {
				return true;
			}
			if (!(o instanceof Position)) {
				return false;
			}
			final Position position = (Position) o;
			return Objects.equals(createdAt, position.createdAt) &&
					Objects.equals(createdBy, position.createdBy);
		}

		@Override
		public int hashCode() {
			return Objects.hash(createdAt, createdBy);
		}

		@Override
		public String toString() {
			return "Position{" +
					"createdAt=" + createdAt +
					", createdBy='" + createdBy + '\'' +
					'}';
		}
	}
}