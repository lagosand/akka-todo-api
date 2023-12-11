lazy val root = project
  .in(file("."))
  .settings(
    name := "Akka Todo API",
    version := "0.1.0",
    scalaVersion := "3.3.1",

    Compile / unmanagedResourceDirectories += baseDirectory.value / "/etc/config",

    libraryDependencies ++= Seq(
      // Production
      "com.github.nscala-time"  %% "nscala-time"        % "2.32.0",

      "com.typesafe.akka"       %% "akka-http"          % "10.5.0",
      "com.typesafe.akka"       %% "akka-stream"        % "2.8.0",
      "com.typesafe.akka"       %% "akka-actor-typed"   % "2.8.0",
      "com.typesafe.akka"       %% "akka-slf4j"         % "2.8.0",

      "ch.qos.logback"          % "logback-classic"     % "1.4.7",

      // Test
      "org.scalatest"     %% "scalatest"          % "3.2.15"  % Test,
      "com.typesafe.akka" %% "akka-http-testkit"  % "10.5.0"  % Test,
      "com.typesafe.akka" %% "akka-testkit"       % "2.8.0"   % Test
    ),

    // Compiler options
    scalacOptions ++= Seq(
      "-deprecation",         // Warnings deprecation
      "-feature",             // Advise features
      "-unchecked",           // More warnings. Strict
      "-Xlint",               // More warnings when compiling
      "-Ywarn-dead-code",
      "-Ywarn-unused",
      "-Ywarn-unused-import",
      "-Xcheckinit"           // Check against early initialization
    ),

    Compile / run / scalacOptions -= "-Xcheckinit", // Remove it in production because it's expensive

    javaOptions += "-Duser.timezone=UTC",

    // Test options
    Test / parallelExecution := false,
    Test / testForkedParallel := false,
    Test / fork := true,
    Test / testOptions ++= Seq(
      Tests.Argument(TestFrameworks.ScalaTest, "-u", "target/test-reports"),  // Save test reports
      Tests.Argument("-oDF")                                                  // Show full stack traces and time spent in each test
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
