package au.edu.utscic.tap.analysis.rhetorical

import au.edu.utscic.tap.UnitSpec
import au.edu.utscic.tap.data.rhetorical.RhetoricalTypes.Node

import scala.collection.immutable.ListMap

/**
  * Created by andrew@andrewresearch.net on 12/7/17.
  */
class LexicalNodeParserSpec extends UnitSpec {

  val input = "My first lecture started 13 March 2015."
  val input2 = "This is another sentence."
  val lexNodes = ListMap(0 -> Node(0,"ROOT",None,None), 1 -> Node(1,"PRP$",Some("My"),Some("my")), 2 -> Node(2,"JJ",Some("first"),Some("#ord#")),
    3 -> Node(3,"NN",Some("lecture"),Some("lecture")), 4 -> Node(4,"VBD",Some("started"),Some("start")), 5 -> Node(5,"CD",Some("13"),Some("0")),
    6 -> Node(6,"NNP",Some("March"),Some("march")), 7 -> Node(7,"CD",Some("2015"),Some("0")), 8 -> Node(8,".",Some("."),Some(".")))

    val lexNodes2 = ListMap(0 -> Node(0,"ROOT",None,None), 1 -> Node(1,"DT",Some("This"),Some("this")), 2 -> Node(2,"VBZ",Some("is"),Some("be")), 3 -> Node(3,"DT",Some("another"),Some("another")),
    4 -> Node(4,"NN",Some("sentence"),Some("sentence")), 5 -> Node(5,".",Some("."),Some(".")))

  "parseSentence" should "produce a list of lexicalNodes from a sentence" in {

    val res = LexicalNodeParser.parseSentence(input)
    val res2 = LexicalNodeParser.parseSentence(input2)
    assert(res==lexNodes)
    assert(res2==lexNodes2)
  }

}
