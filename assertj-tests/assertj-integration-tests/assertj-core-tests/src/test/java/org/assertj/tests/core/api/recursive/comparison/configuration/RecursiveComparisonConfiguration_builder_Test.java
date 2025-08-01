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
package org.assertj.tests.core.api.recursive.comparison.configuration;

import static org.assertj.core.api.Assertions.catchThrowable;
import static org.assertj.core.api.BDDAssertions.then;
import static org.assertj.core.presentation.StandardRepresentation.STANDARD_REPRESENTATION;
import static org.assertj.tests.core.testkit.AlwaysEqualComparator.alwaysEqual;
import static org.assertj.tests.core.testkit.BiPredicates.STRING_EQUALS;

import java.util.Comparator;
import java.util.Set;
import java.util.function.BiPredicate;
import java.util.regex.Pattern;

import org.apache.commons.lang3.RandomUtils;
import org.assertj.core.api.recursive.comparison.FieldLocation;
import org.assertj.core.api.recursive.comparison.RecursiveComparisonConfiguration;
import org.assertj.core.api.recursive.comparison.RecursiveComparisonConfiguration.Builder;
import org.assertj.core.api.recursive.comparison.RecursiveComparisonIntrospectionStrategy;
import org.assertj.tests.core.testkit.AlwaysEqualComparator;
import org.junit.jupiter.api.Test;

class RecursiveComparisonConfiguration_builder_Test {

  @Test
  void should_use_same_defaults_as_constructor() {
    // GIVEN
    RecursiveComparisonConfiguration configurationBuiltWithBuilder = configBuilder().build();
    RecursiveComparisonConfiguration configurationBuiltWithConstructor = new RecursiveComparisonConfiguration();
    // WHEN/THEN
    then(configurationBuiltWithBuilder).isEqualTo(configurationBuiltWithConstructor);
  }

  @Test
  void should_set_ignoreAllActualNullFields() {
    // GIVEN
    boolean value = RandomUtils.secure().randomBoolean();
    // WHEN
    RecursiveComparisonConfiguration configuration = configBuilder().withIgnoreAllActualNullFields(value).build();
    // THEN
    then(configuration.getIgnoreAllActualNullFields()).isEqualTo(value);
  }

  @Test
  void should_set_ignoreAllActualEmptyOptionalFields() {
    // GIVEN
    boolean value = RandomUtils.secure().randomBoolean();
    // WHEN
    RecursiveComparisonConfiguration configuration = configBuilder().withIgnoreAllActualEmptyOptionalFields(value).build();
    // THEN
    then(configuration.getIgnoreAllActualEmptyOptionalFields()).isEqualTo(value);
  }

  @Test
  void should_set_ignoreAllExpectedNullFields() {
    // GIVEN
    boolean value = RandomUtils.secure().randomBoolean();
    // WHEN
    RecursiveComparisonConfiguration configuration = configBuilder().withIgnoreAllExpectedNullFields(value).build();
    // THEN
    then(configuration.getIgnoreAllExpectedNullFields()).isEqualTo(value);
  }

  @Test
  void should_set_ignoreAllOverriddenEquals() {
    // GIVEN
    boolean value = RandomUtils.secure().randomBoolean();
    // WHEN
    RecursiveComparisonConfiguration configuration = configBuilder().withIgnoreAllOverriddenEquals(value).build();
    // THEN
    then(configuration.getIgnoreAllOverriddenEquals()).isEqualTo(value);
  }

  @Test
  void should_set_ignoreCollectionOrder() {
    // GIVEN
    boolean value = RandomUtils.secure().randomBoolean();
    // WHEN
    RecursiveComparisonConfiguration configuration = configBuilder().withIgnoreCollectionOrder(value).build();
    // THEN
    then(configuration.getIgnoreCollectionOrder()).isEqualTo(value);
  }

  @Test
  void should_set_ignoreArrayOrder() {
    // GIVEN
    boolean value = RandomUtils.secure().randomBoolean();
    // WHEN
    RecursiveComparisonConfiguration configuration = configBuilder().withIgnoreArrayOrder(value).build();
    // THEN
    then(configuration.shouldIgnoreArrayOrder()).isEqualTo(value);
  }

