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
package de.dailyfratze.news.port.adapter.persistence;

import static java.util.stream.Collectors.toList;

import de.dailyfratze.news.domain.model.Post;
import de.dailyfratze.news.domain.model.PostRepository;
import de.dailyfratze.news.port.adapter.persistence.jpa.PostEntity;
import de.dailyfratze.news.port.adapter.persistence.jpa.PostEntityDao;
import lombok.RequiredArgsConstructor;

import org.springframework.lang.Nullable;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.time.OffsetDateTime;
import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.util.List;
import java.util.Optional;

import javax.validation.constraints.Null;

/**
 * @author Michael J. Simons, 2018-05-31
 */
@Repository
@RequiredArgsConstructor
public class PostRepositoryImpl implements PostRepository {
	private final PostEntityDao postEntityDao;

	@Override
	@Transactional
	public void save(final Post post) {
		final OffsetDateTime createdAt = post.getCreatedAt().toOffsetDateTime();
		final String createdBy = post.getCreatedBy();

		this.postEntityDao.findByCreatedAtAndCreatedBy(createdAt, createdBy).ifPresentOrElse(
			existingPost -> existingPost.updateWith(post),
			() -> this.postEntityDao.save(new PostEntity(post.getContent(), createdAt, createdBy))
		);
	}

	@Override
	public List<Post> findAll(final int limit, @Nullable final Post seekTo) {
		var hlp = Optional.ofNullable(seekTo);
		return this.postEntityDao.findAll(
				limit,
				hlp.map(Post::getCreatedAt).map(ZonedDateTime::toOffsetDateTime).orElse(null),
				hlp.map(Post::getCreatedBy).orElse(null)
		).stream()
				.map(p -> Post.builder()
						.createdBy(p.getCreatedBy())
						.createdAt(p.getCreatedAt().toZonedDateTime()) // TODO use correct zone
						.updatedBy(p.getUpdatedBy())
						.updatedAt(p.getUpdatedAt().toZonedDateTime())
						.content(p.getContent())
						.build())
				.collect(toList());

	}
}
