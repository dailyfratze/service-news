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

import java.time.OffsetDateTime;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.cache.annotation.EnableCaching;

import de.dailyfratze.news.config.NewsServiceProperties;
import de.dailyfratze.news.port.adapter.persistence.jpa.PostEntityDao;

/**
 * @author Michael J. Simons, 2018-05-31
 */
@SpringBootApplication
@EnableConfigurationProperties(NewsServiceProperties.class)
@EnableCaching
public class NewsServiceApplication implements CommandLineRunner {

	@Autowired
	PostEntityDao postEntityDao;



	public static void main(String[] args) {
		SpringApplication.run(NewsServiceApplication.class, args);
	}

	@Override
	public void run(String... args) throws Exception {
		postEntityDao.findAll(5, OffsetDateTime.now(), null).forEach(System.out::println);

	}
}
