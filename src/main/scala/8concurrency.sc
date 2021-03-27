//  The merge function runs two streams concurrently, combining their outputs

import cats.effect.{ContextShift, IO}
import fs2.Stream

import scala.concurrent.ExecutionContext


val dbExecutionContext = ExecutionContext.global
implicit val contextShift: ContextShift[IO] = IO.contextShift(dbExecutionContext)

Stream(1,2,3).merge(Stream.eval(IO { Thread.sleep(200); 4 })).compile.toVector.unsafeRunSync()

// The function parJoin runs multiple streams concurrently
