package au.edu.utscic.tap.pipelines

/**
  * Created by andrew@andrewresearch.net on 19/5/17.
  */

import akka.NotUsed
import akka.stream.scaladsl.{Flow, Keep, Sink}
import au.edu.utscic.tap.TapStreamContext

import scala.collection.immutable

case class TextPipeline[A,B](iterable:immutable.Iterable[A],flow:Flow[A,B,NotUsed],singleOutput:Boolean = true) extends Pipeline {
  import TapStreamContext.materializer
  val sink = if(singleOutput) Sink.head[B] else Sink.seq[B]
  def run = sourceFrom(iterable).via(flow).toMat(sink)(Keep.right).run()
}
