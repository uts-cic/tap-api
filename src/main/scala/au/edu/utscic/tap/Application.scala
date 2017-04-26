package au.edu.utscic.tap

import au.edu.utscic.tap.httpServer.Server

/** The Application entry point starts the akka-http server
  *
  * @todo - Need to wire in config
  *
  */
object Application extends App {
  Server.startServer("0.0.0.0",8080)
}
