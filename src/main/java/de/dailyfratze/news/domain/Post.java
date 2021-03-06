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

import lombok.AccessLevel;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import javax.persistence.Cacheable;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.UniqueConstraint;
import java.io.Serializable;
import java.time.OffsetDateTime;
import java.util.Objects;
import java.util.Optional;

import static de.dailyfratze.news.support.Strings.isNotBlank;

/**
 * @author Michael J. Simons, 2018-05-31
 */
@Entity
@Table(
	name = "sn_posts",
	uniqueConstraints = @UniqueConstraint(name = "sn_posts_uk", columnNames = {"created_at", "created_by"}))
@Cacheable
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
@NoArgsConstructor(access = AccessLevel.PACKAGE)
@Getter
@EqualsAndHashCode(of = {"createdAt", "createdBy"})
public class Post implements Serializable {
	private static final long serialVersionUID = 8762443218410015743L;

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(precision = 8)
	private Integer id;

	/**
	 * Content of this posts.
	 */
	@Column(nullable = false, columnDefinition = "text")
	private String content;

	// See reasoning about LocalDateTime, OffsetDateTime and ZonedDateTime here:
	// http://blog.schauderhaft.de/2018/03/14/dont-use-localdatetime/
	// and read postgres doc here:
	// https://jdbc.postgresql.org/documentation/head/8-date-time.html
	@Column(name = "created_at", nullable = false)
	private OffsetDateTime createdAt;

	@Column(name = "created_by", nullable = false)
	private String createdBy;

	@Column(name = "updated_at", nullable = false)
	private OffsetDateTime updatedAt;

	@Column(name = "updated_by", nullable = false)
	private String updatedBy;

	/**
	 * Creates a new post, updated* will be equal to created*
	 * @param content
	 * @param createdAt
	 * @param createdBy
	 */
	Post(final String content, final OffsetDateTime createdAt, final String createdBy) {
		this.content = Objects.requireNonNull(Optional.ofNullable(content).map(String::trim).filter(isNotBlank).orElse(null), "A post requires some content.");
		this.createdAt = this.updatedAt = Objects.requireNonNull(createdAt, "A post requires a creation date.");
		this.createdBy = this.updatedBy = Objects.requireNonNull(Optional.ofNullable(createdBy).map(String::trim).filter(isNotBlank).orElse(null), "A post requires a creator.");
	}
}
