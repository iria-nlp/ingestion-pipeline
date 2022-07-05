package app.iria.pipeline.components

import app.iria.model.Doc

trait Processor {
    val name : String
    val priority : Int
    val version : String
    val async : Boolean

    def execute( doc : Doc ) : Doc
}
