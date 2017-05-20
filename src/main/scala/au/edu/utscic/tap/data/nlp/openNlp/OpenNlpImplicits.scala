package au.edu.utscic.tap.data.nlp.openNlp

import au.edu.utscic.tap.data.nlp.{Document, DocumentConverter, Sentence, SentenceConverter}

/**
  * Created by andrew@andrewresearch.net on 19/5/17.
  */
object OpenNlpImplicits {
  implicit object OpenNlpToDocument extends DocumentConverter[Document] {
    def fromText(text:String):Document = Processors.textToDoc(text)
  }
  implicit object OpenNlpToSentence extends SentenceConverter[Sentence] {
    def fromText(text:String) = Processors.textToSentence(text)
  }
}
