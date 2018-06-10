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
package de.dailyfratze.news.port.adapter.web;

import java.time.ZonedDateTime;
import java.util.Map;
import java.util.Optional;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import de.dailyfratze.news.application.FetchPostsCommand;
import de.dailyfratze.news.application.PostService;

/**
 * @author Michael J. Simons, 2018-05-31
 */
@Controller
class NewsController {
	private final PostService postService;

	public NewsController(final PostService postService) {
		this.postService = postService;
	}

	@GetMapping({"", "/", "/news"})
	public ModelAndView index(

	) {
		// Map.of("posts", this.postService.fetchPosts(fetchPosts.orElseGet(() -> new FetchPostsCommand(5, null))))
		return new ModelAndView("index", Map.of());
	}
}
