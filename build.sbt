seq(webSettings: _*)

scalaVersion := "2.9.1"

scalacOptions := Seq("-deprecation", "-unchecked", "-explaintypes", "-Yrepl-sync")

libraryDependencies ++= {
  val liftVersion = "2.4"
  Seq(
    "net.liftweb" %% "lift-webkit" % liftVersion,
    "net.liftweb" %% "lift-wizard" % liftVersion,
    "net.liftweb" %% "lift-mapper" % liftVersion,
    "ch.qos.logback" % "logback-classic" % "1.0.0",
    "com.h2database" % "h2" % "1.2.138",
    "javax.servlet" % "servlet-api" % "2.5" % "provided->default",
    "org.mortbay.jetty" % "jetty" % "6.1.22" % "container,test")
}
