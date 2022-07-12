
import app.iria.extractors.tika.TikaExtractor
import app.iria.pipeline.Pipeline
import app.iria.utils.temporal.OffsetDateTimes
import app.iria.utils.temporal.OffsetDateTimes._
import better.files.File
import com.typesafe.config.{Config, ConfigFactory}
import org.apache.spark.sql.SparkSession
import org.apache.spark.sql.types.{BinaryType, StringType}

object Main {

    def main( args : Array[ String ] ) : Unit = {


        println( s"Starting IRIA document ingestion pipeline... ${OffsetDateTimes.now().toIsoString()}" )

        val config : Config = ConfigFactory.load( "env/default.conf" ).resolve()
        val kafkaConfig : Config = config.getConfig( "kafka" )

        val spark = initSpark( config )

        import spark.implicits._

        val stream = {
            spark.readStream
              .format( "kafka" )
              .option( "kafka.bootstrap.servers", kafkaConfig.getString( "bootstrap.servers" ) )
              .option( "subscribe", kafkaConfig.getString( "input.topics" ) )
              .load()
              .select( $"key".cast( StringType ), $"value".cast( BinaryType ) )
        }

        val pipeline =
            Pipeline
              .builder()
              .withExtractor( new TikaExtractor )
              .build()

        val output = pipeline.run( spark, stream )

        output
          .collect()
          .foreach( row => {
              val docId = row.getAs[ String ]( "document_id" )
              val output = row.getAs[ String ]( "output" )
              File( s"/opt/app/data/${docId}.txt" ).writeText( output )
          } )

        spark.streams.awaitAnyTermination()
    }


    private def initSpark( conf : Config ) : SparkSession = {
        SparkSession
          .builder()
          .appName( "odinson-batch-indexer" )
          .master( conf.getString( "spark.master" ) )
          .getOrCreate()
    }

}
