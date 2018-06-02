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

import de.dailyfratze.news.domain.model.Post;
import lombok.Builder;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

/**
 * @author Michael J. Simons, 2018-06-02
 */
@Getter
@RequiredArgsConstructor
@Builder(builderMethodName = "fetch")
public final class FetchPostsCommand {

	/**
	 * @param atMostNPosts
	 * @return A builder to fetch at most n posts.
	 */
	public static FetchPostsCommandBuilder fetch(final int atMostNPosts) {
		return new FetchPostsCommandBuilder().numberToFetch(atMostNPosts);
	}

	/**
	 * Maximum number of posts to fetch.
	 */
	private final int numberToFetch;

	/**
	 * Optional last post that was fetched. If set, seek to that post and return {@link #numberToFetch}-posts
	 * from there on.
	 */
	private final Post lastFetchedPost;
}
