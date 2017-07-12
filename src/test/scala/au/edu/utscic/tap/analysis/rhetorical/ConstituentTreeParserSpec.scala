package au.edu.utscic.tap.analysis.rhetorical

/**
  * Created by andrew@andrewresearch.net on 12/7/17.
  */

import au.edu.utscic.tap.UnitSpec

class ConstituentTreeParserSpec extends UnitSpec {

  val ctp = ConstituentTreeParser
  val t0 = "My first lecture started 13 March 2015."
  val s0 = "(TOP (S (NP (PRP$ My) (JJ first) (NN lecture)) (VP (VBD started) (NP (CD 13)) (NP-TMP (NNP March) (CD 2015))) (. .)))"
  val s0List =""
  val s0json = """["ROOT",["S",["NP",["PRP$","My"],["JJ","first"],["NN","lecture"]],["VP",["VBD","started"],["NP",["CD","13"]],["NP-TMP",["NNP","March"],["CD","2015"]]],[".","."]]]"""


  val s1 = "(S (VP (VBP say)) (NP (NNP November)))"
  val s1List = List("S", List("VP", List("VBP", "say")), List("NP", List("NNP", "November")))
  val s1json = """["S",["VP",["VBP","say"]],["NP",["NNP","November"]]]"""

  val s2 = "(S (AA BB) (CC DD))"
  val s2List = List("S",List("AA","BB"),List("CC","DD"))

  val s3 = "(S (AA BB))"
  val s3List = List("S",List("AA","BB"))

  "parseToList" should "parse nested parentheses" in {
    val res = ctp.parseToList(s3)
    assert(res==s3List)
  }

  "parseToList" should "parse complex nests with multiple elements" in {
    val res = ctp.parseToList(s1)
    assert(res==s1List)
  }

  "parseOpenNlpTree" should "output valid json string" in {
    val res = ctp.parseOpenNlpTree(s1)
    assert(res==s1json)
  }

  "parseOpenNlpTree" should "change TOP to ROOT" in {
    val res = ctp.parseOpenNlpTree(s0)
    assert(res==s0json)
  }

}
