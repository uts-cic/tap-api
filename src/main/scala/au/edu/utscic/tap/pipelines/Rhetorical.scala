package au.edu.utscic.tap.pipelines

import akka.NotUsed
import akka.stream.scaladsl.Flow
import au.edu.utscic.tap.analysis.rhetorical.{Athanor, SentenceParser}

/**
  * Created by andrew@andrewresearch.net on 29/6/17.
  */
object Rhetorical {

  object Pipeline {
    val sentenceMoves:Flow[String,String,NotUsed] = athanor
  }

  val athanor:Flow[String,String,NotUsed] = Flow[String].map {s =>
    val parsedSent = SentenceParser.parseSentence(s)
    Athanor.process(parsedSent).mkString(",")
  }
}
