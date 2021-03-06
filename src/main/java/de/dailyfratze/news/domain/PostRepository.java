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

import java.time.OffsetDateTime;
import java.util.List;
import java.util.Optional;

import javax.persistence.EntityManager;

import lombok.RequiredArgsConstructor;
import org.hibernate.query.Query;
import org.hibernate.type.OffsetDateTimeType;
import org.hibernate.type.StringType;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.lang.Nullable;

import static lombok.AccessLevel.PACKAGE;

/**
 * @author Michael J. Simons, 2018-05-31
 */
interface PostRepository extends JpaRepository<Post, Integer>, PostRepositoryNativeExt {
	/**
	 * Retrieves post by unique key.
	 *
	 * @param createdAt
	 * @param createdBy
	 * @return
	 */
	Optional<Post> findByCreatedAtAndCreatedBy(OffsetDateTime createdAt, String createdBy);
}

interface PostRepositoryNativeExt {
	/**
	 * Retrieves the last n posts in order by creation date.
	 *
	 * @param limit
	 * @param seekTo
	 * @return
	 */
	List<Post> findAll(int limit, @Nullable Post seekTo);
}

@RequiredArgsConstructor(access = PACKAGE)
class PostRepositoryNativeExtImpl implements PostRepositoryNativeExt {
	private final EntityManager entityManager;

	/**
	 * Here we see the beauty of JPA in action:
	 * <ol>
	 * 	<li>One cannot compare tuples with JPQL or HQL.</li>
	 * 	<li>One cannot use native limit with JPQL or HQL.</li>
	 * 	<li>
	 * 	    If we decide to use JPA, JPA maps null values to VARBINARY,
	 * 	    at least using the PostgreSQL Driver. This is true for using it directly or through Spring Data JPA.
	 * 	    We need to unwrap it to Hibernates native Query class where {@code setParameter} methods exists
	 * 	    that allow specifying the type for other types than date related stuff.
	 * 	</li>
	 * 	<li>
	 * 	    And than comes a funny one: PostgreSQL cannot determie the type of {@code created_at} (timestamp) when null.
	 * 	    Even we set the god damn value with the dedicated method. So, we have to cast it in the method. It would be to
	 * 	    nice to just write {@code :created_at::timestamp}, but than the Hibernate parser burps, so here we
	 * 	    go with a nice, additional cast.
	 * 	    The other option would be ommiting the null check and coalesce things.
	 * 	</li>
	 * 	<li>
	 * 	    Last but not least, there's no way to get the type of the query back once unwrapped so one has
	 * 	    to suppress warnings as well.
	 * 	</li>
	 * </ol>
	 *
	 * Wasted another 2 hours of my life for triviality.
	 *
	 * @param limit
	 * @param seekTo
	 * @return
	 */
	@Override
	public List<Post> findAll(final int limit, @Nullable final Post seekTo) {
		var sql = "SELECT * FROM sn_posts p "
				+ " WHERE (CAST(:created_at AS timestamp with time zone) IS NULL AND :created_by IS NULL) "
				+ "    OR (p.created_at, p.created_by) < (:created_at, :created_by) "
				+ " ORDER BY p.created_at DESC, p.created_by DESC "
				+ " LIMIT :limit";

		@SuppressWarnings("unchecked")
		final Query<Post> query = entityManager.createNativeQuery(sql, Post.class).unwrap(Query.class);
		var hlp = Optional.ofNullable(seekTo);
		return query
				.setParameter("created_at", hlp.map(Post::getCreatedAt).orElse(null), OffsetDateTimeType.INSTANCE)
				.setParameter("created_by", hlp.map(Post::getCreatedBy).orElse(null), StringType.INSTANCE)
				.setParameter("limit", limit)
				.getResultList();
	}
}