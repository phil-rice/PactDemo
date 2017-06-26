package org.pactDemo.utilities

import com.twitter.finagle.http.Request
import com.twitter.finagle.{Http, Service}
import com.twitter.util.Future


class FinatraClient(host: String, port: Int, fn: String => String) extends Service[Int, String] {
  val service = Http.newService(s"$host:$port")

  override def apply(id: Int): Future[String] =
    service(Request(s"/id/$id")).map(res => fn(res.contentString))

}
