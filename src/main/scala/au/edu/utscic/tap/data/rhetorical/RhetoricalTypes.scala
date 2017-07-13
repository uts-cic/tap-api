package au.edu.utscic.tap.data.rhetorical

import org.json4s.JsonAST.JArray

import scala.collection.immutable.ListMap

/**
  * Created by andrew@andrewresearch.net on 29/6/17.
  */

object RhetoricalTypes {
  type ConstituentTree = String
  type LexicalNodes = ListMap[Int,Node]
  type Dependencies = List[Dependency]
  type ParsedSentence = (LexicalNodes,ConstituentTree,Dependencies)

  case class Node(
                   id:Int,
                   POS:String,
                   surface:Option[String],
                   lemma:Option[String]
                 )

  case class Dependency(
                         name:String,
                         governor:Int,
                         dependent:Int
                       )
}





//(TOP (S (NP-SBJ (DT Some) )(VP (VBP say) (NP (NNP November) ))(. .) ))
//[“ROOT”, [“S”, [“NP”, [[“Det”,”0”],[“Noun”,”1”]], [“VP”, [“VBD”,”2”]]]]]