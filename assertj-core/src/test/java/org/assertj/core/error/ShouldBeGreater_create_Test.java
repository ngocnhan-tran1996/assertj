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
package org.assertj.core.error;

import static java.lang.String.format;
import static org.assertj.core.api.BDDAssertions.then;
import static org.assertj.core.error.ShouldBeGreater.shouldBeGreater;

import org.assertj.core.description.Description;
import org.assertj.core.description.TextDescription;
import org.assertj.core.api.comparisonstrategy.ComparatorBasedComparisonStrategy;
import org.assertj.core.presentation.StandardRepresentation;
import org.assertj.core.testkit.AbsValueComparator;
import org.junit.jupiter.api.Test;

/**
 * Tests for  code>{@link ShouldBeGreater#create(Description, org.assertj.core.presentation.Representation)}</code.
 *
 * @author Alex Ruiz
 * @author Joel Costigliola
 */
class ShouldBeGreater_create_Test {

  @Test
  void should_create_error_message() {
    // GIVEN
    ErrorMessageFactory factory = shouldBeGreater(6, 8);
    // WHEN
    String message = factory.create(new TextDescription("Test"), new StandardRepresentation());
    // THEN
    then(message).isEqualTo(format("[Test] %n" +
                                   "Expecting actual:%n" +
                                   "  6%n" +
                                   "to be greater than:%n" +
                                   "  8%n"));
  }

  @Test
  void should_create_error_message_with_custom_comparison_strategy() {
    // GIVEN
    ErrorMessageFactory factory = shouldBeGreater(6, 8, new ComparatorBasedComparisonStrategy(new AbsValueComparator<Integer>()));
    // WHEN
    String message = factory.create(new TextDescription("Test"), new StandardRepresentation());
    // THEN
    then(message).isEqualTo(format("[Test] %n" +
                                   "Expecting actual:%n" +
                                   "  6%n" +
                                   "to be greater than:%n" +
                                   "  8%n" +
                                   "when comparing values using AbsValueComparator"));
  }
}
