import sbt._

object Dependencies {

    val slf4jVersion = "1.7.32"
    val logbackVersion = "1.2.10"
    val java8CompatVersion = "0.9.1"
    val betterFilesVersion = "3.8.0"
    val sparkVersion = "3.1.2"
    val scalaTestVersion = "3.1.4"
    val mockitoVersion = "1.16.0"
    val typesafeConfigVersion = "1.4.1"
    val jacksonVersion = "2.13.1"

    val cdr4sVersion = "3.0.9"
    val dartCommonsVersion = "3.0.30"

    val log4jOverrideVersion = "2.17.1"

    val sparkArangoVersion = "1.0.0"

    val iriaCoreVersion = "0.0.1-SNAPSHOT"
    val iriaExtractorVersion = "0.0.1-SNAPSHOT"

    val iria = Seq( "app.iria" %% "iria-document-model" % iriaCoreVersion,
                    "app.iria" %% "iria-utils" % iriaCoreVersion,
                    "app.iria" %% "iria-extractor-api" % iriaExtractorVersion )

    val spark = Seq( "org.apache.spark" %% "spark-streaming" % sparkVersion % Provided,
                     "org.apache.spark" %% "spark-sql" % sparkVersion % Provided,
                     "org.apache.spark" %% "spark-sql-kafka-0-10" % sparkVersion,
                     "org.apache.spark" %% "spark-streaming-kafka-0-10" % sparkVersion )

    val sparkArango = Seq( "com.arangodb" %% "arangodb-spark-datasource-3.1" % sparkArangoVersion )

    val typesafeConfig = Seq( "com.typesafe" % "config" % typesafeConfigVersion )

    val logging = Seq( "org.slf4j" % "slf4j-api" % slf4jVersion,
                       "ch.qos.logback" % "logback-classic" % logbackVersion )

    // JSON
    val jackson = Seq( "com.fasterxml.jackson.module" %% "jackson-module-scala" % jacksonVersion,
                       "com.fasterxml.jackson.datatype" % "jackson-datatype-jsr310" % jacksonVersion )

    val betterFiles = Seq( "com.github.pathikrit" %% "better-files" % betterFilesVersion )

    val unitTesting = Seq( "org.scalatest" %% "scalatest" % scalaTestVersion % Test,
                           "org.mockito" %% "mockito-scala-scalatest" % mockitoVersion % Test )

}
