package au.edu.utscic.tap.data.nlp.openNlp

/**
  * Created by andrew@andrewresearch.net on 8/5/17.
  */

import au.edu.utscic.tap.data.nlp.{Document, Sentence}

case class OpenNlpDocument(text:String) extends Document {

  def sentences = text.split('.').toList.map(OpenNlpSentence(_))

}

case class OpenNlpSentence(newtext:String) extends Sentence {
  def text = ""
  def words = List()
  def lemmas = List()
  def posTags = List()
}
