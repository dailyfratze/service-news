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
package de.dailyfratze.news.app;

import de.dailyfratze.commons.text.TextFilter;
import de.dailyfratze.news.NewsServiceProperties;
import org.springframework.stereotype.Component;

import java.util.Optional;

/**
 * @author Michael J. Simons, 2018-05-31
 */
@Component
class PostRenderer {
	private final TextFilter postFilterChain;

	private final Optional<String> baseUrl;

	public PostRenderer(TextFilter postFilterChain, final NewsServiceProperties newsServiceProperties) {
		this.postFilterChain = postFilterChain;
		this.baseUrl = newsServiceProperties.getBaseURL();
	}

	/*
	@Cacheable(cacheNames = "texts.rendered-posts", key = "#post.id")
	public String render(final PostEntity post) {
		return postFilterChain.apply(post.getContent(), baseUrl);
	}
	*/
}