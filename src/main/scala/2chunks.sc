/**
A Chunk is a strict, finite sequence of values that supports efficient indexed based lookup of elements
*/

import fs2.{Stream, Chunk}

val s1c = Stream.chunk(Chunk.array(Array(1.0, 2.0, 3.0)))

s1c.compile.toList