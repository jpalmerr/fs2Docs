// STREAM[F, O] => effect type F output type O

import fs2.{Pure, Stream}

val s0 = Stream.empty
val s1: Stream[Pure, Int] = Stream.emit(1)

val s1a = Stream(1, 2, 3)
val s1b: Stream[Pure, Int] = Stream.emits(List(1, 2, 3))

// Pure => doesn't use an effect

s1.toList

// list like functions

(Stream(1,2,3) ++ Stream(4,5)).toList
Stream(1,2,3).map(_ + 1).toList
Stream(1,2,3).filter(_ % 2 != 0).toList
Stream(1,2,3).fold(0)(_ + _).toList
Stream(None,Some(2),Some(3)).collect { case Some(i) => i }.toList
Stream.range(0,5).intersperse(42).toList
Stream(1,2,3).flatMap(i => Stream(i,i)).toList
Stream(1,2,3).repeat.take(9).toList
Stream(1,2,3).repeatN(2).toList

// FS2 streams can also include evaluation of effects:

import cats.effect.IO

val eff: Stream[IO, Int] = Stream.eval(IO { println("BEING RUN!!"); 1 + 1 })

/*
def eval[F[_],A](f: F[A]): Stream[F,A]

eval produces a stream that evaluates the given effect and emits the result
 */

eff.compile.toVector.unsafeRunSync() // Vector(2)

val rb: IO[Unit] = eff.compile.drain // purely for effects
// Removes all output values from this stream

rb.unsafeRunSync()


val rc = eff.compile.fold(0)(_ + _) // run and accumulate some result

rc.unsafeRunSync()

