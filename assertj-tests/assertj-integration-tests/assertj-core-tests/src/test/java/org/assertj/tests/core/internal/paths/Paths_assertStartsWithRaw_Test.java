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

import static java.nio.file.Files.createDirectory;
import static java.nio.file.Files.createFile;
import static org.assertj.core.api.Assertions.catchThrowable;
import static org.assertj.core.api.BDDAssertions.then;
import static org.assertj.core.error.ShouldStartWithPath.shouldStartWith;
import static org.assertj.core.util.FailureMessages.actualIsNull;
import static org.assertj.tests.core.util.AssertionsUtil.expectAssertionError;

import java.io.IOException;
import java.nio.file.Path;

import org.junit.jupiter.api.Test;

class Paths_assertStartsWithRaw_Test extends PathsBaseTest {

  @Test
  void should_fail_if_actual_is_null() throws IOException {
    // GIVEN
    Path other = createFile(tempDir.resolve("other"));
    // WHEN
    AssertionError error = expectAssertionError(() -> underTest.assertStartsWithRaw(INFO, null, other));
    // THEN
    then(error).hasMessage(actualIsNull());
  }

  @Test
  void should_fail_if_other_is_null() throws IOException {
    // GIVEN
    Path actual = createFile(tempDir.resolve("actual"));
    // WHEN
    Throwable thrown = catchThrowable(() -> underTest.assertStartsWithRaw(INFO, actual, null));
    // THEN
    then(thrown).isInstanceOf(NullPointerException.class)
                .hasMessage("the expected start path should not be null");
  }

  @Test
  void should_fail_if_actual_does_not_start_with_other() throws IOException {
    // GIVEN
    Path actual = createFile(tempDir.resolve("actual"));
    Path other = createFile(tempDir.resolve("other"));
    // WHEN
    AssertionError error = expectAssertionError(() -> underTest.assertStartsWithRaw(INFO, actual, other));
    // THEN
    then(error).hasMessage(shouldStartWith(actual, other).create());
  }

  @Test
  void should_pass_if_actual_starts_with_other() throws IOException {
    // GIVEN
    Path other = createDirectory(tempDir.resolve("other")).toRealPath();
    Path actual = createFile(other.resolve("actual")).toRealPath();
    // WHEN/THEN
    underTest.assertStartsWithRaw(INFO, actual, other);
  }

  @Test
  void should_fail_if_actual_is_not_canonical() throws IOException {
    // GIVEN
    Path other = createDirectory(tempDir.resolve("other"));
    Path file = createFile(other.resolve("file"));
    Path actual = tryToCreateSymbolicLink(tempDir.resolve("actual"), file);
    // WHEN
    AssertionError error = expectAssertionError(() -> underTest.assertStartsWithRaw(INFO, actual, other));
    // THEN
    then(error).hasMessage(shouldStartWith(actual, other).create());
  }

  @Test
  void should_fail_if_other_is_not_canonical() throws IOException {
    // GIVEN
    Path directory = createDirectory(tempDir.resolve("directory"));
    Path other = tryToCreateSymbolicLink(tempDir.resolve("other"), directory);
    Path actual = createFile(directory.resolve("actual"));
    // WHEN
    AssertionError error = expectAssertionError(() -> underTest.assertStartsWithRaw(INFO, actual, other));
    // THEN
    then(error).hasMessage(shouldStartWith(actual, other).create());
  }

}
