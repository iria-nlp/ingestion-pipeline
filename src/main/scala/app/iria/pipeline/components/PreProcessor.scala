package app.iria.pipeline.components

import app.iria.model.Doc

class PreProcessor( val name : String,
                    val priority : Int,
                    val version : String,
                    val async : Boolean = false ) extends Processor {

    override def execute( doc : Doc ) : Doc = ???

}
