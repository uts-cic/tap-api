package au.edu.utscic.tap.message

import akka.util.ByteString
import org.json4s.jackson.Serialization
import org.json4s.{Extraction, JValue, NoTypeHints}

import scala.concurrent.Future

/**
  * Created by andrew@andrewresearch.net on 24/2/17.
  */
object Json {
  import scala.concurrent.ExecutionContext.Implicits.global
  implicit val serialformats = Serialization.formats(NoTypeHints)

  case class ByteStringAnalysis(byteStr:ByteString,analysisType:String)
  case class CorpusAnalysis(corpus:String,analysisType:String)
  case class Results(message:String,results:JValue)


  def formatStringResults(results:String, message:String):Future[Results] = Future(Results(message,Extraction.decompose(results)))

  def formatResults[T](results:Future[T], message:String):Future[Results] = results.map { r =>
    Results(message, Extraction.decompose(r))
  }



}
