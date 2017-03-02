package au.edu.utscic.tap.data

/**
  * Created by andrew@andrewresearch.net on 27/2/17.
  */

case class TapDocument(document:List[TapSection])

case class TapSection(section:List[TapSentence])

case class TapSentence(sentence:String,tokens:List[String],tags:TapTags = TapTags())

case class TapTags(lemmas:List[String] = List(),posTags:List[String] = List()) //,nerTags:List[String] = List())