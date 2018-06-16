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

import de.dailyfratze.news.domain.PostService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.servlet.ModelAndView;

import java.util.Map;

import static lombok.AccessLevel.PACKAGE;

/**
 * @author Michael J. Simons, 2018-05-31
 */
@Controller
@RequiredArgsConstructor(access = PACKAGE)
class PostController {

	private final PostService postService;

	@GetMapping({"", "/"})
	public ModelAndView index(final FetchPostsCommand fetchPosts) {
		return new ModelAndView("index", Map.of("posts", this.postService.fetchPosts(fetchPosts.getNumberToFetch(), fetchPosts.getSeekTo().orElse(null))));
	}

	@GetMapping("/{id:\\d+}")
	public ModelAndView singlePost(@PathVariable final int id) {
		return new ModelAndView("single_post", Map.of("posts", this.postService.fetchPosts(fetchPosts.getNumberToFetch(), fetchPosts.getSeekTo().orElse(null))));
	}
}
