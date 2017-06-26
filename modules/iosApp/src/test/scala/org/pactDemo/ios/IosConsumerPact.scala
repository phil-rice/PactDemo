package org.pactDemo.ios

import com.twitter.util.{Await, Future}
import org.pactDemo.utilities.FinatraClient
import org.scalatest.{FunSpec, Matchers}
import org.pactDemo.utilities.Futures._

class IosConsumerPact extends FunSpec with Matchers {

  import com.itv.scalapact.ScalaPactForger._


  //  implicit val formats = DefaultFormats

  describe("Connecting to the Provider service") {

    it("should be able to fetch results") {


      forgePact
        .between("Ios")
        .and("Provider")
        .addInteraction(
          interaction
            .description("Fetching results")
            //            .given("Results: Bob, Fred, Harry")
            .uponReceiving("/id/1")
            .willRespondWith(200, """{"id":"1"}""")
        )
        .runConsumerTest {
          mockConfig =>
            val client = new FinatraClient(mockConfig.host, mockConfig.port, x => x)
            client(1).await
        }
    }
  }
}