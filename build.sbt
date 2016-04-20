import com.typesafe.config.ConfigFactory

val conf = ConfigFactory.parseFile(new File("conf/application.conf")).resolve()

name := """myBlog"""

version := conf.getString("app.version")

routesGenerator := StaticRoutesGenerator

lazy val root = (project in file(".")).enablePlugins(PlayJava)

scalaVersion := "2.11.7"

javaOptions ++= Seq("-Xmx2048M", "-Xms512M", "-XX:MaxMetaspaceSize=256M")

libraryDependencies ++= Seq(
  javaJdbc,
  cache,
  javaWs,
  filters,  
  "org.webjars" % "jquery" % "1.11.0",
  "org.webjars" % "underscorejs" % "1.6.0",
  "org.webjars" % "bootstrap" % "3.3.5",  
  "org.webjars" % "bootswatch-cerulean" % "3.3.5+4",
  "org.webjars" % "backbonejs" % "1.2.3",
  "org.webjars" % "handlebars" % "4.0.2",
  "org.webjars" % "requirejs" % "2.1.22",
  "org.webjars" % "requirejs-text" % "2.0.14-1",
  "org.webjars" % "bootstrap-table" % "1.9.1",
  "org.webjars" % "jquery-ui" % "1.11.4",  
  "org.webjars" % "jquery-ui-themes" % "1.11.4",   
  "org.webjars" % "backgrid" % "0.3.5-3",
  "org.springframework" % "spring-context" % "4.2.4.RELEASE",
  "org.springframework" % "spring-tx" % "4.2.4.RELEASE",
  "org.springframework" % "spring-jdbc" % "4.2.4.RELEASE",
  "org.springframework" % "spring-test" % "4.2.4.RELEASE",
  "org.apache.commons" % "commons-lang3" % "3.4",
  "org.mockito" % "mockito-core" % "2.0.49-beta",
  "org.easytesting" % "fest-assert" % "1.4",
  "org.jasypt" % "jasypt" % "1.9.2",
  "com.sun.mail" % "javax.mail" % "1.5.5"
)
doc in Compile <<= target.map(_ / "none")
