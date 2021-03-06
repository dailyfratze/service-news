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

import lombok.RequiredArgsConstructor;
import org.apache.http.client.config.CookieSpecs;
import org.apache.http.client.config.RequestConfig;
import org.apache.http.config.ConnectionConfig;
import org.apache.http.config.Registry;
import org.apache.http.config.RegistryBuilder;
import org.apache.http.conn.socket.ConnectionSocketFactory;
import org.apache.http.conn.socket.PlainConnectionSocketFactory;
import org.apache.http.conn.ssl.SSLConnectionSocketFactory;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.impl.conn.PoolingHttpClientConnectionManager;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.context.annotation.Bean;

import java.nio.charset.StandardCharsets;
import java.time.ZoneId;

/**
 * @author Michael J. Simons, 2018-05-31
 */
@SpringBootApplication
@EnableConfigurationProperties(NewsServiceProperties.class)
@EnableCaching
@RequiredArgsConstructor
public class NewsServiceApplication {

	private final NewsServiceProperties properties;

	@Bean
	public ZoneId targetTimeZone() {
		return this.properties.getTargetTimeZone();
	}

	/**
	 * @return The system wide HttpClient to use
	 */
	@Bean(destroyMethod = "close")
	public CloseableHttpClient httpClient() {

		final RequestConfig.Builder requestConfigBuilder = RequestConfig.custom()
			.setExpectContinueEnabled(false)
			.setConnectTimeout(5 * 60 * 1000)
			.setSocketTimeout(1 * 60 * 1000)
			.setRedirectsEnabled(true)
			.setCircularRedirectsAllowed(false)
			.setMaxRedirects(10);

		if (this.properties.getHttpClient().isIgnoreCookies()) {
			requestConfigBuilder.setCookieSpec(CookieSpecs.IGNORE_COOKIES);
		}

		final RequestConfig requestConfig = requestConfigBuilder.build();

		final ConnectionConfig connectionConfig = ConnectionConfig.custom()
			.setCharset(StandardCharsets.UTF_8)
			.build();

		final Registry<ConnectionSocketFactory> socketFactoryRegistry = RegistryBuilder.<ConnectionSocketFactory>create()
			.register("http", PlainConnectionSocketFactory.getSocketFactory())
			.register("https", SSLConnectionSocketFactory.getSocketFactory())
			.build();

		final PoolingHttpClientConnectionManager connectionManager = new PoolingHttpClientConnectionManager(socketFactoryRegistry);
		connectionManager.setMaxTotal(200);
		connectionManager.setDefaultMaxPerRoute(20);

		return HttpClients.custom()
			.setConnectionManager(connectionManager)
			.setDefaultRequestConfig(requestConfig)
			.setDefaultConnectionConfig(connectionConfig)
			.build();
	}

	public static void main(String[] args) {
		SpringApplication.run(NewsServiceApplication.class, args);
	}
}
