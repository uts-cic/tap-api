package au.edu.utscic.tap.data.nlp

import au.edu.utscic.tap.data.nlp.openNlp.{OpenNlpDocument, OpenNlpSentence}

/**
  * Created by andrew@andrewresearch.net on 8/5/17.
  */
//trait Document {
//  def sentences:List[Sentence]
//}

trait Document {
  def sentences:List[Sentence]
}

trait Sentence {
  def text:String
  def words:List[String]
  def lemmas:List[String]
  def posTags:List[String]
}

trait CanBeDocument[T]{
  def fromText(text:String): Document
}
object CanBeDocument {
  implicit object OpenNlpToDocument extends CanBeDocument[OpenNlpDocument] {
    def fromText(text:String) = OpenNlpDocument(text)
  }
}

trait CanBeSentence[T]{
  def fromText(text:String): Sentence
}
object CanBeSentence {
  implicit object OpenNlpToSentence extends CanBeSentence[OpenNlpSentence] {
    def fromText(text:String) = OpenNlpSentence(text)
  }
}

object Nlp {
  def document[T](text: String)(implicit toDoc: CanBeDocument[T]): Document = {
    toDoc.fromText(text)
  }
  def sentence[T](text: String)(implicit toSentence: CanBeSentence[T]): Sentence = {
    toSentence.fromText(text)
  }
}
