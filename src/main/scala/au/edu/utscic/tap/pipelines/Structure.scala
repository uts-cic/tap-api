package au.edu.utscic.tap.pipelines

import akka.NotUsed
import akka.stream.scaladsl.{Flow, Source}
import au.edu.utscic.tap.data.{TapDocument, TapSection, TapSentence, TapTags}

/**
  * Created by andrew@andrewresearch.net on 24/2/17.
  */
object Structure {

    import edu.stanford.nlp.simple._
    import scala.collection.JavaConverters._

  val sectionsAsDocs:Flow[String,Document,NotUsed] = Flow[String].map(s => new Document(s))
  val sentences:Flow[Document,List[Sentence],NotUsed] = Flow[Document].map(_.sentences().asScala.toList)
  val tapSentences:Flow[List[Sentence],List[TapSentence],NotUsed] = Flow[List[Sentence]].map(_.map( s => buildTapSentence(s)))
  val tapSection:Flow[List[TapSentence],TapSection,NotUsed] = Flow[List[TapSentence]].map(TapSection(_))
  val tapDocument = Flow[TapSection].fold(List[TapSection]())(_:+_).map(TapDocument(_))

  val pipeline = sectionsAsDocs.via(sentences).via(tapSentences).via(tapSection).via(tapDocument)

  def buildTapSentence(sentence:Sentence) = TapSentence(
    sentence.text(),
    sentence.words().asScala.toList.map(_.toLowerCase),
    TapTags(
      sentence.lemmas().asScala.toList,
      sentence.posTags().asScala.toList
    )
  )


}


