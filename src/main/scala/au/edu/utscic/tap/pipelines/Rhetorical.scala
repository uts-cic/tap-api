package au.edu.utscic.tap.pipelines

import akka.NotUsed
import akka.stream.scaladsl.Flow
import au.edu.utscic.tap.analysis.rhetorical.Athanor

/**
  * Created by andrew@andrewresearch.net on 29/6/17.
  */
object Rhetorical {

  object Pipeline {
    val sentenceMoves:Flow[String,String,NotUsed] = athanor
  }

  val athanor:Flow[String,String,NotUsed] = Flow[String].map {s =>
    val mySent = s
    Athanor.process(Athanor.demoParsed)
  }
}
