/*
 * Copyright 2011-2021 GatlingCorp (https://gatling.io)
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *  http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package io.gatling.core.javaapi.condition;

import io.gatling.core.javaapi.Possibility;
import io.gatling.core.javaapi.Session;
import io.gatling.core.javaapi.StructureBuilder;
import io.gatling.core.javaapi.internal.condition.ScalaDoSwitch;
import java.util.Arrays;
import java.util.List;
import java.util.function.Function;
import javax.annotation.Nonnull;

/**
 * Methods for defining "doSwitch" conditional blocks.
 *
 * <p>Important: instances are immutable so any method doesn't mutate the existing instance but
 * returns a new one.
 *
 * @param <T> the type of {@link StructureBuilder} to attach to and to return
 * @param <W> the type of wrapped Scala instance
 */
public interface DoSwitch<
    T extends StructureBuilder<T, W>, W extends io.gatling.core.structure.StructureBuilder<W>> {

  T make(Function<W, W> f);

  /**
   * Execute one of the "possibilities" when the actual value is equal to the possibility's one.
   *
   * @param actual the actual value expressed as a Gatling Expression Language String
   * @return a DSL component for defining the "possibilities"
   */
  @Nonnull
  default Possibilities<T> doSwitch(@Nonnull String actual) {
    return new Possibilities<>(ScalaDoSwitch.apply(this, actual));
  }

  /**
   * Execute one of the "possibilities" when the actual value is equal to the possibility's one.
   *
   * @param actual the actual value expressed as a function
   * @return a DSL component for defining the "possibilities"
   */
  @Nonnull
  default Possibilities<T> doSwitch(@Nonnull Function<Session, Object> actual) {
    return new Possibilities<>(ScalaDoSwitch.apply(this, actual));
  }

  /**
   * The DSL component for defining the "possibilities"
   *
   * @param <T> the type of {@link StructureBuilder} to attach to and to return
   */
  final class Possibilities<T extends StructureBuilder<T, ?>> {
    private final ScalaDoSwitch.Then<T, ?> wrapped;

    Possibilities(ScalaDoSwitch.Then<T, ?> wrapped) {
      this.wrapped = wrapped;
    }

    /**
     * Define the "possibilities"
     *
     * @param possibilities the possibilities
     * @return a new {@link StructureBuilder}
     */
    @Nonnull
    public T possibilities(@Nonnull Possibility.WithValue... possibilities) {
      return possibilities(Arrays.asList(possibilities));
    }

    /**
     * Define the "possibilities"
     *
     * @param possibilities the possibilities
     * @return a new {@link StructureBuilder}
     */
    @Nonnull
    public T possibilities(@Nonnull List<Possibility.WithValue> possibilities) {
      return wrapped.possibilities(possibilities);
    }
  }
}
