seq(webSettings: _*)

scalaVersion := "2.8.1"

libraryDependencies ++= {
  val liftVersion = "2.3"
  Seq(
    "net.liftweb" %% "lift-webkit" % liftVersion,
    "net.liftweb" %% "lift-wizard" % liftVersion,
    "net.liftweb" %% "lift-mapper" % liftVersion,
    "ch.qos.logback" % "logback-classic" % "0.9.26",
    "com.h2database" % "h2" % "1.2.138",
    "javax.servlet" % "servlet-api" % "2.5" % "provided->default",
    "org.mortbay.jetty" % "jetty" % "6.1.22" % "container,test",
    "junit" % "junit" % "4.5" % "test->default",
    "org.scala-tools.testing" %% "specs" % "1.6.6" % "test->default")
}
