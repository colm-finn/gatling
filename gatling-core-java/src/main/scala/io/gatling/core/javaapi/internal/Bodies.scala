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

package io.gatling.core.javaapi.internal

import io.gatling.core.body.{ Body => ScalaBody }
import io.gatling.core.javaapi.Body

object Bodies {

  def toJavaBody(scalaBody: ScalaBody): Body = scalaBody match {
    case b: io.gatling.core.body.StringBody      => new Body.WithString(b)
    case b: io.gatling.core.body.RawFileBody     => new Body.WithBytes(b)
    case b: io.gatling.core.body.ByteArrayBody   => new Body.WithBytes(b)
    case b: io.gatling.core.body.ElBody          => new Body.WithString(b)
    case b: io.gatling.core.body.InputStreamBody => new Body.Default(b)
  }
}
