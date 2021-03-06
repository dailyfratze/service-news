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
package de.dailyfratze.news;

import ac.simons.oembed.OembedEndpoint;
import lombok.Getter;
import lombok.Setter;
import org.springframework.boot.context.properties.ConfigurationProperties;

import java.time.ZoneId;
import java.util.ArrayList;
import java.util.List;

/**
 * @author Michael J. Simons, 2018-05-31
 */
@Setter
@Getter
@ConfigurationProperties(prefix = "news-service")
public class NewsServiceProperties {

	@Setter
	@Getter
	public static class PostRendererProperties {
		private String smileyPack = "standard2.0";
	}

	@Setter
	@Getter
	public static class AutoLinkerProperties {
		private boolean hexEncodeEmailAddress = true;

		private boolean obfuscateEmailAddress = true;

		private int maxLabelLength = 30;

	}

	@Getter
	@Setter
	public static class OembedProperties {
		/**
		 * The list of configured endpoints.
		 */
		private List<OembedEndpoint> endpoints = new ArrayList<>();

		/**
		 * A flag wether autodiscovery of oembed endpoints should be tried. Defaults
		 * to false.
		 */
		private boolean autodiscovery = false;

		/**
		 * The name of the cached used by this service. Defaults to
		 * "ac.simons.oembed.OembedService".
		 */
		private String cacheName = null;

		/**
		 * Time in seconds responses are cached. Used if the response has no
		 * cache_age, defaults to 3600 (one hour).
		 */
		private Integer defaultCacheAge = null;

	}

	@Setter
	@Getter
	public static class HttpClientProperties {
		/**
		 * A flag wether the <em>Daily Fratze</em> wide http client should ignore
		 * cookies or not.
		 */
		private boolean ignoreCookies = false;

	}

	private String baseURL = null;

	private PostRendererProperties postRenderer = new PostRendererProperties();

	private AutoLinkerProperties autoLinker = new AutoLinkerProperties();

	private OembedProperties oembed = new OembedProperties();

	private HttpClientProperties httpClient = new HttpClientProperties();

	/**
	 * The target time-zone for date computations. This is independent from the
	 * zone stuff is presented to the user.
	 */
	private ZoneId targetTimeZone = ZoneId.of("Europe/Berlin");
}
