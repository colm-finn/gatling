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

package io.gatling.core.javaapi.internal.loop

import java.{ lang => jl }
import java.time.Duration

import scala.concurrent.duration.FiniteDuration

import io.gatling.core.javaapi.{ ChainBuilder, StructureBuilder }
import io.gatling.core.javaapi.internal.Expressions._
import io.gatling.core.javaapi.internal.JavaExpression
import io.gatling.core.javaapi.loop.DoWhileDuring
import io.gatling.core.session.Expression
import io.gatling.core.session.el._

object ScalaDoWhileDuring {

  def apply[T <: StructureBuilder[T, W], W <: io.gatling.core.structure.StructureBuilder[W]](
      context: DoWhileDuring[T, W],
      condition: String,
      duration: String,
      counterName: String,
      exitASAP: Boolean
  ): Loop[T, W] =
    new Loop(context, condition.el, duration.el, counterName, exitASAP)

  def apply[T <: StructureBuilder[T, W], W <: io.gatling.core.structure.StructureBuilder[W]](
      context: DoWhileDuring[T, W],
      condition: JavaExpression[jl.Boolean],
      duration: JavaExpression[Duration],
      counterName: String,
      exitASAP: Boolean
  ): Loop[T, W] =
    new Loop(context, javaBooleanFunctionToExpression(condition), javaDurationFunctionToExpression(duration), counterName, exitASAP)

  final class Loop[T <: StructureBuilder[T, W], W <: io.gatling.core.structure.StructureBuilder[W]](
      context: DoWhileDuring[T, W],
      condition: Expression[Boolean],
      duration: Expression[FiniteDuration],
      counterName: String,
      exitASAP: Boolean
  ) {

    def loop(chain: ChainBuilder): T =
      context.make(_.asLongAsDuring(condition, duration, counterName, exitASAP)(chain.wrapped))
  }
}
