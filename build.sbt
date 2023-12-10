val scala3Version = "3.3.1"

lazy val root = project
  .in(file("."))
  .settings(
    name := "Akka Todo API",
    version := "0.1.0",
    scalaVersion := scala3Version,
    libraryDependencies ++= Seq(
      "com.github.nscala-time" %% "nscala-time" % "2.32.0",
      "com.typesafe.akka" %% "akka-http" % "10.5.0",
      "com.typesafe.akka" %% "akka-actor" % "2.8.0",
      "com.typesafe.akka" %% "akka-http-testkit" % "10.5.0" % Test,
      "com.typesafe.akka" %% "akka-testkit" % "2.8.0" % Test,
      "org.scalameta" %% "munit" % "0.7.29" % Test,
    )
  )

/** ********* COMMANDS ALIASES ******************/
addCommandAlias("t", "test")
addCommandAlias("to", "testOnly")
addCommandAlias("tq", "testQuick")
addCommandAlias("tsf", "testShowFailed")

addCommandAlias("c", "compile")
addCommandAlias("tc", "test:compile")

addCommandAlias("f", "scalafmt")             // Format production files according to ScalaFmt
addCommandAlias("fc", "scalafmtCheck")       // Check if production files are formatted according to ScalaFmt
addCommandAlias("tf", "test:scalafmt")       // Format test files according to ScalaFmt
addCommandAlias("tfc", "test:scalafmtCheck") // Check if test files are formatted according to ScalaFmt

// All the needed tasks before pushing to the repository (compile, compile test, format check in prod and test)
addCommandAlias("prep", ";c;tc;fc;tfc")