  @Test
  void should_set_ignoreCollectionOrderInFields() {
    // GIVEN
    String[] values = { "foo", "bar" };
    // WHEN
    RecursiveComparisonConfiguration configuration = configBuilder().withIgnoredCollectionOrderInFields(values).build();
    // THEN
    then(configuration.getIgnoredCollectionOrderInFields()).containsExactly(values);
  }

  @Test
  void should_set_ignoreCollectionOrderInFieldsMatchingRegexes() {
    // GIVEN
    String[] values = { "foo", "bar" };
    // WHEN
    RecursiveComparisonConfiguration configuration = configBuilder().withIgnoredCollectionOrderInFieldsMatchingRegexes(values)
                                                                    .build();
    // THEN
    then(configuration.getIgnoredCollectionOrderInFieldsMatchingRegexes()).extracting(Pattern::pattern)
                                                                          .containsExactly(values);
  }

  @Test
  void should_set_comparedFields() {
    // GIVEN
    String[] values = { "foo", "bar" };
    // WHEN
    RecursiveComparisonConfiguration configuration = configBuilder().withComparedFields(values).build();
    // THEN
    then(configuration.getComparedFields()).containsExactly(new FieldLocation("foo"), new FieldLocation("bar"));
  }

  @Test
  void should_set_comparedTypes() {
    // GIVEN
    Class<?>[] values = { String.class, double.class };
    // WHEN
    RecursiveComparisonConfiguration configuration = configBuilder().withComparedTypes(values).build();
    // THEN
    then(configuration.getComparedTypes()).containsExactly(String.class, double.class);
  }

  @Test
  void should_set_ignoredFields() {
    // GIVEN
    String[] values = { "foo", "bar" };
    // WHEN
    RecursiveComparisonConfiguration configuration = configBuilder().withIgnoredFields(values).build();
    // THEN
    then(configuration.getIgnoredFields()).containsExactly(values);
  }

  @Test
  void should_set_ignoredFieldsRegexes() {
    // GIVEN
    String[] values = { "foo", "bar" };
    // WHEN
    RecursiveComparisonConfiguration configuration = configBuilder().withIgnoredFieldsMatchingRegexes(values).build();
    // THEN
    then(configuration.getIgnoredFieldsRegexes()).extracting(Pattern::pattern)
                                                 .containsExactly(values);
  }

  @Test
  void should_set_ignoreTransientFields() {
    // WHEN
    RecursiveComparisonConfiguration configuration = configBuilder().withIgnoredTransientFields().build();
    // THEN
    then(configuration.getIgnoreTransientFields()).isTrue();
  }

  @Test
  void should_set_ignoredOverriddenEqualsForFields() {
    // GIVEN
    String[] values = { "foo", "bar" };
    // WHEN
    RecursiveComparisonConfiguration configuration = configBuilder().withIgnoredOverriddenEqualsForFields(values).build();
    // THEN
    then(configuration.getIgnoredOverriddenEqualsForFields()).containsExactly(values);
  }

  @Test
  void should_set_ignoredOverriddenEqualsForTypes() {
    // GIVEN
    Class<?>[] values = { String.class, Long.class, int.class };
    // WHEN
    RecursiveComparisonConfiguration configuration = configBuilder().withIgnoredOverriddenEqualsForTypes(values).build();
    // THEN
    then(configuration.getIgnoredOverriddenEqualsForTypes()).containsExactly(String.class, Long.class, int.class);
  }

  @Test
  void should_set_ignoredOverriddenEqualsRegexes() {
    // GIVEN
    String[] values = { "foo", "bar" };
    // WHEN
    RecursiveComparisonConfiguration configuration = configBuilder().withIgnoredOverriddenEqualsForFieldsMatchingRegexes(values)
                                                                    .build();
    // THEN
    then(configuration.getIgnoredOverriddenEqualsForFieldsMatchingRegexes()).extracting(Pattern::pattern)
                                                                            .containsExactly(values);
  }

  @Test
  void should_set_strictTypeCheckingMode() {
    // GIVEN
    boolean value = RandomUtils.secure().randomBoolean();
    // WHEN
    RecursiveComparisonConfiguration configuration = configBuilder().withStrictTypeChecking(value).build();
    // THEN
    then(configuration.isInStrictTypeCheckingMode()).isEqualTo(value);
  }

