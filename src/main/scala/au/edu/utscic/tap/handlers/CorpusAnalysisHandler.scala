package au.edu.utscic.tap.handlers

import akka.NotUsed
import akka.stream.scaladsl.{Flow, Keep, RunnableGraph, Sink, Source}
import akka.util.ByteString
import au.edu.utscic.tap.TapStreamContext
import au.edu.utscic.tap.data.CorpusTfIdfResults
import au.edu.utscic.tap.io.Local
import au.edu.utscic.tap.io.Local.CorpusFile
import au.edu.utscic.tap.message.Exception.UnknownAnalysisType
import au.edu.utscic.tap.message.Json
import au.edu.utscic.tap.pipelines._
import io.nlytx.commons.ranking.TfIdf

import scala.collection.immutable
import scala.concurrent.{Await, Future}
import scala.concurrent.duration._
import scala.util.Success

/**
  * Created by andrew@andrewresearch.net on 20/2/17.
  */
object CorpusAnalysisHandler {

  import TapStreamContext._

  def analyse(msg: Json.CorpusAnalysis): Future[Json.Results] = {
    TapStreamContext.log.debug("Analysing '{}' corpus: {}", msg.analysisType, msg.corpus)
    val pipeline = msg.analysisType match {
      case "tfidf" => {
        val corpus:Future[Seq[CorpusFile]] = CorpusPipelineIter(Local.directorySource("/"+msg.corpus),Local.fileFlow).run
        corpus.map { c =>
          val contentsList = c.toList.map(_.contents)
          val filenamesList = c.toList.map(_.name)
          val tfidf = TfIdf.calculateNonWeighted(contentsList,true,0.02)
          (filenamesList zip tfidf).map(e => CorpusTfIdfResults(e._1,e._2.size,0.02,false,e._2))
        }
      }
      case "file" => CorpusPipeline(Local.directorySource("/"+msg.corpus),Local.fileFlow).run
      //Local.pipeline.toMat(Sink.seq[Future[String]])(Keep.right) //.via(Local.pipeline).toMat(Sink.seq[String])(Keep.right)
      //case "topic" => s"Analysing ${msg.corpus} for ${msg.analysisType}" //Pipeline(sourceFrom(msg.byteStr),Cleaning.pipeline.via(Structure.pipeline))
      case _ => {
        throw UnknownAnalysisType("Unknown analysis type")
      }
    }

    Json.formatResults(pipeline, "Corpus Analysis Results") //.map(Future.sequence(_))
    //Json.formatStringResults(pipeline,"Corpus Analysis Results")
  }
}

