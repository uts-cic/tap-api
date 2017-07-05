package au.edu.utscic.tap.handlers

import akka.stream.scaladsl.{Flow, Keep, Sink, Source}
import akka.util.ByteString
import au.edu.utscic.tap.pipelines.Cleaning
import au.edu.utscic.tap.pipelines.Cleaning.White
import au.edu.utscic.tap.{TapStreamContext, UnitSpec}

import scala.concurrent.Await
import scala.concurrent.duration._

/**
  * Created by andrew@andrewresearch.net on 4/7/17.
  */

class CleaningPipelineSpec extends UnitSpec {

  import TapStreamContext._

//  "revealInvisible" should "replace whitespace characters with visible characters" in {
//
//    val input = White.sp ++ White.nb ++ White.nl ++ White.cr
//
//    val testSource = Source.single(input)
//    val testSink = Flow[ByteString].toMat(Sink.head[ByteString])(Keep.right)
//    val future = testSource via Cleaning.Pipeline.revealInvisible runWith testSink
//    val result = Await.result(future, 3 seconds)
//    assert(result.utf8String=="")
//
//  }

}
