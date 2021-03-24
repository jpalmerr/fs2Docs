import fs2.Stream

Stream(1,0).repeat.take(6).toList

Stream(1,2,3).drain.toList

(Stream(1,2) ++ Stream(3).map(_ => throw new Exception("nooo!!!"))).attempt.toList