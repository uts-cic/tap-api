package au.edu.utscic.tap.io

import java.io.{File, InputStream}
import java.nio.file.{Path, Paths}

import akka.NotUsed
import akka.stream.IOResult
import akka.util.ByteString

import scala.concurrent.{Await, Future}
import scala.concurrent.duration._

/**
  * Created by andrew@andrewresearch.net on 1/3/17.
  */
object Local {

  import akka.stream.scaladsl._

  import au.edu.utscic.tap.TapStreamContext._

  def directorySource(directory: String):Source[Path,NotUsed] = {

    val dir = new File(getClass.getResource(directory).getPath)
    println(dir.toString)
    val files = dir.listFiles.filter(f => f.isFile && f.canRead).toList.map(f => Paths.get(f.getAbsolutePath))
    files.foreach(println(_))
    Source(files)
  }

  val fileFlow:Flow[Path,Future[String],NotUsed] = Flow[Path].map(fileSource(_).toMat(Sink.head[ByteString])(Keep.right).run().map(_.utf8String))


  def fileSource(path:Path):Source[ByteString,Future[IOResult]] = FileIO.fromPath(path)

 val pipeline = directorySource("/").via(fileFlow) //.f mapConcat(f => Await.result(f,1 second))

    //implicit val formats: Formats = DefaultFormats
    //implicit val jacksonSerialization: Serialization = jackson.Serialization
    //val fileName = "/affect-lexicon.json"
    //lazy val stream : InputStream = getClass.getResourceAsStream(fileName)
    //lazy val src = scala.io.Source.fromInputStream( stream )
    //def load:List[Affect] = org.json4s.jackson.Serialization.read[List[Affect]](src.reader())

}
