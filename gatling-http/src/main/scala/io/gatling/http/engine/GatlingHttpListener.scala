/*
 * Copyright 2011-2020 GatlingCorp (https://gatling.io)
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

package io.gatling.http.engine

import io.gatling.commons.util.Clock
import io.gatling.http.client.HttpListener
import io.gatling.http.engine.response.ResponseProcessor
import io.gatling.http.response.ResponseBuilder
import io.gatling.http.engine.tx.HttpTx

import com.typesafe.scalalogging._
import io.netty.buffer.ByteBuf
import io.netty.handler.codec.http.{ HttpHeaders, HttpResponseStatus }

/**
 * This class is the AsyncHandler that AsyncHttpClient needs to process a request's response
 *
 * It is part of the HttpRequestAction
 */
class GatlingHttpListener(tx: HttpTx, clock: Clock, responseProcessor: ResponseProcessor) extends HttpListener with LazyLogging {

  private val responseBuilder = new ResponseBuilder(tx.request)
  private var init = false
  private var done = false
  // [fl]
  //
  //
  //
  // [fl]

  override def onSend(wireRequestHeaders: HttpHeaders): Unit =
    if (!init) {
      init = true
      val now = clock.nowMillis
      responseBuilder.updateStart(now, wireRequestHeaders)
      // [fl]
      //
      //
      //
      // [fl]
    }

  // [fl]
  //
  //
  //
  //
  //
  //
  //
  //
  //
  //
  //
  //
  //
  //
  //
  //
  //
  //
  //
  //
  //
  //
  //
  //
  //
  // [fl]

  override def onHttpResponse(status: HttpResponseStatus, headers: HttpHeaders): Unit =
    if (!done) {
      responseBuilder.recordResponse(status, headers, clock.nowMillis)
    }

  override def onHttpResponseBodyChunk(chunk: ByteBuf, last: Boolean): Unit =
    if (!done) {
      responseBuilder.recordBodyChunk(chunk, clock.nowMillis)
      if (last) {
        done = true
        try {
          responseProcessor.onComplete(responseBuilder.buildResponse)
        } finally {
          responseBuilder.releaseChunks()
        }
      }
    }

  override def onThrowable(throwable: Throwable): Unit = {
    responseBuilder.updateEndTimestamp(clock.nowMillis)
    logger.info(s"Request '${tx.request.requestName}' failed for user ${tx.session.userId}", throwable)
    try {
      responseProcessor.onComplete(responseBuilder.buildFailure(throwable))
    } finally {
      responseBuilder.releaseChunks()
    }
  }

  override def onProtocolAwareness(isHttp2: Boolean): Unit =
    responseBuilder.setHttp2(isHttp2)
}
