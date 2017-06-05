package au.edu.utscic.tap.httpServer

import akka.http.scaladsl.model.HttpResponse
import akka.http.scaladsl.server.Directives._
import akka.util.ByteString
import au.edu.utscic.tap.handlers.{CorpusAnalysisHandler, TextAnalysisHandler}
import akka.http.scaladsl.model.StatusCodes._
import au.edu.utscic.tap.message.Json

/**
  * Created by andrew@andrewresearch.net on 20/2/17.
  */
trait TapStreamApi extends GenericApi {

  val textRoutes = pathPrefix("text") {
    extractUnmatchedPath { param =>
      if (!param.isEmpty) {
        post {
          import akka.http.scaladsl.unmarshalling.PredefinedFromEntityUnmarshallers.byteStringUnmarshaller
          entity(as[ByteString]) { str =>
            if (str.isEmpty) complete("Text required for analysis")
            else {
              val analysisType = param.dropChars(1).head.toString
              //println("analysisType - {}",analysisType)
              val analysisMsg = Json.ByteStringAnalysis(str,analysisType)
              complete(TextAnalysisHandler.analyse(analysisMsg))
            }
          }
        }
      }
      else nothingHere
    }
  }

  val corpusRoutes = pathPrefix("corpus") {
    extractUnmatchedPath { params =>
      if (!params.isEmpty) {
        get{
          val typeCorpus = params.dropChars(1).toString.split("/").toList
          if(typeCorpus.length == 2) {
            val msg = Json.CorpusAnalysis(typeCorpus(1),typeCorpus(0))
            complete(CorpusAnalysisHandler.analyse(msg))
          } else complete(HttpResponse(BadRequest, entity = "The URL is not in the correct format for this endpoint."))
        }
      }
      else nothingHere
    }
  }

  override val customRoutes = pathPrefix("analyse") {
    textRoutes ~ corpusRoutes
  }

}
