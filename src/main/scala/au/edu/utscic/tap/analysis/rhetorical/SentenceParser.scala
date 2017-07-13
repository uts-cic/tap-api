package au.edu.utscic.tap.analysis.rhetorical

import au.edu.utscic.tap.data.nlp.openNlp.Parsers
import au.edu.utscic.tap.data.rhetorical.RhetoricalTypes._

/**
  * Created by andrew@andrewresearch.net on 12/7/17.
  */
object SentenceParser {

  def parseSentence(text:String):ParsedSentence = {
    val ln:LexicalNodes = getNodes(text)
    val ct:ConstituentTree = makeTree(text)
    val dp:Dependencies = getDependencies(text)
    (ln,ct,dp)
  }

  def getNodes(text:String):LexicalNodes = {
    LexicalNodeParser.parseSentence(text)
  }

  def makeTree(text:String):ConstituentTree = {
    ConstituentTreeParser.parseNlpTree(Parsers.parseTree(text))
  }

  def getDependencies(text:String):Dependencies = {
    //List[Dependency]
    List()
  }
}
