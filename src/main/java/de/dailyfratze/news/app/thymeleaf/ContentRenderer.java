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

import org.thymeleaf.context.ITemplateContext;
import org.thymeleaf.engine.AttributeName;
import org.thymeleaf.model.IProcessableElementTag;
import org.thymeleaf.processor.element.AbstractAttributeTagProcessor;
import org.thymeleaf.processor.element.IElementTagStructureHandler;
import org.thymeleaf.standard.expression.StandardExpressions;
import org.thymeleaf.templatemode.TemplateMode;

import java.beans.Introspector;
import java.util.Map;

/**
 * A renderer that generates a representation for an arbitrary object.
 *
 * @author Michael J. Simons, 2018-07-16
 */
@FunctionalInterface
public interface ContentRenderer<T>  {
	String render(T t);
}

class ContentRendererBasedProcessor extends AbstractAttributeTagProcessor {

	private final Map<String, ContentRenderer> renderer;

	public ContentRendererBasedProcessor(final String dialectPrefix, final Map<String, ContentRenderer> renderer) {
		super(TemplateMode.HTML, dialectPrefix, null, false, "with-content-of", true, 10, true);
		this.renderer = renderer;
	}

	@Override
	protected void doProcess(ITemplateContext context, IProcessableElementTag tag, AttributeName attributeName, String attributeValue, IElementTagStructureHandler structureHandler) {
		var parser = StandardExpressions.getExpressionParser(context.getConfiguration());
		var expression = parser.parseExpression(context, attributeValue);
		structureHandler.setBody(render(expression.execute(context)), true);
	}

	final String render(final Object content) {
		return renderer.get(getRendererName(content)).render(content);
	}

	final String getRendererName(final Object content) {
		return Introspector.decapitalize(content.getClass().getSimpleName()) + "Renderer";
	}
}