  @Test
  void should_set_ignoredTypes() {
    // GIVEN
    Class<?>[] values = { String.class, Long.class, Object.class, int.class };
    // WHEN
    RecursiveComparisonConfiguration configuration = configBuilder().withIgnoredFieldsOfTypes(values).build();
    // THEN
    then(configuration.getIgnoredTypes()).containsExactly(String.class, Long.class, Object.class, Integer.class);
  }

  @Test
  void should_set_comparatorForField() {
    // GIVEN
    String fooLocation = "foo";
    String barLocation = "foo.bar";
    AlwaysEqualComparator<?> alwaysEqualComparator = alwaysEqual();
    // WHEN
    RecursiveComparisonConfiguration configuration = configBuilder().withComparatorForFields(alwaysEqualComparator, fooLocation,
                                                                                             barLocation)
                                                                    .build();
    // THEN
    then(configuration.hasComparatorForField(fooLocation)).isTrue();
    then(configuration.getComparatorForField(fooLocation)).isSameAs(alwaysEqualComparator);
    then(configuration.hasComparatorForField(barLocation)).isTrue();
    then(configuration.getComparatorForField(barLocation)).isSameAs(alwaysEqualComparator);
  }

  @Test
  void should_throw_NPE_if_given_comparator_for_fields_is_null() {
    // GIVEN
    Comparator<Integer> integerComparator = null;
    // WHEN
    Throwable throwable = catchThrowable(() -> configBuilder().withComparatorForFields(integerComparator, "age"));
    // THEN
    then(throwable).isInstanceOf(NullPointerException.class)
                   .hasMessage("Expecting a non null Comparator");
  }

  @Test
  void should_throw_NPE_if_given_comparator_for_type_is_null() {
    // GIVEN
    Comparator<Integer> integerComparator = null;
    // WHEN
    Throwable throwable = catchThrowable(() -> configBuilder().withComparatorForType(integerComparator, Integer.class));
    // THEN
    then(throwable).isInstanceOf(NullPointerException.class)
                   .hasMessage("Expecting a non null Comparator");
  }

  @Test
  void should_throw_NPE_if_given_BiPredicate_for_type_is_null() {
    // GIVEN
    BiPredicate<String, String> stringEquals = null;
    // WHEN
    Throwable throwable = catchThrowable(() -> configBuilder().withEqualsForType(stringEquals, String.class));
    // THEN
    then(throwable).isInstanceOf(NullPointerException.class)
                   .hasMessage("Expecting a non null BiPredicate");
  }

  @Test
  void should_throw_NPE_if_given_BiPredicate_for_fields_is_null() {
    // GIVEN
    BiPredicate<String, String> stringEquals = null;
    // WHEN
    Throwable throwable = catchThrowable(() -> configBuilder().withEqualsForFields(stringEquals, "id"));
    // THEN
    then(throwable).isInstanceOf(NullPointerException.class)
                   .hasMessage("Expecting a non null BiPredicate");
  }

  @Test
  void should_throw_NPE_if_given_BiPredicate_for_fields_matching_regexes_is_null() {
    // GIVEN
    BiPredicate<String, String> stringEquals = null;
    // WHEN
    Throwable throwable = catchThrowable(() -> configBuilder().withEqualsForFieldsMatchingRegexes(stringEquals, ".*id"));
    // THEN
    then(throwable).isInstanceOf(NullPointerException.class)
                   .hasMessage("Expecting a non null BiPredicate");
  }

  @Test
  void should_set_equalsForField() {
    // GIVEN
    String nameLocation = "name";
    String titleLocation = "title";
    // WHEN
    RecursiveComparisonConfiguration configuration = configBuilder().withEqualsForFields(STRING_EQUALS, nameLocation,
                                                                                         titleLocation)
                                                                    .build();
    // THEN
    then(configuration.hasComparatorForField(nameLocation)).isTrue();
    then(configuration.hasComparatorForField(titleLocation)).isTrue();
  }

  @Test
  void should_set_equals_for_fields_matching_regexes() {
    // GIVEN
    String nameRegex = ".*name";
    String titleRegex = ".*title";
    // WHEN
    RecursiveComparisonConfiguration configuration = configBuilder().withEqualsForFieldsMatchingRegexes(STRING_EQUALS, nameRegex,
                                                                                                        titleRegex)
                                                                    .build();
    // THEN
    then(configuration.hasComparatorForField("name")).isTrue();
    then(configuration.hasComparatorForField("firstname")).isTrue();
    then(configuration.hasComparatorForField("lastname")).isTrue();
    then(configuration.hasComparatorForField("title")).isTrue();
    then(configuration.hasComparatorForField("job.title")).isTrue();
  }

