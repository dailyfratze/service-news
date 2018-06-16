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
package de.dailyfratze.news.app.thymeleaf;

import lombok.RequiredArgsConstructor;
import org.thymeleaf.dialect.IProcessorDialect;
import org.thymeleaf.processor.IProcessor;

import java.util.Map;
import java.util.Set;

/**
 * @author Michael J. Simons, 2018-07-16
 */
@RequiredArgsConstructor
public class DailyFratzeDialect implements IProcessorDialect {
	private final Map<String, ContentRenderer> renderer;

	@Override
	public String getPrefix() {
		return "df";
	}

	@Override
	public int getDialectProcessorPrecedence() {
		return 1000;
	}

	@Override
	public Set<IProcessor> getProcessors(String s) {
		return Set.of(new ContentRendererBasedProcessor(s, renderer));
	}

	@Override
	public String getName() {
		return this.getClass().getName();
	}
}
