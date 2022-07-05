import Dependencies._
import sbt.Tests.Argument
import sbt._

organization in ThisBuild := "app.iria"
name := "ingestion-pipeline"
scalaVersion in ThisBuild := "2.12.7"

resolvers in ThisBuild ++= Seq( "Maven Central" at "https://repo1.maven.org/maven2/",
                                ( "Clulab Artifactory" at "http://artifactory.cs.arizona.edu:8081/artifactory/sbt-release" ).withAllowInsecureProtocol( true ),
                                "Local Ivy Repository" at s"file://${System.getProperty( "user.home" )}/.ivy2/local/default",
                                Resolver.mavenLocal )

lazy val root = ( project in file( "." ) )
  .settings( libraryDependencies ++= iria
                                     ++ spark
                                     ++ sparkArango
                                     ++ jackson
                                     ++ betterFiles
                                     ++ typesafeConfig
                                     ++ logging
                                     ++ unitTesting,
             dependencyOverrides ++= Seq( "org.apache.logging.log4j" % "log4j-api" % log4jOverrideVersion,
                                          "org.apache.logging.log4j" % "log4j-core" % log4jOverrideVersion,
                                          "org.apache.logging.log4j" % "log4j-slf4j-impl" % log4jOverrideVersion ) )

mainClass in(Compile, run) := Some( "Main" )

runMain in Compile := Defaults.runMainTask( fullClasspath in Compile, runner in(Compile, run) ).evaluated

enablePlugins( JavaAppPackaging )

// don't run tests when build the fat jar, use sbt test instead for that (takes too long when building the image)
test in assembly := {}

coverageExcludedPackages := ".*Main.*"

assemblyMergeStrategy in assembly := {
    case PathList( "META-INF", "MANIFEST.MF" ) => MergeStrategy.discard
    case PathList( "META-INF", "*.SF" ) => MergeStrategy.discard
    case PathList( "META-INF", "*.DSA" ) => MergeStrategy.discard
    case PathList( "META-INF", "*.RSA" ) => MergeStrategy.discard
    case PathList( "META-INF", "*.DEF" ) => MergeStrategy.discard
    case PathList( "*.SF" ) => MergeStrategy.discard
    case PathList( "*.DSA" ) => MergeStrategy.discard
    case PathList( "*.RSA" ) => MergeStrategy.discard
    case PathList( "*.DEF" ) => MergeStrategy.discard
    case PathList( "reference.conf" ) => MergeStrategy.concat
    case _ => MergeStrategy.last
}

assembly / assemblyOption := ( assembly / assemblyOption ).value.copy( includeScala = false )

parallelExecution in Test := false

testOptions in Test := Seq( Argument( "-oI" ) )
