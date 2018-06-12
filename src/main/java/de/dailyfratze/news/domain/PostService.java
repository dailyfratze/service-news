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

import lombok.RequiredArgsConstructor;
import org.springframework.lang.Nullable;
import org.springframework.stereotype.Service;

import java.time.ZoneId;
import java.util.List;
import java.util.Optional;

/**
 * The services takes care of retrieving posts from the database and mapping them to the outside world.
 *
 * @author Michael J. Simons, 2018-06-12
 */
@Service
@RequiredArgsConstructor
public class PostService {
	private final ZoneId targetTimeZone;

	private final PostRepository postRepository;

	public Optional<Post> findById(final Integer id) {
		return Optional.ofNullable(id).flatMap(this.postRepository::findById);
	}

	public List<Post> fetchPosts(final int numberToFetch, @Nullable final Post seekTo) {
		return this.postRepository.findAll(numberToFetch,  seekTo);
	}
}
