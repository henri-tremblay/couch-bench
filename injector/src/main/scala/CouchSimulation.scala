
import com.excilys.ebi.gatling.core.Predef._
import com.excilys.ebi.gatling.http.Predef._
import com.excilys.ebi.gatling.jdbc.Predef._
import com.excilys.ebi.gatling.http.Headers.Names._
import akka.util.duration._
import bootstrap._
import assertions._

class CouchSimulation extends Simulation {

  val users = Integer.getInteger("users", 1)
  val getUrl = "/couch-bench/get/${id}" // use "/${id}" for node
  val port = "8080" // use 1337 for node
  
  val httpConf = httpConfig
    .baseURL("http://192.168.10.3:" + port)
    .acceptHeader("text/html,application/xhtml+xml,application/xml;q=0.9,image/webp,*/*;q=0.8")
    .acceptEncodingHeader("gzip,deflate,sdch")
    .acceptLanguageHeader("en-US,en;q=0.8,fr;q=0.6")
    .userAgentHeader("Mozilla/5.0 (Windows NT 6.3; WOW64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/31.0.1650.63 Safari/537.36")

  val headers_1 = Map(
    "Cache-Control" -> """max-age=0"""
  )

  val scn = scenario("Scenario Name")
    .during(5 minutes) {
      feed(csv("ids.csv").random)
      .exec(http("get")
        .get(getUrl)
        .headers(headers_1)
        .check(regex("value"))
      )
    }
    
  setUp(scn.users(users).ramp(10).protocolConfig(httpConf))
}