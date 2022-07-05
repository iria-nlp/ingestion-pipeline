package app.iria.pipeline.components

import app.iria.model.Doc

class Annotator( val name : String,
                 val priority : Int,
                 val version : String,
                 val async : Boolean = true ) extends Processor {

    override def execute( doc : Doc ) : Doc = ???

}
