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
package de.dailyfratze.news.port.adapter.persistence.jpa;

import org.springframework.data.jpa.repository.JpaRepository;

import java.time.OffsetDateTime;
import java.util.Optional;

/**
 * @author Michael J. Simons, 2018-05-31
 */
public interface PostEntityDao extends JpaRepository<PostEntity, Integer> {
	/**
	 * Retrieves post by unique key.
	 *
	 * @param createdAt
	 * @param createdBy
	 * @return
	 */
	Optional<PostEntity> findByCreatedAtAndAndCreatedBy(OffsetDateTime createdAt, String createdBy);
}
