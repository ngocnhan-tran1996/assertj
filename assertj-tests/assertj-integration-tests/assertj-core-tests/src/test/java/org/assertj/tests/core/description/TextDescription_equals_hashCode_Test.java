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
package org.assertj.tests.core.description;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.tests.core.testkit.EqualsHashCodeContractAssert.assertEqualsIsReflexive;
import static org.assertj.tests.core.testkit.EqualsHashCodeContractAssert.assertEqualsIsSymmetric;
import static org.assertj.tests.core.testkit.EqualsHashCodeContractAssert.assertEqualsIsTransitive;
import static org.assertj.tests.core.testkit.EqualsHashCodeContractAssert.assertMaintainsEqualsAndHashCodeContract;

import org.assertj.core.description.TextDescription;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

/**
 * @author Alex Ruiz
 */
class TextDescription_equals_hashCode_Test {

  private static TextDescription description;

  @BeforeAll
  public static void setUpOnce() {
    description = new TextDescription("Yoda");
  }

  @Test
  void should_have_reflexive_equals() {
    assertEqualsIsReflexive(description);
  }

  @Test
  void should_have_symmetric_equals() {
    assertEqualsIsSymmetric(description, new TextDescription("Yoda"));
  }

  @Test
  void should_have_transitive_equals() {
    assertEqualsIsTransitive(description, new TextDescription("Yoda"), new TextDescription("Yoda"));
  }

  @Test
  void should_maintain_equals_and_hashCode_contract() {
    assertMaintainsEqualsAndHashCodeContract(description, new TextDescription("Yoda"));
  }

  @Test
  void should_not_be_equal_to_Object_of_different_type() {
    assertThat(description.equals("Yoda")).isFalse();
  }

  @Test
  void should_not_be_equal_to_null() {
    assertThat(description.equals(null)).isFalse();
  }

  @Test
  void should_not_be_equal_to_TextDescription_with_different_value() {
    assertThat(description.equals(new TextDescription("Luke"))).isFalse();
  }
}
