organization  := "com.example"

version       := "0.1"

scalaVersion  := "2.10.2"

scalacOptions := Seq("-unchecked", "-deprecation", "-encoding", "utf8")

resolvers ++= Seq(
  "spray repo" at "http://repo.spray.io/"
)

libraryDependencies ++= Seq(
  "io.spray"            %   "spray-can"     % "1.1-M8",
  "io.spray"            %   "spray-routing" % "1.1-M8",
  "io.spray"            %   "spray-http"    % "1.1-M8",
  "io.spray"            %   "spray-httpx"   % "1.1-M8",
  "io.spray"            %   "spray-util"    % "1.1-M8",
  "io.spray"            %   "spray-client"  % "1.1-M8",
  "io.spray"            %   "spray-testkit" % "1.1-M8",
  "io.spray"            %%  "spray-json"    % "1.2.5",
  "org.slf4j"           %   "slf4j-api"     % "1.7.5",
  "com.typesafe.akka"   %%  "akka-actor"    % "2.1.4",
  "com.typesafe.akka"   %%  "akka-testkit"  % "2.1.4",
  "com.typesafe.akka"   %%  "akka-slf4j"    % "2.1.4",
  "org.specs2"          %%  "specs2"        % "1.14" % "test",
  "net.liftweb"         %   "lift-json_2.10" % "2.5.1"
)

seq(Revolver.settings: _*)
