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
package org.assertj.core.internal;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatIllegalArgumentException;

import org.assertj.core.util.Employee;
import org.junit.jupiter.api.Test;

import java.util.Locale;

/**
 * @author Joel Costigliola
 */
class StandardComparisonStrategy_isLessThan_Test extends AbstractTest_StandardComparisonStrategy {

  @Test
  void should_pass() {
    Employee boss = new Employee(10000, 35);
    Employee young = new Employee(10000, 25);
    assertThat(standardComparisonStrategy.isLessThan(young, boss)).isTrue();
    assertThat(standardComparisonStrategy.isLessThan(boss, young)).isFalse();
    assertThat(standardComparisonStrategy.isLessThan(boss, boss)).isFalse();
  }

  @Test
  void should_fail_if_a_parameter_is_not_comparable() {
    assertThatIllegalArgumentException().isThrownBy(() -> standardComparisonStrategy.isLessThan(Locale.ROOT, Locale.US));
  }

}
