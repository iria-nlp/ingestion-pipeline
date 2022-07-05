
import app.iria.pipeline.spark.Schemas
import app.iria.pipeline.utils.Kafka
import app.iria.utils.temporal.OffsetDateTimes
import com.typesafe.config.{Config, ConfigFactory}
import org.apache.spark.sql.SparkSession


object Main {

    def main( args : Array[ String ] ) : Unit = {

        import app.iria.utils.temporal.OffsetDateTimes._

        println( s"Starting IRIA document ingestion pipeline... ${OffsetDateTimes.now().toIsoString()}" )

        val config : Config = ConfigFactory.load( "env/default.conf" ).resolve()
        val kafkaConfig : Config = config.getConfig( "kafka" )


        val spark = initSpark( config )

        val stream = {
            spark.readStream
              .schema( Schemas.RAW_FEED_SCHEMA )
              .format( "kafka" )
              .options( Kafka.mapConfig( kafkaConfig ) )
              .option( "subscribe", kafkaConfig.getString( "input.topic" ) )
              .load()
        }

        stream.printSchema()
        stream.show()

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
