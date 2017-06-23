package org.pactDemo

import com.twitter.finagle.Http
import com.twitter.finagle.http.{Request, Response}
import com.twitter.finatra.http.{Controller, HttpServer}
import com.twitter.finatra.http.filters.{CommonFilters, LoggingMDCFilter, TraceIdMDCFilter}
import com.twitter.finatra.http.routing.HttpRouter
import com.twitter.finatra.request.{QueryParam, RouteParam}
import org.pactDemo.utilities.FinatraServer

case class AndroidRequest(@RouteParam id: Int)


class AndroidController extends Controller {
  val service = Http.newService("localhost:9000")
  get("/id/:id") { request: AndroidRequest =>
    import request._
    service(Request(s"/id/$id")).
      map(res => response.ok(res.contentString.replace("}", ""","server":"android"}""")).contentType("application/json"))
  }
}

object AndroidApp extends App {

  new FinatraServer(9010, new AndroidController).main(Array())
}
