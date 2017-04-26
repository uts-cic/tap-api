package au.edu.utscic.tap.pipelines

import java.nio.file.Path

import akka.NotUsed
import akka.stream.scaladsl.{Flow, Keep, Sink, Source}
import au.edu.utscic.tap.TapStreamContext
import au.edu.utscic.tap.io.Local
import au.edu.utscic.tap.io.Local.CorpusFile

import scala.collection.immutable
import scala.concurrent.Future

/**
  * Created by andrew@andrewresearch.net on 27/2/17.
  */

trait Pipeline {

}

object PipeUtil {
  def sourceFrom[A,B](input:immutable.Iterable[A]):Source[A,NotUsed] = Source[A](input)
}

/*
case class TextPipeline[A,B](source:Source[A,B],flow:Flow[A,B,NotUsed],singleOutput:Boolean) {
  import TapStreamContext._
  val sink = if(singleOutput) Sink.head[B] else Sink.seq[B]
  def run = source.via(flow).toMat(sink)(Keep.right).run()
}
*/

case class CorpusTextPipeline[A](source:Source[String,NotUsed],flow:Flow[String,List[Map[String,Double]],NotUsed],singleOutput:Boolean)  {
  import TapStreamContext._
  val sink = if(singleOutput) Sink.head[List[Map[String,Double]]] else Sink.seq[List[Map[String,Double]]]
  def run = source.via(flow).toMat(sink)(Keep.right).run()
}


case class TextPipeline[A,B](iterable:immutable.Iterable[A],flow:Flow[A,B,NotUsed],singleOutput:Boolean = true) extends Pipeline {
  //def this(iterable:immutable.Iterable[Future[String]],flow:Flow[A,B,NotUsed]) = this(Source[Future[String]](iterable).mapAsync(1)(identity),flow,false)
  import TapStreamContext.materializer
  val sink = if(singleOutput) Sink.head[B] else Sink.seq[B]
  def run = PipeUtil.sourceFrom(iterable).via(flow).toMat(sink)(Keep.right).run()
}

//object TextPipeline extends Pipeline {
//  val source = sourceFrom(iterable)
//  def apply[A,B](iterable:immutable.Iterable[A],flow:Flow[A,B,NotUsed],outputIsSingle:Boolean) = new TextPipeline(source,flow,outputIsSingle)
//
//  //def apply[T](source:immutable.Iterable[Future[String]],flow:Flow[String,T,NotUsed],outputIsSingle:Boolean) = new TextPipeline(Source[Future[String]](source).mapAsync(1)(identity),flow,outputIsSingle)
//}

case class CorpusPipeline[A,B](source:Source[Path,A],flow:Flow[Path,Future[Local.CorpusFile],B]) extends Pipeline {
  import TapStreamContext._
  val sink = Sink.seq[Future[Local.CorpusFile]].mapMaterializedValue(_.map(Future.sequence(_)).flatten)
  val pipeline =  source.via(flow).toMat(sink)(Keep.right)
  def run = pipeline.run()
}

case class CorpusPipelineIter[A,B](source:Source[Path,A],flow:Flow[Path,Future[CorpusFile],B]) extends Pipeline {
  import TapStreamContext._
  val sink = Sink.seq[Future[CorpusFile]]
  val pipeline =  source.via(flow).toMat(sink)(Keep.right)
  def run = pipeline.run().map(Future.sequence(_)).flatten
}

//case class Pipeline[A,B,C](source:Source[A,B],flow:Flow[A,C,NotUsed],singleOutput:Boolean) {
//  import TapStreamContext.materializer
//  val sink = if(singleOutput) Sink.head[C] else Sink.seq[C]
//
//  def run = source.via(flow).toMat(sink)(Keep.right).run()
//  //def run[T](pipeline:Flow[A,B,NotUsed]) = pipeline.toMat(Sink.seq)(Keep.right).run()
//
//
//}



