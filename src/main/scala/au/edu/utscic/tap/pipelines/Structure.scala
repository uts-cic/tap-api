package au.edu.utscic.tap.pipelines

import akka.NotUsed
import akka.stream.scaladsl.{Flow, Source}
import au.edu.utscic.tap.data.nlp.{Document, NlpBuilders, Sentence}
import au.edu.utscic.tap.data.{TapDocument, TapSection, TapSentence, TapTags}

/**
  * Created by andrew@andrewresearch.net on 24/2/17.
  */
object Structure {
  import au.edu.utscic.tap.data.nlp.openNlp.OpenNlpImplicits._
  val sectionsAsDocs:Flow[String,Document,NotUsed] = Flow[String].map(s => NlpBuilders.document(s))
  val sentences:Flow[Document,List[Sentence],NotUsed] = Flow[Document].map(_.sentences)
  val tapSentences:Flow[List[Sentence],List[TapSentence],NotUsed] = Flow[List[Sentence]].map(_.map( s => buildTapSentence(s)))
  val tapSection:Flow[List[TapSentence],TapSection,NotUsed] = Flow[List[TapSentence]].map(TapSection(_))
  val tapDocument = Flow[TapSection].fold(List[TapSection]())(_:+_).map(TapDocument(_))

  val pipeline = sectionsAsDocs.via(sentences).via(tapSentences).via(tapSection).via(tapDocument)

  def buildTapSentence(sentence:Sentence) = TapSentence(
    sentence.text,
    sentence.words.map(_.toLowerCase),
    TapTags(
      sentence.lemmas,
      sentence.posTags
    )
  )


}


