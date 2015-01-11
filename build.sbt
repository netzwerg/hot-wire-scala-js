scalaJSSettings

name := "hot-wire-scala-js"

version := "0.1-SNAPSHOT"

scalaVersion := "2.11.2"

ScalaJSKeys.persistLauncher := true
skip in ScalaJSKeys.packageJSDependencies := false

libraryDependencies ++= Seq(
    "org.scala-lang.modules.scalajs" %%% "scalajs-dom" % "0.6",
    "org.scala-lang.modules.scalajs" %%% "scalajs-jquery" % "0.6"
)
