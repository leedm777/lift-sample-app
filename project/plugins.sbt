libraryDependencies <+= sbtVersion(v =>
    "com.github.siasia" %% "xsbt-web-plugin" % (v+"-0.2.10"))

resolvers += "sbt-idea-repo" at "http://mpeltonen.github.com/maven/"

addSbtPlugin("com.github.mpeltonen" %% "sbt-idea" % "1.0.0")
