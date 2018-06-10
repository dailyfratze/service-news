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
package de.dailyfratze.news.config;

import java.time.ZoneId;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import de.dailyfratze.news.port.adapter.serialization.json.CommandsModule;

/**
 * Configures several beans and aspects of the the news-service.
 * @author Michael J. Simons, 2018-06-10
 */
@Configuration
public class NewsServiceConfig {
	private final NewsServiceProperties newsServiceProperties;

	public NewsServiceConfig(final NewsServiceProperties newsServiceProperties) {
		this.newsServiceProperties = newsServiceProperties;
	}

	@Bean
	public ZoneId targetTimeZone() {
		return this.newsServiceProperties.getTargetTimeZone();
	}

	@Bean
	public CommandsModule commandsModule() {
		return new CommandsModule();
	}
}
