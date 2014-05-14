name := "shanshan"

version := "1.0-SNAPSHOT"

libraryDependencies ++= Seq(
  cache
)

unmanagedBase := baseDirectory.value / "lib"

play.Project.playScalaSettings
