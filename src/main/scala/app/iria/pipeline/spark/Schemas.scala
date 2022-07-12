package app.iria.pipeline.spark

import org.apache.spark.sql.types.{ArrayType, BinaryType, StringType, StructField, StructType}

object Schemas {

    val DOC_NORMALIZED_SCHEMA : StructType = {
        new StructType(
            Array(
                StructField( "document_id", StringType, nullable = false ),
                StructField( "body", StringType, nullable = true ),
                StructField( "title", StringType, nullable = true ),
                StructField( "author", StringType, nullable = true ),
                StructField( "subject", StringType, nullable = true ),
                StructField( "genre", StringType, nullable = true ),
                StructField( "tags", ArrayType( StringType ), nullable = true ) )
            )
    }

}
