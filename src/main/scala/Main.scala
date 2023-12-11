import scala.io.StdIn
import scala.concurrent.{ExecutionContextExecutor, Future}
import com.typesafe.config.ConfigFactory
import akka.actor.typed.ActorSystem
import akka.actor.typed.scaladsl.Behaviors
import akka.http.scaladsl.Http

@main def main(): Unit =
  val appConf = ConfigFactory.load("application")
  val httpServerConf = ConfigFactory.load("http-server")

  val actorSystemName = appConf.getString("main-actor-system.name")
  val host = httpServerConf.getString("http-server.host")
  val port = httpServerConf.getInt("http-server.port")

  implicit val system: ActorSystem[Any] = ActorSystem(Behaviors.empty, actorSystemName)
  implicit val executionContext: ExecutionContextExecutor = system.executionContext

  val bindingFuture: Future[Http.ServerBinding] = Http().newServerAt(host, port).bind(Routes.all)

  println(s"Server now online. Please navigate to http://$host:$port/status\nPress RETURN to stop...")
  StdIn.readLine()

  bindingFuture
    .flatMap(_.unbind())
    .onComplete(_ => system.terminate())
