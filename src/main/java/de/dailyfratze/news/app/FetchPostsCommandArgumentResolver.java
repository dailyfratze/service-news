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

import de.dailyfratze.news.domain.Post;
import de.dailyfratze.news.domain.PostService;
import lombok.RequiredArgsConstructor;
import org.springframework.core.MethodParameter;
import org.springframework.web.bind.support.WebDataBinderFactory;
import org.springframework.web.context.request.NativeWebRequest;
import org.springframework.web.method.support.HandlerMethodArgumentResolver;
import org.springframework.web.method.support.ModelAndViewContainer;

import java.util.Optional;

import static lombok.AccessLevel.PACKAGE;

/**
 * Helps resolving {@link FetchPostsCommand fetch posts commands}. If a number of posts to fetch or a seek to post cannot
 * be resolved (for example parameter not given or invalid number), uses a sane default value.
 *
 * @author Michael J. Simons, 2018-06-12
 */
@RequiredArgsConstructor(access = PACKAGE)
class FetchPostsCommandArgumentResolver implements HandlerMethodArgumentResolver {

	static final int DEFAULT_NUMBER_TO_FETCH = 5;

	private final PostService postService;

	@Override
	public boolean supportsParameter(MethodParameter parameter) {
		return parameter.getParameterType().isAssignableFrom(FetchPostsCommand.class);
	}

	@Override
	public Object resolveArgument(MethodParameter parameter, ModelAndViewContainer mavContainer, NativeWebRequest webRequest, WebDataBinderFactory binderFactory) {
		var numberToFetch = Optional.<Integer>empty();
		var seekTo = Optional.<Post>empty();
		try {
			numberToFetch = Optional.ofNullable(webRequest.getParameter("numberToFetch")).map(Integer::parseInt);
		} catch(NumberFormatException e) {
		}
		try {
			seekTo = Optional.ofNullable(webRequest.getParameter("seekTo")).map(Integer::parseInt).flatMap(postService::findById);
		} catch(NumberFormatException e) {
		}
		return new FetchPostsCommand(numberToFetch.orElse(DEFAULT_NUMBER_TO_FETCH), seekTo);
	}
}
