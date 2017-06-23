package org.pactDemo

import com.twitter.finagle.Http
import com.twitter.finagle.http.{Request, Response}
import com.twitter.finatra.http.{Controller, HttpServer}
import com.twitter.finatra.http.filters.{CommonFilters, LoggingMDCFilter, TraceIdMDCFilter}
import com.twitter.finatra.http.routing.HttpRouter
import com.twitter.finatra.request.{QueryParam, RouteParam}
import org.pactDemo.utilities.FinatraServer

case class IosRequest(@RouteParam id: Int)


class IosAppController extends Controller {
  val service = Http.newService("localhost:9000")
  get("/id/:id") { request: IosRequest =>
    import request._
    service(Request(s"/id/$id")).map(res => response.ok(res.contentString.replace("}", ""","server":"ios"}""")).contentType("application/json"))
  }
}

object IosApp extends App {
  new FinatraServer(9020, new IosAppController).main(Array())
}
