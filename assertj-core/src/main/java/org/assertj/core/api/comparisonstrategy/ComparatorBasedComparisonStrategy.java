/*
 * Licensed under the Apache License, Version 2.0 (the "License"); you may not use this file except in compliance with
 * the License. You may obtain a copy of the License at
 *
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software distributed under the License is distributed on
 * an "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied. See the License for the
 * specific language governing permissions and limitations under the License.
 *
 * Copyright 2012-2025 the original author or authors.
 */
package org.assertj.core.api.comparisonstrategy;

import static org.assertj.core.configuration.ConfigurationProvider.CONFIGURATION_PROVIDER;
import static org.assertj.core.util.IterableUtil.isNullOrEmpty;

import java.util.Collection;
import java.util.Comparator;
import java.util.Iterator;
import java.util.Set;
import java.util.TreeSet;

import org.assertj.core.internal.DescribableComparator;

/**
 * Implements {@link ComparisonStrategy} contract with a comparison strategy based on a {@link Comparator}.
 *
 * @author Joel Costigliola
 */
public class ComparatorBasedComparisonStrategy extends AbstractComparisonStrategy {

  public static final int NOT_EQUAL = -1;

  // A raw type is necessary because we can't make assumptions on object to be compared.
  @SuppressWarnings("rawtypes")
  private final Comparator comparator;

  // Comparator description used in assertion messages.
  private final String comparatorDescription;

  /**
   * Creates a new <code>{@link ComparatorBasedComparisonStrategy}</code> specifying the comparison strategy with given
   * comparator.
   *
   * @param comparator the comparison strategy to use.
   */
  public ComparatorBasedComparisonStrategy(@SuppressWarnings("rawtypes") Comparator comparator) {
    this(comparator, null);

  }

  /**
   * Creates a new <code>{@link ComparatorBasedComparisonStrategy}</code> specifying the comparison strategy with given
   * comparator and comparator description
   *
   * @param comparator the comparison strategy to use.
   * @param comparatorDescription the comparator description to use in assertion messages.
   */
  public ComparatorBasedComparisonStrategy(@SuppressWarnings("rawtypes") Comparator comparator,
                                           String comparatorDescription) {
    this.comparator = comparator;
    this.comparatorDescription = comparatorDescription;
  }

  /**
   * Creates a new <code>{@link ComparatorBasedComparisonStrategy}</code> specifying the comparison strategy with given
   * {@link DescribableComparator}.
   *
   * @param comparator the comparator to use in the comparison strategy.
   */
  public ComparatorBasedComparisonStrategy(DescribableComparator<?> comparator) {
    this(comparator, comparator.description());
  }

  /**
   * Returns true if given {@link Iterable} contains given value according to {@link #comparator}, false otherwise.<br>
   * If given {@link Iterable} is null or empty, return false.
   *
   * @param iterable the {@link Iterable} to search value in
   * @param value the object to look for in given {@link Iterable}
   * @return true if given {@link Iterable} contains given value according to {@link #comparator}, false otherwise.
   */
  @Override
  @SuppressWarnings("unchecked")
  public boolean iterableContains(Iterable<?> iterable, Object value) {
    if (isNullOrEmpty(iterable)) return false;
    for (Object element : iterable) {
      // avoid comparison when objects are the same or both null
      if (element == value) return true;
      // both objects are not null => if one is then the other is not => compare next element with value
      if (value == null || element == null) continue;
      if (comparator.compare(element, value) == 0) return true;
    }
    return false;
  }

  /**
   * Look for given value in given {@link Iterable} according to the {@link Comparator}, if value is found it is removed
   * from it.<br>
   * Does nothing if given {@link Iterable} is null (meaning no exception thrown).
   *
   * @param iterable the {@link Iterable} we want remove value from
   * @param value object to remove from given {@link Iterable}
   */
  @Override
  @SuppressWarnings("unchecked")
  public void iterableRemoves(Iterable<?> iterable, Object value) {
    if (iterable == null) return;
    // Avoid O(N^2) complexity of serial removal from an iterator of collections like ArrayList
    if (iterable instanceof Collection<?> collection) {
      collection.removeIf(o -> comparator.compare(o, value) == 0);
    } else {
      Iterator<?> iterator = iterable.iterator();
      while (iterator.hasNext()) {
        if (comparator.compare(iterator.next(), value) == 0) {
          iterator.remove();
        }
      }
    }
  }

  @Override
  @SuppressWarnings("unchecked")
  public void iterablesRemoveFirst(Iterable<?> iterable, Object value) {
    if (iterable == null) return;
    Iterator<?> iterator = iterable.iterator();
    while (iterator.hasNext()) {
      if (comparator.compare(iterator.next(), value) == 0) {
        iterator.remove();
        return;
      }
    }
  }

  /**
   * Returns true if actual and other are equal according to {@link #comparator}, false otherwise.<br>
   * Handles the cases where one of the parameter is null so that internal {@link #comparator} does not have too.
   *
   * @param actual the object to compare to other
   * @param other the object to compare to actual
   * @return true if actual and other are equal according to {@link #comparator}, false otherwise.
   */
  @Override
  @SuppressWarnings("unchecked")
  public boolean areEqual(Object actual, Object other) {
    // we don't check actual or expected for null, this should be done by the comparator, the rationale being that a
    // comparator might consider null to be equals to some special value (like blank String and null)
    return comparator.compare(actual, other) == 0;
  }

  /**
   * Returns any duplicate elements from the given {@link Iterable} according to {@link #comparator}.
   *
   * @param iterable the given {@link Iterable} we want to extract duplicate elements.
   * @return an {@link Iterable} containing the duplicate elements of the given one. If no duplicates are found, an
   *         empty {@link Iterable} is returned.
   */
  // overridden to write javadoc.
  @Override
  public Iterable<?> duplicatesFrom(Iterable<?> iterable) {
    return super.duplicatesFrom(iterable);
  }

  @SuppressWarnings("unchecked")
  @Override
  protected Set<Object> newSetUsingComparisonStrategy() {
    return new TreeSet<>(comparator);
  }

  @Override
  public String asText() {
    return "when comparing values using " + toString();
  }

  @Override
  public String toString() {
    return CONFIGURATION_PROVIDER.representation().toStringOf(this);
  }

  public Comparator<?> getComparator() {
    return comparator;
  }

  public String getComparatorDescription() {
    return comparatorDescription;
  }

  @Override
  @SuppressWarnings("unchecked")
  public boolean stringStartsWith(String string, String prefix) {
    if (string.length() < prefix.length()) return false;
    String stringPrefix = string.substring(0, prefix.length());
    return comparator.compare(stringPrefix, prefix) == 0;
  }

  @Override
  @SuppressWarnings("unchecked")
  public boolean stringEndsWith(String string, String suffix) {
    if (string.length() < suffix.length()) return false;
    String stringSuffix = string.substring(string.length() - suffix.length());
    return comparator.compare(stringSuffix, suffix) == 0;
  }

  @Override
  public boolean stringContains(String string, String sequence) {
    int sequenceLength = sequence.length();
    for (int i = 0; i < string.length(); i++) {
      String subString = string.substring(i);
      if (subString.length() < sequenceLength) return false;
      if (stringStartsWith(subString, sequence)) return true;
    }
    return false;
  }

  @Override
  @SuppressWarnings("unchecked")
  public boolean isGreaterThan(Object actual, Object other) {
    return comparator.compare(actual, other) > 0;
  }

  @Override
  public boolean isStandard() {
    return false;
  }
}
