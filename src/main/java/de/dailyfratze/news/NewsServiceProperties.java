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

import java.time.ZoneId;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.springframework.boot.context.properties.ConfigurationProperties;

import ac.simons.oembed.OembedEndpoint;

/**
 * @author Michael J. Simons, 2018-05-31
 */
@ConfigurationProperties(prefix = "news-service")
public class NewsServiceProperties {

	private Optional<String> baseURL;

	public static class PostRendererProperties {
		private String smileyPack = "standard2.0";

		public String getSmileyPack() {
			return smileyPack;
		}

		public void setSmileyPack(String smileyPack) {
			this.smileyPack = smileyPack;
		}
	}

	public static class AutoLinkerProperties {
		private boolean hexEncodeEmailAddress = true;

		private boolean obfuscateEmailAddress = true;

		private int maxLabelLength = 30;

		public boolean isHexEncodeEmailAddress() {
			return hexEncodeEmailAddress;
		}

		public void setHexEncodeEmailAddress(boolean hexEncodeEmailAddress) {
			this.hexEncodeEmailAddress = hexEncodeEmailAddress;
		}

		public boolean isObfuscateEmailAddress() {
			return obfuscateEmailAddress;
		}

		public void setObfuscateEmailAddress(boolean obfuscateEmailAddress) {
			this.obfuscateEmailAddress = obfuscateEmailAddress;
		}

		public int getMaxLabelLength() {
			return maxLabelLength;
		}

		public void setMaxLabelLength(int maxLabelLength) {
			this.maxLabelLength = maxLabelLength;
		}
	}

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
		private Optional<String> cacheName = Optional.empty();

		/**
		 * Time in seconds responses are cached. Used if the response has no
		 * cache_age, defaults to 3600 (one hour).
		 */
		private Optional<Integer> defaultCacheAge = Optional.empty();

		public List<OembedEndpoint> getEndpoints() {
			return endpoints;
		}

		public void setEndpoints(List<OembedEndpoint> endpoints) {
			this.endpoints = endpoints;
		}

		public boolean isAutodiscovery() {
			return autodiscovery;
		}

		public void setAutodiscovery(boolean autodiscovery) {
			this.autodiscovery = autodiscovery;
		}

		public Optional<String> getCacheName() {
			return cacheName;
		}

		public void setCacheName(Optional<String> cacheName) {
			this.cacheName = cacheName;
		}

		public Optional<Integer> getDefaultCacheAge() {
			return defaultCacheAge;
		}

		public void setDefaultCacheAge(Optional<Integer> defaultCacheAge) {
			this.defaultCacheAge = defaultCacheAge;
		}
	}

	public static class HttpClientProperties {
		/**
		 * A flag wether the <em>Daily Fratze</em> wide http client should ignore
		 * cookies or not.
		 */
		private boolean ignoreCookies = false;

		public boolean isIgnoreCookies() {
			return ignoreCookies;
		}

		public void setIgnoreCookies(boolean ignoreCookies) {
			this.ignoreCookies = ignoreCookies;
		}
	}

	private PostRendererProperties postRenderer = new PostRendererProperties();

	private AutoLinkerProperties autoLinker = new AutoLinkerProperties();

	private OembedProperties oembed = new OembedProperties();

	private HttpClientProperties httpClient = new HttpClientProperties();

	/**
	 * The target time-zone for date computations. This is independent from the
	 * zone stuff is presented to the user.
	 */
	private ZoneId targetTimeZone = ZoneId.of("Europe/Berlin");

	public Optional<String> getBaseURL() {
		return baseURL;
	}

	public void setBaseURL(Optional<String> baseURL) {
		this.baseURL = baseURL;
	}

	public PostRendererProperties getPostRenderer() {
		return postRenderer;
	}

	public void setPostRenderer(PostRendererProperties postRenderer) {
		this.postRenderer = postRenderer;
	}

	public AutoLinkerProperties getAutoLinker() {
		return autoLinker;
	}

	public void setAutoLinker(AutoLinkerProperties autoLinker) {
		this.autoLinker = autoLinker;
	}

	public OembedProperties getOembed() {
		return oembed;
	}

	public void setOembed(OembedProperties oembed) {
		this.oembed = oembed;
	}

	public HttpClientProperties getHttpClient() {
		return httpClient;
	}

	public void setHttpClient(HttpClientProperties httpClient) {
		this.httpClient = httpClient;
	}

	public ZoneId getTargetTimeZone() {
		return targetTimeZone;
	}

	public void setTargetTimeZone(ZoneId targetTimeZone) {
		this.targetTimeZone = targetTimeZone;
	}
}
