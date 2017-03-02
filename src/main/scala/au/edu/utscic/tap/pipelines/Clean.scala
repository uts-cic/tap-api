package au.edu.utscic.tap.pipelines

import akka.stream.scaladsl.{Flow}

/** A cleaning pipeline
  * Creates a runnable graph for the input ByteString
  * Replaces invisible characters with visible symbols
  *
  */
object Clean  {

  /* Make all invisible characters visible */
  val makeVisible = Flow[Byte].map {
    case b if b == 10 || b == 13 => '\u00ac' // newline and return
    case b if b == 32 || b == 160 => '\u00b7' // space and non-break space
    case b if b < 33 => '\u00a4' // lower control characters
    case b if b > 126 && b < 160 => '\u00af' // middle control characters
    case b if b == 173 => '-' // soft hyphen
    case b if b > 191 => '\u00af' // all upper characters
    case b:Byte => b.toChar
  }

  /* Remove characters that represented control characters */
  val stripControl = Flow[Char].filterNot(c => c =='\u00a4' || c =='\u00af')

  /* Reduce multiple spaces to single spaces, multiple newlines indicate a section */
  val reduceWhiteSpace = Flow[Char].fold(""){(s:String,c:Char) =>
      if(c=='\u00b7') { //will end with space
        if(s.endsWith(" ") || s.endsWith("\u00ac")) s
        else if (s.endsWith("\u00a7")) s
        else s + " "
      } else if(c=='\u00ac') { //will end with newline
        if(s.endsWith(" ")) s.dropRight(1) + "\u00ac"
        else if(s.endsWith("\u00ac")) s.dropRight(1) + "\u00a7" // multiple newlines treated as section break
        else if (s.endsWith("\u00a7")) s
        else s + c
      } else s + c
  }

  /* Convert single newlines to spaces */
  val newlineToSpace = Flow[String].map(_.replaceAll("\u00ac"," "))

  /* Split into sections creating a list */
  val splitSections = Flow[String].mapConcat(_.split("\u00a7").toList.filterNot(_.isEmpty))


  val pipeline = makeVisible
    .via(stripControl)
    .via(reduceWhiteSpace)
    .via(newlineToSpace)
    .via(splitSections)


}
