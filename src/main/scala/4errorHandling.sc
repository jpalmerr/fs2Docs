// A stream can raise errors

import cats.effect._
import fs2.Stream

val err = Stream.raiseError[IO](new Exception("oh noes!"))

err.handleErrorWith {e =>
  Stream.emits(e.getLocalizedMessage)
}.compile.toList.unsafeRunSync()

// ^ the stream will be terminated after the error and no more values will be pulled

val err4 = Stream(1,2,3).covary[IO] ++
  Stream.raiseError[IO](new Exception("bad things!")) ++
  Stream.eval(IO(4))

err4.handleErrorWith { _ => Stream(0) }.compile.toList.unsafeRunSync()

// don't use handleErrorWith for resource clean up
