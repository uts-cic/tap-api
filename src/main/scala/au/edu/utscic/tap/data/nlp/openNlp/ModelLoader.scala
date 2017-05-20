package au.edu.utscic.tap.data.nlp.openNlp

import java.io.{FileInputStream, IOException}

import opennlp.tools.lemmatizer.LemmatizerModel
import opennlp.tools.postag.POSModel
import opennlp.tools.sentdetect.SentenceModel
import opennlp.tools.tokenize.TokenizerModel


object ModelLoader {

  def load[T](t:Class[T]):Option[T] = {
    try {
      val model = t match {
        case Models.Sentence.kind => new SentenceModel(modelFile(Models.Sentence.path))
        case Models.Token.kind => new TokenizerModel(modelFile(Models.Token.path))
        case Models.PosTag.kind => new POSModel(modelFile(Models.PosTag.path))
        case Models.Lemma.kind => new LemmatizerModel(modelFile(Models.Lemma.path))
      }
      Some(model.asInstanceOf[T])
    }
    catch {
      case e: IOException =>
        e.printStackTrace()
        None
    }
  }

  private def modelFile(model:String) = new FileInputStream(model)


}
