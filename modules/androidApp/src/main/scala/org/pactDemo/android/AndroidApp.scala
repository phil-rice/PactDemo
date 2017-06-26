package org.pactDemo.android

import com.twitter.finatra.http.Controller
import com.twitter.finatra.request.RouteParam
import org.pactDemo.utilities.{FinatraClient, FinatraServer}

case class AndroidRequest(@RouteParam id: Int)


class AndroidController extends Controller {
  val client = new FinatraClient("localhost", 9000, _.replace("}", ""","server":"android"}"""))
  get("/id/:id") { request: AndroidRequest =>
    client(request.id).map(response.ok(_).contentType("application/json"))
  }
}

object AndroidApp extends App {

  new FinatraServer(9010, new AndroidController).main(Array())
}
