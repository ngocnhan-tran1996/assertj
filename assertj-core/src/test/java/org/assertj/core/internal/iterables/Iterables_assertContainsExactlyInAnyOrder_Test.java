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
package org.assertj.core.internal.iterables;

import static java.util.Collections.emptyList;
import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatExceptionOfType;
import static org.assertj.core.api.Assertions.assertThatNullPointerException;
import static org.assertj.core.api.Assertions.catchThrowable;
import static org.assertj.core.error.ShouldContainExactlyInAnyOrder.shouldContainExactlyInAnyOrder;
import static org.assertj.core.internal.ErrorMessages.valuesToLookForIsNull;
import static org.assertj.core.testkit.ObjectArrays.emptyArray;
import static org.assertj.core.testkit.TestData.someInfo;
import static org.assertj.core.util.Arrays.array;
import static org.assertj.core.util.FailureMessages.actualIsNull;
import static org.assertj.core.util.Lists.newArrayList;
import static org.mockito.Mockito.verify;

import org.assertj.core.api.AssertionInfo;
import org.assertj.core.internal.Iterables;
import org.assertj.core.internal.IterablesBaseTest;
import org.assertj.core.api.comparisonstrategy.StandardComparisonStrategy;
import org.junit.jupiter.api.Test;

/**
 * Tests for <code>{@link Iterables#assertContainsExactlyInAnyOrder(AssertionInfo, Iterable, Object[])} </code>.
 *
 * @author Lovro Pandzic
 */
class Iterables_assertContainsExactlyInAnyOrder_Test extends IterablesBaseTest {

  @Test
  void should_pass_if_actual_contains_exactly_given_values() {
    iterables.assertContainsExactlyInAnyOrder(someInfo(), actual, array("Luke", "Yoda", "Leia"));
  }

  @Test
  void should_pass_if_actual_contains_given_values_exactly_with_null_elements() {
    iterables.assertContainsExactlyInAnyOrder(someInfo(), actual, array("Leia", "Yoda", "Luke"));
    actual.add(null);
    iterables.assertContainsExactlyInAnyOrder(someInfo(), actual, array("Leia", null, "Yoda", "Luke"));
  }

  @Test
  void should_pass_if_actual_and_given_values_are_empty() {
    actual.clear();
    iterables.assertContainsExactlyInAnyOrder(someInfo(), actual, array());
  }

  @Test
  void should_fail_if_array_of_values_to_look_for_is_empty_and_actual_is_not() {
    assertThatExceptionOfType(AssertionError.class).isThrownBy(() -> iterables.assertContainsExactlyInAnyOrder(someInfo(), actual,
                                                                                                               emptyArray()));
  }

  @Test
  void should_fail_if_expected_is_null() {
    assertThatNullPointerException().isThrownBy(() -> iterables.assertContainsExactlyInAnyOrder(someInfo(), emptyList(),
                                                                                                null))
                                    .withMessage(valuesToLookForIsNull());
  }

  @Test
  void should_fail_if_actual_is_null() {
    assertThatExceptionOfType(AssertionError.class).isThrownBy(() -> iterables.assertContainsExactlyInAnyOrder(someInfo(), null,
                                                                                                               emptyArray()))
                                                   .withMessage(actualIsNull());
  }

  @Test
  void should_fail_if_actual_does_not_contain_given_values_exactly() {
    AssertionInfo info = someInfo();
    Object[] expected = { "Luke", "Yoda", "Han" };

    Throwable error = catchThrowable(() -> iterables.assertContainsExactlyInAnyOrder(info, actual, expected));

    assertThat(error).isInstanceOf(AssertionError.class);
    verify(failures).failure(info,
                             shouldContainExactlyInAnyOrder(actual, expected, newArrayList("Han"), newArrayList("Leia"),
                                                            StandardComparisonStrategy.instance()));
  }

  @Test
  void should_pass_if_actual_contains_all_given_values_in_different_order() {
    AssertionInfo info = someInfo();
    Object[] expected = { "Luke", "Leia", "Yoda" };
    iterables.assertContainsExactlyInAnyOrder(info, actual, expected);
  }

  @Test
  void should_fail_if_actual_contains_duplicates_and_expected_does_not() {
    AssertionInfo info = someInfo();
    actual = newArrayList("Luke", "Leia", "Luke");
    Object[] expected = { "Luke", "Leia" };

    Throwable error = catchThrowable(() -> iterables.assertContainsExactlyInAnyOrder(info, actual, expected));

    assertThat(error).isInstanceOf(AssertionError.class);
    verify(failures).failure(info,
                             shouldContainExactlyInAnyOrder(actual, expected, emptyList(), newArrayList("Luke"),
                                                            StandardComparisonStrategy.instance()));
  }

  @Test
  void should_fail_if_expected_contains_duplicates_and_actual_does_not() {
    AssertionInfo info = someInfo();
    actual = newArrayList("Luke", "Leia");
    Object[] expected = { "Luke", "Leia", "Luke" };

    Throwable error = catchThrowable(() -> iterables.assertContainsExactlyInAnyOrder(info, actual, expected));

    assertThat(error).isInstanceOf(AssertionError.class);
    verify(failures).failure(info,
                             shouldContainExactlyInAnyOrder(actual, expected, newArrayList("Luke"), emptyList(),
                                                            StandardComparisonStrategy.instance()));
  }

  // ------------------------------------------------------------------------------------------------------------------
  // tests using a custom comparison strategy
  // ------------------------------------------------------------------------------------------------------------------

  @Test
  void should_pass_if_actual_contains_given_values_exactly_according_to_custom_comparison_strategy() {
    iterablesWithCaseInsensitiveComparisonStrategy.assertContainsExactlyInAnyOrder(someInfo(), actual,
                                                                                   array("LUKE", "YODA", "Leia"));
  }

  @Test
  void should_fail_if_actual_does_not_contain_given_values_exactly_according_to_custom_comparison_strategy() {
    AssertionInfo info = someInfo();
    Object[] expected = { "Luke", "Yoda", "Han" };

    Throwable error = catchThrowable(() -> iterablesWithCaseInsensitiveComparisonStrategy.assertContainsExactlyInAnyOrder(info,
                                                                                                                          actual,
                                                                                                                          expected));

    assertThat(error).isInstanceOf(AssertionError.class);
    verify(failures).failure(info, shouldContainExactlyInAnyOrder(actual, expected, newArrayList("Han"),
                                                                  newArrayList("Leia"), comparisonStrategy));
  }

  @Test
  void should_pass_if_actual_contains_all_given_values_in_different_order_according_to_custom_comparison_strategy() {
    AssertionInfo info = someInfo();
    Object[] expected = { "Luke", "Leia", "Yoda" };
    iterablesWithCaseInsensitiveComparisonStrategy.assertContainsExactlyInAnyOrder(info, actual, expected);
  }

  @Test
  void should_fail_if_actual_contains_all_given_values_but_size_differ_according_to_custom_comparison_strategy() {
    AssertionInfo info = someInfo();
    actual = newArrayList("Luke", "Leia", "Luke");
    Object[] expected = { "LUKE", "Leia" };

    Throwable error = catchThrowable(() -> iterablesWithCaseInsensitiveComparisonStrategy.assertContainsExactlyInAnyOrder(info,
                                                                                                                          actual,
                                                                                                                          expected));

    assertThat(error).isInstanceOf(AssertionError.class);
    verify(failures).failure(info, shouldContainExactlyInAnyOrder(actual, expected, emptyList(), newArrayList("Luke"),
                                                                  comparisonStrategy));
  }

}
