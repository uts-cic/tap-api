name := "tap-api"
version := "2.0.0"
scalaVersion := "2.12.2"
organization := "au.edu.utscic"

// RUN sbt dependencyUpdates to check dependency version

// RUN sbt universal:packageZipTarball to create a tar package for upload to server
// ensure that JavaAppPackaging is enabled
enablePlugins(JavaAppPackaging)



//Scala library versions
val akkaVersion = "2.5.1"
val akkaStreamVersion = "2.5.1"
val akkaHttpVersion = "10.0.6"
val akkaHttpJson4sVersion = "1.16.1"
val json4sVersion = "3.5.2"
val slickVersion = "3.2.0"
val slickpgVersion = "0.15.0-RC"
val slf4jVersion = "1.7.25"
val logbackVersion = "1.2.3"
val scalatestVersion = "3.0.3"
val nlytxCommonsVersion = "0.1.1"
//Java library versions
val postgresDriverVersion = "42.1.1"
val openNlpVersion = "1.8.0"

//Akka
libraryDependencies ++= Seq(
  "com.typesafe.akka" % "akka-stream_2.12" % akkaStreamVersion,
  "com.typesafe.akka" % "akka-stream-testkit_2.12" % akkaStreamVersion,
  "com.typesafe.akka" %% "akka-http" % akkaHttpVersion,
  "com.typesafe.akka" %% "akka-http-testkit" % akkaHttpVersion,
  "com.typesafe.akka" %% "akka-slf4j" % akkaVersion
)
//NLP dependencies
libraryDependencies ++= Seq(
  "org.apache.opennlp" % "opennlp-tools" % openNlpVersion

)
//Slick
libraryDependencies ++= Seq(
  "com.typesafe.slick" %% "slick" % slickVersion,
  "com.typesafe.slick" %% "slick-hikaricp" % slickVersion,
  "org.postgresql" % "postgresql" % postgresDriverVersion,
  "com.github.tminglei" %% "slick-pg" % slickpgVersion,
  "com.github.tminglei" %% "slick-pg_json4s" % slickpgVersion
)

//General
libraryDependencies ++= Seq(
  "io.nlytx" %% "commons" % nlytxCommonsVersion,
//  "com.typesafe" % "config" % "1.3.1",
    "org.json4s" %% "json4s-jackson" % json4sVersion,
    "de.heikoseeberger" %% "akka-http-json4s" % akkaHttpJson4sVersion,
  "org.scalatest" %% "scalatest" % scalatestVersion % "test",
  "org.slf4j" % "jcl-over-slf4j" % slf4jVersion,
  "ch.qos.logback" % "logback-classic" % logbackVersion
)

scalacOptions in (Compile, doc) ++= Seq("-doc-root-content", baseDirectory.value+"/src/main/scala/root-doc.md")
