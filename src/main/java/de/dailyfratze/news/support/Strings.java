package de.dailyfratze.news.support;

import java.util.function.Predicate;

/**
 * Contains some utility methods. Will be moved to a commons lib eventually or replaced by upcoming JDK 11 methods.
 *
 * <a href="https://bugs.openjdk.java.net/browse/JDK-8050818">JDK-8050818</a>
 * <a href="https://dzone.com/articles/new-methods-on-java-strings-with-jdk-11">new-methods-on-java-strings-with-jdk-11</a>
 *
 * @author Michael J. Simons, 2018-06-12
 */
public final class Strings {

	/**
	 * Null-safe predicate that checks wether the given String is blank or not. A String is blank if it is empty
	 * after trimming.
	 */
	public static final Predicate<String> isBlank = s -> s == null || s.trim().isEmpty();

	/**
	 * Negated version of {@link #isBlank}.
	 */
	public static final Predicate<String> isNotBlank = isBlank.negate();

	private Strings() {
	}
}
