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
package org.assertj.guava.error;

import org.assertj.core.error.BasicErrorMessageFactory;
import org.assertj.core.error.ErrorMessageFactory;

import com.google.common.base.Optional;

/**
 * 
 * Creates an error message indicating that an Optional should contain an expected value
 * 
 * @author Kornel Kiełczewski
 * @author Joel Costigliola
 */
public final class OptionalShouldBePresentWithValue extends BasicErrorMessageFactory {

  public static <T> ErrorMessageFactory shouldBePresentWithValue(final Optional<T> actual, final Object value) {
    return new OptionalShouldBePresentWithValue("%nExpecting Optional to contain value %n  %s%n but contained %n  %s",
                                                value, actual.get());
  }

  public static <T> ErrorMessageFactory shouldBePresentWithValue(final Object value) {
    return new OptionalShouldBePresentWithValue(
                                                "Expecting Optional to contain %s but contained nothing (absent Optional)",
                                                value);
  }

  private OptionalShouldBePresentWithValue(final String format, final Object... arguments) {
    super(format, arguments);
  }

}
