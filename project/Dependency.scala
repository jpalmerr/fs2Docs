import sbt._

object Dependency {

  private object Version {
    val Cats          = "2.1.0"
    val Fs2           = "2.5.0"
  }

  val deps: Seq[ModuleID] = Seq(
    "org.typelevel"              %% "cats-core"            % Version.Cats,
    "org.typelevel"              %% "cats-effect"          % Version.Cats,
    "co.fs2"                     %% "fs2-core"             % Version.Fs2,
    "co.fs2"                     %% "fs2-io"               % Version.Fs2,
    "co.fs2"                     %% "fs2-reactive-streams" % Version.Fs2
  )

  def apply(): Seq[ModuleID]  =  deps
}