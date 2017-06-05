package au.edu.utscic.tap.handlers

import au.edu.utscic.tap.TapStreamContext
import au.edu.utscic.tap.message.Exception.UnknownAnalysisType
import au.edu.utscic.tap.message.Json
import au.edu.utscic.tap.pipelines._
import au.edu.utscic.tap.util.StringUtil

import scala.concurrent.Future

/**
  * Created by andrew@andrewresearch.net on 20/2/17.
  */
object TextAnalysisHandler {

  def analyse(msg:Json.ByteStringAnalysis):Future[Json.Results] = {
    TapStreamContext.log.debug("Analysing '{}' text: {}", StringUtil.shorten(msg.byteStr.utf8String))
    val pipeline = msg.analysisType match {
      case "clean" => TextPipeline(msg.byteStr,Clean.pipeline,false)
      case "structure" => TextPipeline(msg.byteStr,Clean.pipeline.via(Structure.pipeline))
      case "vocab" => TextPipeline(msg.byteStr,Clean.pipeline.via(Structure.pipeline).via(Vocab.pipeline))
      //case "complexity" => getAnalysis[AllComplexity]("complexityAggregator",msg,sender)
//      case "expressions" => getAnalysis[AllExpressions]("expressionAnalyser",msg,sender)
//      case "metrics" => getAnalysis[AllMetrics]("metricsAnalyser",msg,sender)
//      case "pos" => getAnalysis[AllPosStats]("posAnalyser",msg,sender)
//      case "spelling" => getAnalysis[AllSpelling]("spellingAnalyser",msg,sender)
//      case "syllables" => getAnalysis[AllSyllables]("syllableAnalyser",msg,sender)
//      case "vocab" => getAnalysis[AllVocab]("vocabAnalyser",msg,sender)
//      case "xip" => getAnalysis[DocumentXip]("xipAnalyser",msg,sender)
//      case "textShape" => getAnalysis[String]("textshapeAnalyser",msg,sender)
      case _ => {
        throw UnknownAnalysisType("Unknown analysis type")
      }
    }
    Json.formatResults(pipeline.run,"Text Analysis Results")
  }
}