  @Test
  void should_set_comparatorForType() {
    // GIVEN
    AlwaysEqualComparator<String> alwaysEqualComparator = alwaysEqual();
    // WHEN
    RecursiveComparisonConfiguration configuration = configBuilder().withComparatorForType(alwaysEqualComparator, String.class)
                                                                    .build();
    // THEN
    then(configuration.getComparatorForDualType(String.class)).isSameAs(alwaysEqualComparator);
  }

  @Test
  void should_set_equalsForType() {
    // WHEN
    RecursiveComparisonConfiguration configuration = configBuilder().withEqualsForType(STRING_EQUALS, String.class).build();
    // THEN
    then(configuration.getComparatorForDualType(String.class)).isNotNull();
  }

  @Test
  void should_set_message_for_field() {
    // GIVEN
    String nameLocation = "name";
    String titleLocation = "title";
    String message = "error message";
    // WHEN
    RecursiveComparisonConfiguration configuration = configBuilder().withErrorMessageForFields(message, nameLocation,
                                                                                               titleLocation)
                                                                    .build();
    // THEN
    then(configuration.hasCustomMessageForField(nameLocation)).isTrue();
    then(configuration.getMessageForField(nameLocation)).isEqualTo(message);

    then(configuration.hasCustomMessageForField(titleLocation)).isTrue();
    then(configuration.getMessageForField(titleLocation)).isEqualTo(message);
  }

  @Test
  void should_set_message_for_type() {
    // GIVEN
    String message = "error message";
    // WHEN
    RecursiveComparisonConfiguration configuration = configBuilder().withErrorMessageForType(message, String.class).build();
    // THEN
    then(configuration.hasCustomMessageForType(String.class)).isTrue();
    then(configuration.getMessageForType(String.class)).isEqualTo(message);
  }

  @Test
  void should_set_introspection_strategy() {
    // GIVEN
    RecursiveComparisonIntrospectionStrategy myIntrospectionStrategy = new RecursiveComparisonIntrospectionStrategy() {
      @Override
      public Set<String> getChildrenNodeNamesOf(Object node) {
        return null;
      }

      @Override
      public Object getChildNodeValue(String childNodeName, Object instance) {
        return null;
      }
    };
    // WHEN
    RecursiveComparisonConfiguration configuration = configBuilder().withIntrospectionStrategy(myIntrospectionStrategy).build();
    // THEN
    then(configuration.getIntrospectionStrategy()).isSameAs(myIntrospectionStrategy);
  }

  @Test
  void should_set_the_standard_representation_if_none_is_set() {
    // WHEN
    RecursiveComparisonConfiguration recursiveComparisonConfiguration = configBuilder().build();
    // THEN
    then(recursiveComparisonConfiguration.getRepresentation()).isSameAs(STANDARD_REPRESENTATION);
  }

  @Test
  void should_set_ignoreNonExistentFields() {
    // GIVEN
    boolean value = RandomUtils.secure().randomBoolean();
    // WHEN
    RecursiveComparisonConfiguration configuration = configBuilder().withIgnoreNonExistentComparedFields(value).build();
    // THEN
    then(configuration.getIgnoreNonExistentComparedFields()).isEqualTo(value);
  }

  @Test
  void should_set_ignoreNonExistentFields_with_shortcut_method() {
    // WHEN
    RecursiveComparisonConfiguration configuration = configBuilder().build();
    // THEN
    then(configuration.getIgnoreNonExistentComparedFields()).isFalse();
  }

  @Test
  void should_set_treatNullAndEmptyIterablesAsEqual() {
    // WHEN
    RecursiveComparisonConfiguration configuration = configBuilder().withTreatingNullAndEmptyIterablesAsEqual().build();
    // THEN
    then(configuration.isTreatingNullAndEmptyIterablesAsEqualEnabled()).isTrue();
  }

  private static Builder configBuilder() {
    return RecursiveComparisonConfiguration.builder();
  }

}
