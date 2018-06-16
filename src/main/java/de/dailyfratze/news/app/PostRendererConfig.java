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

import ac.simons.autolinker.AutoLinkService;
import ac.simons.autolinker.EmailAddressAutoLinker;
import ac.simons.autolinker.TwitterUserAutoLinker;
import ac.simons.autolinker.UrlAutoLinker;
import ac.simons.oembed.OembedService;
import de.dailyfratze.commons.text.SmileyFilter;
import de.dailyfratze.commons.text.TextFilter;
import de.dailyfratze.commons.text.TextileFilter;
import de.dailyfratze.news.NewsServiceProperties;
import de.dailyfratze.news.NewsServiceProperties.AutoLinkerProperties;
import de.dailyfratze.news.NewsServiceProperties.OembedProperties;
import net.sf.ehcache.CacheManager;
import org.apache.http.client.HttpClient;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.Resource;
import org.springframework.core.io.ResourceLoader;

import java.io.IOException;
import java.util.List;

/**
 * @author Michael J. Simons, 2018-05-31
 */
@Configuration
class PostRendererConfig {
	@Bean
	AutoLinkService autoLinkService(final NewsServiceProperties newsServiceProperties) {
		final AutoLinkerProperties config = newsServiceProperties.getAutoLinker();
		return new AutoLinkService(List.of(
			new EmailAddressAutoLinker(config.isHexEncodeEmailAddress(), config.isObfuscateEmailAddress()),
			new TwitterUserAutoLinker(),
			new UrlAutoLinker(config.getMaxLabelLength())
		));
	}

	@Bean
	OembedService oembedService(
		final NewsServiceProperties newsServiceProperties,
		final HttpClient httpClient,
		final CacheManager cacheManager) {
		final OembedProperties config = newsServiceProperties.getOembed();
		final OembedService oembedService = new OembedService(httpClient, cacheManager, config.getEndpoints(), "dailyfratze");
		oembedService.setAutodiscovery(config.isAutodiscovery());
		if (config.getCacheName() != null) {
			oembedService.setCacheName(config.getCacheName());
		}
		if (config.getDefaultCacheAge() != null) {
			oembedService.setDefaultCacheAge(config.getDefaultCacheAge());
		}
		return oembedService;
	}

	@Bean
	TextFilter postFilterChain(
		final NewsServiceProperties newsServiceProperties,
		final ResourceLoader resourceLoader,
		final AutoLinkService autoLinkService,
		final OembedService oembedService
	) throws IOException {
		final String smileyPack = newsServiceProperties.getPostRenderer().getSmileyPack();
		final Resource resource = resourceLoader.getResource(String.format("classpath:/smilies/%s.pak", smileyPack));
		if (!resource.isReadable()) {
			throw new IllegalArgumentException(String.format("Smiley pack '%s' is not readable. Check if it exists under '/smilies/'", smileyPack));
		}

		final TextFilter smileyFilter = new SmileyFilter(smileyPack, resource.getInputStream());
		final TextFilter textileFilter = new TextileFilter();

		return (in, baseUrl) -> smileyFilter
			.andThen(s -> textileFilter.apply(s, baseUrl))
			.andThen(s -> autoLinkService.addLinks(s, baseUrl))
			.andThen(s -> oembedService.embedUrls(s, baseUrl))
			.apply(in, baseUrl);
	}

	@Bean
	PostRenderer postRenderer(
		final TextFilter postFilterChain,
		final NewsServiceProperties newsServiceProperties
	) {
		return new PostRenderer(postFilterChain, newsServiceProperties.getBaseURL());
	}
}
