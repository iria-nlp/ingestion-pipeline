package app.iria.pipeline

import app.iria.extractors.Extractor
import app.iria.pipeline.components.Annotator

import javax.annotation.processing.Processor

class Pipeline( val extractors : Set[ Extractor ],
                val processors : Seq[ Processor ],
                val annotators : Seq[ Annotator ] ) {


    def run( ) : Unit = {}

}
