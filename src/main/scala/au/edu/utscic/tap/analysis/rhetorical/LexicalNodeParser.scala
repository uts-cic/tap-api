package au.edu.utscic.tap.analysis.rhetorical

import au.edu.utscic.tap.data.nlp.nlp4j.Lemmatiser
import au.edu.utscic.tap.data.nlp.openNlp.Parsers
import au.edu.utscic.tap.data.rhetorical.RhetoricalTypes._

import scala.collection.immutable.ListMap


/**
  * Created by andrew@andrewresearch.net on 12/7/17.
  */
object LexicalNodeParser {

  def parseSentence(text:String):ListMap[Int,Node] = {
    val tokens = Parsers.token(text)
    val posTags = Parsers.posTag(tokens)
    val lemmas = Lemmatiser.process(tokens,posTags)

    val nodes = tokens.zipWithIndex.zip(posTags).zip(lemmas).map {
      case (((t,i),p),l) => (i+1) -> Node(i+1,p,Some(t),Some(l))
    }
    //Add the root node
    val lexNodes = nodes.toMap ++ Map(0 -> Node(0,"ROOT",None,None))

    ListMap(lexNodes.toSeq.sortBy(_._1):_*)
  }
}
