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
package org.assertj.tests.core.internal.paths;

import static org.assertj.core.api.BDDAssertions.then;
import static org.assertj.core.error.ShouldBeRelativePath.shouldBeRelativePath;
import static org.assertj.core.util.FailureMessages.actualIsNull;
import static org.assertj.tests.core.util.AssertionsUtil.expectAssertionError;

import java.nio.file.Path;

import org.junit.jupiter.api.Test;

class Paths_assertIsRelative_Test extends PathsBaseTest {

  @Test
  void should_fail_if_actual_is_null() {
    // WHEN
    AssertionError error = expectAssertionError(() -> underTest.assertIsRelative(INFO, null));
    // THEN
    then(error).hasMessage(actualIsNull());
  }

  @Test
  void should_fail_if_actual_is_not_relative() {
    // GIVEN
    Path actual = tempDir.getRoot().resolve("absolute");
    // WHEN
    AssertionError error = expectAssertionError(() -> underTest.assertIsRelative(INFO, actual));
    // THEN
    then(error).hasMessage(shouldBeRelativePath(actual).create());
  }

  @Test
  void should_pass_if_actual_is_relative() {
    // GIVEN
    Path actual = Path.of("relative");
    // WHEN/THEN
    underTest.assertIsRelative(INFO, actual);
  }

}
