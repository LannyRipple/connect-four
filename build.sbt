lazy val root =
    (project in file(".")).
    settings(
        name := "connectfour",
        organization := "org.ljr",
        version := "1.0.0",

        scalaVersion := "2.12.12",

        libraryDependencies ++= Seq(
        )
    )
