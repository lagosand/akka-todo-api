import akka.http.scaladsl.model.{ContentTypes, HttpEntity, StatusCodes}
import akka.http.scaladsl.server.Directives.{get, path, complete}
import akka.http.scaladsl.testkit.ScalatestRouteTest
import org.scalatest.matchers.should.Matchers
import org.scalatest.wordspec.AnyWordSpec

final class ScalaHttpApiTest  extends AnyWordSpec with Matchers with ScalatestRouteTest:

  private val configuredRoutes =
    get {
      path("status") {
        complete(HttpEntity(ContentTypes.`application/json`, """{"status":"ok"}"""))
      }
    }

  "scala http api" should {
    "respond successful while requesting its status" in {
      Get("/status") ~> configuredRoutes ~> check {
        status shouldBe StatusCodes.OK
        contentType shouldBe ContentTypes.`application/json`
        entityAs[String] shouldBe """{"status":"ok"}"""
      }
    }
  }
