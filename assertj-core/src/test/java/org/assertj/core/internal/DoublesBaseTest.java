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

import static org.assertj.core.testkit.TestData.someInfo;
import static org.mockito.Mockito.spy;

import org.assertj.core.api.WritableAssertionInfo;
import org.assertj.core.api.comparisonstrategy.ComparatorBasedComparisonStrategy;
import org.assertj.core.testkit.AbsValueComparator;
import org.junit.jupiter.api.BeforeEach;

/**
 * Base class for tests involving {@link Doubles}
 * <p>
 * Is in <code>org.assertj.core.internal</code> package to be able to set {@link Doubles#failures} appropriately.
 *
 * @author Joel Costigliola
 */
public class DoublesBaseTest {

  protected static final WritableAssertionInfo INFO = someInfo();

  protected Failures failures;
  protected Doubles doubles;

  protected ComparatorBasedComparisonStrategy absValueComparisonStrategy;
  protected Doubles doublesWithAbsValueComparisonStrategy;

  @BeforeEach
  public void setUp() {
    failures = spy(Failures.instance());
    doubles = new Doubles();
    doubles.setFailures(failures);
    absValueComparisonStrategy = new ComparatorBasedComparisonStrategy(new AbsValueComparator<Double>());
    doublesWithAbsValueComparisonStrategy = new Doubles(absValueComparisonStrategy);
    doublesWithAbsValueComparisonStrategy.failures = failures;
  }

  protected Double NaN() {
    return doubles.NaN();
  }

  protected Double absDiff(Double actual, Double other) {
    return Doubles.instance().absDiff(actual, other);
  }
}
