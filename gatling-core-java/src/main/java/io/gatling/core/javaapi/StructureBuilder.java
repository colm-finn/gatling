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

package io.gatling.core.javaapi;

import io.gatling.core.javaapi.condition.*;
import io.gatling.core.javaapi.error.Errors;
import io.gatling.core.javaapi.exec.Execs;
import io.gatling.core.javaapi.feed.Feeds;
import io.gatling.core.javaapi.group.Groups;
import io.gatling.core.javaapi.loop.*;
import io.gatling.core.javaapi.pause.Paces;
import io.gatling.core.javaapi.pause.Pauses;
import io.gatling.core.javaapi.pause.RendezVous;

/**
 * The parent class of {@link ScenarioBuilder} and {@link ChainBuilder}.
 *
 * <p>For internal use only, do not extend!
 *
 * @param <T> the type of {@link StructureBuilder} to attach to and to return
 * @param <W> the type of wrapped Scala instance
 */
public abstract class StructureBuilder<
        T extends StructureBuilder<T, W>, W extends io.gatling.core.structure.StructureBuilder<W>>
    implements Execs<T, W>,
        Groups<T, W>,
        Feeds<T, W>,
        Pauses<T, W>,
        Paces<T, W>,
        RendezVous<T, W>,
        Repeat<T, W>,
        ForEach<T, W>,
        During<T, W>,
        Forever<T, W>,
        AsLongAs<T, W>,
        DoWhile<T, W>,
        AsLongAsDuring<T, W>,
        DoWhileDuring<T, W>,
        DoIf<T, W>,
        DoIfOrElse<T, W>,
        DoIfEquals<T, W>,
        DoIfEqualsOrElse<T, W>,
        DoSwitch<T, W>,
        DoSwitchOrElse<T, W>,
        RandomSwitch<T, W>,
        RandomSwitchOrElse<T, W>,
        UniformRandomSwitch<T, W>,
        RoundRobinSwitch<T, W>,
        Errors<T, W> {

  public final W wrapped;

  protected StructureBuilder(W wrapped) {
    this.wrapped = wrapped;
  }
}
