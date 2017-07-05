package au.edu.utscic.tap.pipelines

import akka.NotUsed
import akka.stream.scaladsl.Flow
import akka.util.ByteString

/*****************************************
  *  Cleaning
  *      The pipelines for cleaning text
  *      Nested object holds pipelines that take a
  *      stream of Char and produce a string stream
  */
object Cleaning  {

  /****************************************
    *  Pipeline
    *      A convenience object that holds the pipelines
    *      for cleaning
    */
  object Pipeline {
    val revealInvisible:Flow[ByteString,ByteString,NotUsed] = Flow[ByteString].map(identity)//utf8Chars via makeVisible via asByteString
    //val lengthPreserveClean:Flow[Char,String,NotUsed] = whitespaceReplace.via(cntlExtReplace).via(makeString)
    //val fullCleanUtf:Flow[Char,String,NotUsed] = charReplace.via(stripControl).via(stripExtended).via(reduceWhiteSpace)
    //val fullClean127:Flow[Char,String,NotUsed] = charReplace.via(stripAbove127).via(stripControl).via(stripExtended).via(reduceWhiteSpace)
  }

  //Characters
  object White {
    val nl = "\u000a"
    val cr = "\u000d"
    val sp = "\u0020"
    val nb = "\u00a0"
  }

  object Replace {
    val not = "\u00ac"
    val dot = "\u00b5"
    val qmk = "\ufffd"
  }

  object Quote {
      val singleCurlyLeft = "\u2018"
      val singleCurlyRight = "\u2019"
      val doubleCurlyLeft = "\u201c"
      val doubleCurlyRight = "\u201d"
      //val double = "\u0022"
      val single = "\u0027"
  }

  object Hyphen {
    val normal = "\u002d"
    val soft = "\u00ad"
  }


//  val currency = ByteString("\u00a4")
//  val macron = ByteString("\u00af")
//
//




  //Filters
//  val singleCurlyQuotes = (c:ByteString) => c == singleCurlyLeft || c == singleCurlyRight
//  val doubleCurlyQuotes = (c:ByteString) => c == doubleCurlyLeft || c == doubleCurlyRight
//  val carriageReturn = (c:ByteString) => c == returnChar
//  val lfCr = (c:ByteString) => c == newLine || carriageReturn(c) // true if linefeed or carriage return
//  val nonBreakSpace = (c:ByteString) => c == nbSpaceChar
//  val spaces = (c:ByteString) => c == spaceChar || nonBreakSpace(c) // space or non-break space
//  val softHyphen = (c:ByteString) => c == ''

//  val lowerControl = (c:ByteString) => c <= '\u001f'
//  val middleControl = (c:ByteString) => c >= '\u007f' && c <= '\u009f'
//  val allControl = (c:ByteString) => lowerControl(c) || middleControl(c)
//  val extended = (c:ByteString) => c >= '\u0100'
//  val above127 = (c:ByteString) => c > '\u007e'

  //val above127 = (b:Byte) => b < 0


  //val utf8Chars:Flow[ByteString,Char,NotUsed] = Flow[ByteString].map(_.utf8String.toVector).mapConcat(identity)

  val makeVisible:Flow[ByteString,ByteString,NotUsed] = Flow[ByteString].map { bs =>
    val str = bs.utf8String
    ByteString(str)
  }

  /****************
    *  makeVisible Flow
    *      Takes a UTF-8 string as a stream of characters (including invisibles)
    *      Outputs a stream of visible characters
    */
//  val makeVisible:Flow[Char,Char,NotUsed] = Flow[Char].map {
//    case c if singleCurlyQuotes(c) => singleAposChar
//    case c if doubleCurlyQuotes(c) => quoteChar
//    case c if lfCr(c) => notChar
//    case c if spaces(c) => dotChar.utf8String.head
//    case c if softHyphen(c) => hyphen
//    case c if allControl(c) => macron
//    case c if extended(c) => currency
//    case c => c //all others pass through
 // }

//  val asByteString:Flow[Char,ByteString,NotUsed] = Flow[Char].fold("")(_ + _).map(ByteString(_))


//  val whitespaceReplace:Flow[Char,Char,NotUsed] = Flow[Char].map {
//    case c if carriageReturn(c) => newLine
//    case c if nonBreakSpace(c) => spaceChar
//    case c => c
//  }
//
//  val cntlExtReplace:Flow[Char,Char,NotUsed] = Flow[Char].map {
//    case c if allControl(c) => spaceChar
//    case c if extended(c) => spaceChar
//    case c if softHyphen(c) => hyphen
//    case c => c
//  }



//
//  val charReplace:Flow[Char,Char,NotUsed] = Flow[Char].map {
//    case c if singleCurlyQuotes(c) => singleAposChar
//    case c if doubleCurlyQuotes(c) => quoteChar
//    case c if softHyphen(c) => hyphen
//    case c => c //all others pass through
//  }
//
//  /* Remove characters that represented control characters */
//  val stripControl = Flow[Char].filterNot(c => allControl(c))
//  val stripExtended = Flow[Char].filterNot(c => extended(c))
//
//
//  /* Reduce multiple spaces to single spaces, multiple newlines indicate a nlpSentences */
//  val reduceWhiteSpace = Flow[Char].fold(""){(s:String,c:Char) =>
//      if(spaces(c)) { //will end with space
//        if(s.endsWith(spaceChar.toString) || s.endsWith(newLine.toString)) s
//        else s + spaceChar
//      } else if(lfCr(c)) { //will end with newline
//        if(s.endsWith(spaceChar.toString)) s.dropRight(1) + newLine
//        else if(s.endsWith(newLine.toString)) s // multiple newlines treated as nlpSentences break
//        else s + c
//      } else s + c
//  }
//
//  val stripAbove127 = Flow[Char].filterNot(c => above127(c))
//



}
