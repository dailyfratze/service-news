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
package de.dailyfratze.news.application;

import org.springframework.context.annotation.Primary;

import de.dailyfratze.news.domain.model.PostRepository.Position;

/**
 * @author Michael J. Simons, 2018-06-02
 */
public final class FetchPostsCommand {

	/**
	 * Maximum number of posts to fetch.
	 */
	private final int numberToFetch;

	/**
	 * Optional last post that was fetched. If set, seek to that post and return {@link #numberToFetch}-posts
	 * from there on.
	 */
	private final Position seekTo;

	public FetchPostsCommand(final int numberToFetch, final Position seekTo) {
		if(numberToFetch <= 0) {
			throw new IllegalNumberOfPostsToFetch(1, numberToFetch);
		}

		this.numberToFetch = numberToFetch;
		this.seekTo = seekTo;
	}

	public int getNumberToFetch() {
		return numberToFetch;
	}

	public Position getSeekTo() {
		return seekTo;
	}

	public static class IllegalNumberOfPostsToFetch extends IllegalArgumentException {
		private final int requiredNumberOfPostsToFetch;

		private final int actualNumberOfPostsToFetch;

		private IllegalNumberOfPostsToFetch(final int requiredNumberOfPostsToFetch, final int actualNumberOfPostsToFetch) {
			super(String.format("Number of posts to fetch must be greater than %d (was %d).", requiredNumberOfPostsToFetch, actualNumberOfPostsToFetch));
			this.requiredNumberOfPostsToFetch = requiredNumberOfPostsToFetch;
			this.actualNumberOfPostsToFetch = actualNumberOfPostsToFetch;
		}

		public int getRequiredNumberOfPostsToFetch() {
			return requiredNumberOfPostsToFetch;
		}

		public int getActualNumberOfPostsToFetch() {
			return actualNumberOfPostsToFetch;
		}
	}
}
