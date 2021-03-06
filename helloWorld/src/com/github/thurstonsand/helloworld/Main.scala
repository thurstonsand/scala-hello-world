package com.github.thurstonsand.helloworld

import cats.effect.IO
import com.twitter.finagle.Http
import com.twitter.finagle.Service
import com.twitter.finagle.http.Request
import com.twitter.finagle.http.Response
import com.twitter.util.Await
import io.finch._
import io.finch.catsEffect._
import io.finch.circe._
import io.circe.generic.JsonCodec

object Main extends App {

  @JsonCodec
  case class Message(hello: String)

  def healthcheck: Endpoint[IO, String] = get(pathEmpty) {
    Ok("OK")
  }

  def helloWorld: Endpoint[IO, Message] = get("hello") {
    Ok(Message("World"))
  }

  def hello: Endpoint[IO, Message] = get("hello" :: path[String]) { s: String =>
    Ok(Message(s))
  }

  def service: Service[Request, Response] =
    Bootstrap.serve[Text.Plain](healthcheck).serve[Application.Json](helloWorld :+: hello).toService

  Await.ready(Http.server.serve(":8081", service))
}
