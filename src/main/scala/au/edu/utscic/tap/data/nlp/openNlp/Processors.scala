package au.edu.utscic.tap.data.nlp.openNlp

import au.edu.utscic.tap.data.nlp.{Document, Sentence}


/**
  * Created by andrew@andrewresearch.net on 19/5/17.
  */
object Processors {
  def textToDoc(text:String):Document = {
    val sentences = Parsers.sentence(text).map(textToSentence(_)) //List(textToSentence("This has"),textToSentence("been converted"))
    Document(text,sentences)
  }
  def textToSentence(text:String):Sentence = {
    val words = Parsers.token(text)
    val posTags = Parsers.posTag(words)
    val lemmas = List()//Parsers.lemma(words,posTags)
    Sentence(text,words,lemmas,posTags)
  }
}
