package au.edu.utscic.tap.data.nlp.openNlp

import opennlp.tools.lemmatizer.LemmatizerModel
import opennlp.tools.postag.POSModel
import opennlp.tools.sentdetect.SentenceModel
import opennlp.tools.tokenize.TokenizerModel

/**
  * Created by andrew@andrewresearch.net on 19/5/17.
  */
object Models {
  def fullPath(model:String) = "/Users/andrew/Documents/development/_projects/CIC-Current/tap-api/src/main/resources/opennlp-models/"+model

  object Sentence {
    val path = fullPath("en-sent.bin")
    val kind = classOf[SentenceModel]
  }

  object Token {
    val path = fullPath("en-token.bin")
    val kind = classOf[TokenizerModel]
  }

  object PosTag {
    val path = fullPath("en-pos-maxent.bin")
    val kind = classOf[POSModel]
  }

  object Lemma {
    val path = fullPath("en-lemma-perceptron-conll09.bin")
    val kind = classOf[LemmatizerModel]
  }
}
