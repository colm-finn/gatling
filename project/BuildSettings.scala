import net.moznion.sbt.spotless.config._
import net.moznion.sbt.SbtSpotless.autoImport.spotlessJava
import sbt.Keys._
import sbt._

object BuildSettings {

  lazy val basicSettings = Seq(
    Test / parallelExecution := false,
    addCompilerPlugin("com.olegpy" %% "better-monadic-for" % "0.3.1"),
    licenses := Seq("Apache-2.0" -> url("http://www.apache.org/licenses/LICENSE-2.0.html")),
    spotlessJava := JavaConfig(
      googleJavaFormat = GoogleJavaFormatConfig()
    )
    // not set in private build
    // [fl]
    //
    //
    // [fl]
  )

  lazy val gatlingModuleSettings =
    basicSettings ++ scaladocSettings ++ utf8Encoding

  lazy val skipPublishing =
    publish / skip := true

  lazy val noSrcToPublish =
    Compile / packageSrc / publishArtifact := false

  lazy val noDocToPublish =
    Compile / packageDoc / publishArtifact := false

  // UTF-8

  lazy val utf8Encoding = Seq(
    fork := true,
    Compile / javacOptions ++= Seq("-encoding", "utf8"),
    Test / javacOptions ++= Seq("-encoding", "utf8")
  )

  // Documentation settings

  lazy val scaladocSettings = Seq(
    autoAPIMappings := true
  )

  // gatling-charts specific settings

  lazy val chartTestsSettings = Seq(
    fork := true,
    Test / javaOptions := Seq("-DGATLING_HOME=gatling-charts") // Allows FileDataReaderSpec to run
  )

  lazy val excludeDummyComponentLibrary = Seq(
    Compile / packageBin / mappings := {
      val compiledClassesMappings = (Compile / packageBin / mappings).value
      compiledClassesMappings.filter { case (_, path) => !path.contains("io/gatling/charts/component/impl") }
    }
  )
}
