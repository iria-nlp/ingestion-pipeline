package app.iria.pipeline.spark

import org.apache.spark.sql.types.{BinaryType, StringType, StructField, StructType}

object Schemas {

    val RAW_FEED_SCHEMA : StructType = {
        new StructType(
            Array(
                StructField( "document_id", StringType, nullable = false ),
                StructField( "content", BinaryType, nullable = false ) )
            )
    }

}
