import sbt._
import Keys._
import org.scalatra.sbt._
import org.scalatra.sbt.PluginKeys._
import com.mojolly.scalate.ScalatePlugin._
import ScalateKeys._

object TodddaviesBuild extends Build {
  val Organization = "com.todddavies"
  val Name = "todddavies"
  val Version = "0.1"
  val ScalaVersion = "2.11.1"
  val ScalatraVersion = "2.3.0"

  lazy val project = Project (
    "todddavies",
    file("."),
    settings = ScalatraPlugin.scalatraWithJRebel ++ scalateSettings ++ Seq(
      organization := Organization,
      name := Name,
      version := Version,
      scalaVersion := ScalaVersion,
      resolvers += Classpaths.typesafeReleases,
      libraryDependencies ++= Seq(
        "org.scalatra" %% "scalatra" % ScalatraVersion,
        "org.scalatra" %% "scalatra-scalate" % ScalatraVersion,
        "org.scalatra" %% "scalatra-specs2" % ScalatraVersion % "test",
        "ch.qos.logback" % "logback-classic" % "1.0.6" % "runtime",
        "org.eclipse.jetty" % "jetty-webapp" % "9.1.3.v20140225" % "container",
        "org.eclipse.jetty" % "jetty-plus" % "9.1.3.v20140225" % "container",
        "org.eclipse.jetty.orbit" % "javax.servlet" % "3.0.0.v201112011016" % "container;provided;test" artifacts (Artifact("javax.servlet", "jar", "jar"))
      ),
      // For the database
      /*libraryDependencies ++= Seq(
        "com.typesafe.slick" %% "slick" % "2.1.0",
        "com.h2database" % "h2" % "1.3.166",
        "c3p0" % "c3p0" % "0.9.1.2"
      ),*/
      libraryDependencies += "mysql" % "mysql-connector-java" % "5.1.12",
      scalateTemplateConfig in Compile <<= (sourceDirectory in Compile){ base =>
        Seq(
          TemplateConfig(
            base / "webapp" / "WEB-INF" / "templates",
            Seq.empty,  /* default imports should be added here */
            Seq.empty,  /* add extra bindings here */
            /*Seq(
              Binding("context", "_root_.org.scalatra.scalate.ScalatraRenderContext", importMembers = true, isImplicit = true)
            ),  /* add extra bindings here */*/
            Some("templates")
          )
        )
      }
    )
  )
}
