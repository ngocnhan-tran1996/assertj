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
package org.assertj.core.api.long2darray;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.testkit.AlwaysEqualComparator.alwaysEqual;

import org.assertj.core.api.Long2DArrayAssert;
import org.assertj.core.api.Long2DArrayAssertBaseTest;
import org.assertj.core.testkit.AlwaysEqualComparator;
import org.junit.jupiter.api.Test;

class Long2DArrayAssert_usingCustomComparator_Test extends Long2DArrayAssertBaseTest {

  private static final AlwaysEqualComparator<long[][]> ALWAYS_EQUAL = alwaysEqual();

  @Override
  protected Long2DArrayAssert invoke_api_method() {
    return assertions.usingComparator(ALWAYS_EQUAL);
  }

  @Override
  protected void verify_internal_effects() {
    assertThat(getObjects(assertions).getComparator()).isSameAs(ALWAYS_EQUAL);
  }

  @Test
  void should_honor_comparator() {
    assertThat(new long[][] {}).usingComparator(ALWAYS_EQUAL)
                               .isEqualTo(new long[][] { { 1L, 2L }, { 3L, 4L } });
  }

}
