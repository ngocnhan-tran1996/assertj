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
import static org.assertj.core.error.ShouldHaveRootCauseExactlyInstance.shouldHaveRootCauseExactlyInstance;
import static org.assertj.core.util.Throwables.getStackTrace;

import java.io.IOException;

import org.assertj.core.internal.TestDescription;
import org.junit.jupiter.api.Test;

class ShouldHaveRootCauseExactlyInstance_create_Test {

  private static final TestDescription DESCRIPTION = new TestDescription("TEST");

  @Test
  void should_create_error_message_when_actual_has_no_root_cause() {
    // GIVEN
    Throwable actual = new RuntimeException();
    // WHEN
    String message = shouldHaveRootCauseExactlyInstance(actual, IOException.class).create(DESCRIPTION);
    // THEN
    then(message).isEqualTo(format("[TEST] %n"
                                   + "Expecting a throwable with root cause being exactly an instance of:%n"
                                   + "  java.io.IOException%n"
                                   + "but current throwable has no cause."
                                   + "%n"
                                   + "Throwable that failed the check:%n"
                                   + "%s",
                                   getStackTrace(actual)));
  }

  @Test
  void should_create_error_message_when_actual_has_a_root_cause() {
    // GIVEN
    Throwable rootCause = new IllegalStateException();
    Throwable cause = new IllegalArgumentException(rootCause);
    Throwable actual = new RuntimeException(cause);
    // WHEN
    String message = shouldHaveRootCauseExactlyInstance(actual, IOException.class).create(DESCRIPTION);
    // THEN
    then(message).isEqualTo(format("[TEST] %n"
                                   + "Expecting a throwable with root cause being exactly an instance of:%n"
                                   + "  java.io.IOException%n"
                                   + "but was an instance of:%n"
                                   + "  java.lang.IllegalStateException%n"
                                   + "%n"
                                   + "Throwable that failed the check:%n"
                                   + "%s",
                                   getStackTrace(actual)));
  }

}
