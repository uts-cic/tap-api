package au.edu.utscic.tap.data.nlp.nlp4j

import au.edu.utscic.tap.UnitSpec

/**
  * Created by andrew@andrewresearch.net on 17/7/17.
  */
class DependencyParserSpec extends UnitSpec {

  "process" should "decode text to dependencies" in {
    val res = DependencyParser.process("Hello, this is a a test of the dependency parser.")
    assert(res==List())
  }

}
