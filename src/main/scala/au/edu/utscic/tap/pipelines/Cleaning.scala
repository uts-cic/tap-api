package au.edu.utscic.tap.pipelines

import akka.NotUsed
import akka.stream.scaladsl.Flow

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
    val revealInvisible:Flow[Char,String,NotUsed] = makeVisible.via(makeString)
    val lengthPreserveClean:Flow[Char,String,NotUsed] = whitespaceReplace.via(cntlExtReplace).via(makeString)
    val fullCleanUtf:Flow[Char,String,NotUsed] = charReplace.via(stripControl).via(stripExtended).via(reduceWhiteSpace)
    val fullClean127:Flow[Char,String,NotUsed] = charReplace.via(stripAbove127).via(stripControl).via(stripExtended).via(reduceWhiteSpace)
  }

  //Characters
  val notChar = '\u00ac'
  val singleAposChar = '\u0027'
  val dotChar = '\u00b7'
  val hyphen = '\u002d'
  val currency = '\u00a4'
  val macron = '\u00af'
  val newLine = '\u000a'
  val spaceChar = '\u0020'
  val singleCurlyLeft = '\u2018'
  val singleCurlyRight = '\u2019'
  val doubleCurlyLeft = '\u201c'
  val doubleCurlyRight = '\u201d'
  val quoteChar = '\u0022'

  //Filters
  val singleCurlyQuotes = (c:Char) => c == singleCurlyLeft || c == singleCurlyRight
  val doubleCurlyQuotes = (c:Char) => c == doubleCurlyLeft || c == doubleCurlyRight
  val carriageReturn = (c:Char) => c == '\u000d'
  val lfCr = (c:Char) => c == newLine || carriageReturn(c) // true if linefeed or carriage return
  val nonBreakSpace = (c:Char) => c == '\u00a0'
  val spaces = (c:Char) => c == spaceChar || nonBreakSpace(c) // space or non-break space
  val softHyphen = (c:Char) => c == '\u00ad'
  val lowerControl = (c:Char) => c <= '\u001f'
  val middleControl = (c:Char) => c >= '\u007f' && c <= '\u009f'
  val allControl = (c:Char) => lowerControl(c) || middleControl(c)
  val extended = (c:Char) => c >= '\u0100'
  val above127 = (c:Char) => c > '\u007e'




  /****************
    * @name makeVisible Flow
    *      Takes a UTF-8 string as a stream of characters (including invisibles)
    *      Outputs a stream of visible characters
    */
  val makeVisible:Flow[Char,Char,NotUsed] = Flow[Char].map {
    case c if singleCurlyQuotes(c) => singleAposChar
    case c if doubleCurlyQuotes(c) => quoteChar
    case c if lfCr(c) => notChar
    case c if spaces(c) => dotChar
    case c if softHyphen(c) => hyphen
    case c if allControl(c) => macron
    case c if extended(c) => currency
    case c => c //all others pass through
  }

  val makeString:Flow[Char,String,NotUsed] = Flow[Char].fold("")(_ + _)


  val whitespaceReplace:Flow[Char,Char,NotUsed] = Flow[Char].map {
    case c if carriageReturn(c) => newLine
    case c if nonBreakSpace(c) => spaceChar
    case c => c
  }

  val cntlExtReplace:Flow[Char,Char,NotUsed] = Flow[Char].map {
    case c if allControl(c) => spaceChar
    case c if extended(c) => spaceChar
    case c if softHyphen(c) => hyphen
    case c => c
  }




  val charReplace:Flow[Char,Char,NotUsed] = Flow[Char].map {
    case c if singleCurlyQuotes(c) => singleAposChar
    case c if doubleCurlyQuotes(c) => quoteChar
    case c if softHyphen(c) => hyphen
    case c => c //all others pass through
  }

  /* Remove characters that represented control characters */
  val stripControl = Flow[Char].filterNot(c => allControl(c))
  val stripExtended = Flow[Char].filterNot(c => extended(c))


  /* Reduce multiple spaces to single spaces, multiple newlines indicate a sentences */
  val reduceWhiteSpace = Flow[Char].fold(""){(s:String,c:Char) =>
      if(spaces(c)) { //will end with space
        if(s.endsWith(spaceChar.toString) || s.endsWith(newLine.toString)) s
        else s + spaceChar
      } else if(lfCr(c)) { //will end with newline
        if(s.endsWith(spaceChar.toString)) s.dropRight(1) + newLine
        else if(s.endsWith(newLine.toString)) s // multiple newlines treated as sentences break
        else s + c
      } else s + c
  }

  val stripAbove127 = Flow[Char].filterNot(c => above127(c))




}
