/*
s: Stream[IO,Int]

To produce a Stream[IO,Int] which takes just the first 5 elements of s,
we need to repeatedly await (or pull) values from s,
keeping track of the number of values seen so far and stopping as soon as we hit 5 elements
 */

import fs2._

def tk[F[_],O](n: Long): Pipe[F,O,O] =
  in => in.scanChunksOpt(n) { n =>
    if (n <= 0) None
    else Some(c => c.size match {
      case m if m < n => (n - m, c)
      case _ => (0, c.take(n.toInt))
    })
  }

Stream(1,2,3,4).through(tk(2)).toList

/*
Sometimes, scanChunksOpt isn't powerful enough to express the stream transformation.
Regardless of how complex the job,
the fs2.Pull type can usually express it.


represents a program that may pull values from one or more streams,
write output of type O, and return a result of type R
 */

import fs2._

def tk[F[_],O](n: Long): Pipe[F,O,O] = {
  def go(s: Stream[F,O], n: Long): Pull[F,O,Unit] = {
    s.pull.uncons.flatMap {     // We use uncons to pull the next chunk from the stream
      case Some((hd,tl)) =>
        hd.size match {
          case m if m <= n => Pull.output(hd) >> go(tl, n - m)
          case _ => Pull.output(hd.take(n.toInt)) >> Pull.done
        }
      case None => Pull.done
    }
  }
  in => go(in,n).stream
}

Stream(1,2,3,4).through(tk(2)).toList

// through: Transforms this stream using the given Pipe

Stream.range(0,100).takeWhile(_ < 7).toList
Stream("Alice","Bob","Carol").intersperse("|").toList
Stream.range(1,10).scan(0)(_ + _).toList