package app.iria.pipeline

import app.iria.extractors.{Extractor, RawInput}
import app.iria.pipeline.components.{Annotator, PreProcessor}
import app.iria.pipeline.spark.Schemas.DOC_NORMALIZED_SCHEMA
import org.apache.spark.sql.{DataFrame, SparkSession}

import javax.annotation.processing.Processor
import scala.collection.mutable
import scala.collection.mutable.Seq


object Pipeline {

    def builder( ) : PipelineBuilder = new PipelineBuilder()

    class PipelineBuilder {

        private val extractors : mutable.Set[ Extractor ] = mutable.Set()
        private val processors : mutable.Seq[ PreProcessor ] = mutable.Seq()
        private val annotators : mutable.Set[ Annotator ] = mutable.Set()

        def withExtractor( extractor : Extractor ) : PipelineBuilder = {
            extractors += extractor
            return this
        }

        def withExtractors( e : Set[ Extractor ] ) : PipelineBuilder = {
            extractors & e
            return this
        }

        def build( ) : Pipeline = {
            new Pipeline( extractors.toSet, Seq(), Seq() )
        }

    }

}


class Pipeline private( extractors : Set[ Extractor ],
                        processors : Seq[ Processor ],
                        annotators : Seq[ Annotator ] ) {


    def run( spark : SparkSession, data : DataFrame ) : DataFrame = {
        import spark.implicits._

        val extracted = data
          .select( $"key", $"content" )
          .map( row => {
              extractors.map( e => {
                  val input = RawInput( "_refactor_", row.getAs[ Array[ Byte ] ]( "content" ) )
                  val doc = e.extract( input )
                  (doc.id, doc.normalized.body, doc.normalized.title, doc.normalized.author, doc.normalized.subject, doc.normalized.genre, doc.normalized.tags)
              } )
          } )
          .toDF()

        val norm = spark.createDataFrame( extracted.rdd, DOC_NORMALIZED_SCHEMA )

        norm
          .select( $"document_id", $"title", $"author", $"body" )
          .map( row => {
              val out =
                  s"""
                     |doc_id: ${row.getAs[ String ]( "document_id" )}
                     |
                     |title: ${row.getAs[ String ]( "title" )}
                     |
                     |author: ${row.getAs[ String ]( "author" )}
                     |
                     |body: ${row.getAs[ String ]( "author" )}
                     |
                     |""".stripMargin

              (row.getAs[ String ]( "document_id" ), out)
          } )
          .toDF( "document_id", "output" )
    }

}
