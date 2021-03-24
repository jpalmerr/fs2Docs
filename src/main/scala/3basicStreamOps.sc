import cats.effect.IO
import fs2.Stream

val appendEx1 = Stream(1,2,3) ++ Stream.emit(42)

val appendEx2 = Stream(1,2,3) ++ Stream.eval(IO.pure(4))

appendEx1.toVector
// Vector(1, 2, 3, 42)
appendEx2.compile.toVector.unsafeRunSync()
// Vector(1, 2, 3, 4)

appendEx1.map(_ + 1).toList
// List(2, 3, 4, 43)

appendEx1.flatMap(i => Stream.emits(List(i,i))).toList

/*
s ++ s2

The runtime of these operations do not depend on the structure of s
*/

