enablePlugins(ScalaJSPlugin)

name := "hot-wire-scala-js"
version := "0.1-SNAPSHOT"

scalaVersion := "2.11.8"

libraryDependencies ++= Seq(
  "org.scala-js" %%% "scalajs-dom" % "0.9.0",
  "com.lihaoyi" %%% "scalatags" % "0.5.4"
)

persistLauncher in Compile := true

persistLauncher in Test := false
